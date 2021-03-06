package com.founder.zsy.founder.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.founder.zsy.founder.MyApp;
import com.founder.zsy.founder.R;
import com.founder.zsy.founder.bean.LocationBean;
import com.founder.zsy.founder.bean.TabEntity;
import com.founder.zsy.founder.service.LocationService;
import com.founder.zsy.founder.ui.homepage.HomeFragment;
import com.founder.zsy.founder.ui.login.LoginActivity;
import com.founder.zsy.founder.ui.mine.MyFragment;
import com.founder.zsy.founder.util.StatusBarCompat;
import com.founder.zsy.founder.util.UserInfoHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    private LocationService locationService;
    @BindView(R.id.main_tab_layout)
    CommonTabLayout tabLayout;

    private String[] mTitles = {"首页", "我"};
    private int[] normal = {
            R.mipmap.tab_bar_btn_home_normal, R.mipmap.tab_bar_btn_my_normal};
    private int[] sel = {
            R.mipmap.tab_bar_btn_home_sel, R.mipmap.tab_bar_btn_my_sel};
    private List<Fragment> list = new ArrayList<>();
    //当前展示的Fragment的位置
    private int currentIndex = 0;
    private Unbinder bind;
    private boolean type;
    public static final int REQUEST_CODE_LOGIN=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarCompat.compat(this, getResources().getColor(R.color.bgred));
        bind = ButterKnife.bind(this);

        list.add(new HomeFragment());
        list.add(new MyFragment());
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], sel[i], normal[i]));
        }
        tabLayout.setTabData(mTabEntities);
        setDefaultFragment();
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

                switchFragment(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        location();

        //启动时检查是否登录
        checkIsLoginAndShowDialog();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_OK){
            //登录成功，可以定位了...

            Log.d("Test","开始定位!");
            startLocation();
        }else{
            //登录失败，
            Toast.makeText(this,"未登录状态无法上传坐标！",Toast.LENGTH_LONG).show();
            stopLocation();
        }

    }

    private void location() {
        locationService = ((MyApp) getApplication()).locationService;
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
    }

    //默认显示的Fragment
    private void setDefaultFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_parent, list.get(0)).commit();

    }

    private void switchFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = list.get(currentIndex);
        Fragment targetFragment = list.get(position);
        if (!targetFragment.isAdded()) {
            transaction.add(R.id.main_parent, targetFragment).hide(currentFragment).commit();
        } else {
            transaction.show(targetFragment).hide(currentFragment).commit();
        }
        currentIndex = position;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        bind.unbind();
    }

    //开启定位
    void startLocation(){

        if (!type) {
            MainActivityPermissionsDispatcher.needWithCheck(this);
            type = true;
            Toast.makeText(this, "定位开启", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "正在定位,请不要重复开启。", Toast.LENGTH_SHORT).show();
        }
    }

    //停止定位
    void stopLocation(){

        if (type) {
            locationService.stop();
            type = false;
            Toast.makeText(this, "定位停止", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "请开启定位", Toast.LENGTH_SHORT).show();
        }

    }

    //检查是否登录
    void checkIsLoginAndShowDialog(){
        if(!UserInfoHelper.isLogin(this)){

            new android.app.AlertDialog.Builder(this)
                    .setTitle("请先登录！")
                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this,"未登录状态无法上传坐标！",Toast.LENGTH_LONG).show();
                            return;
                        }
                    })
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivityForResult(new Intent(MainActivity.this, LoginActivity.class),REQUEST_CODE_LOGIN);
                        }
                    }).show();
        }else{
            startLocation();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEvenetMainThread(String bean){

        if(TextUtils.isEmpty(bean)) return;
        if(bean.equals("login!")){
            Log.d("Test","login接收成功！");
            startLocation();
        }else if(bean.equals("logout!")){
            Log.d("Test","logou接收成功!");
            stopLocation();
        }else{
            Log.d("Test","尼玛发生了什么啊！");
            return;
        }
    }


    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void need() {
        locationService.start();// 定位SDK
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void show(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("权限请求")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//再次执行请求
                    }
                })
                .show();
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void denied() {
    }

    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void never() {
        Toast.makeText(this, "权限未打开", Toast.LENGTH_SHORT).show();
    }

    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                if (location.getCity() != null) {
                }
                StringBuffer sb = new StringBuffer(256);
                LocationBean locationBean=new LocationBean();
                locationBean.setLa(location.getLatitude()+"");
                locationBean.setLn(location.getLongitude()+"");
                locationBean.setDesc(location.getLocationDescribe());
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                locationBean.setPoi(sb.toString());
                locationBean.setTime(location.getTime());
                if(location.getLocType() == BDLocation.TypeGpsLocation){
                    locationBean.setType(0);//GPS定位
                }else if(location.getLocType() == BDLocation.TypeNetWorkLocation){
                    locationBean.setType(1);//网络定位
                }else if(location.getLocType() == BDLocation.TypeOffLineLocation){
                    locationBean.setType(2);
                }else if (location.getLocType() == BDLocation.TypeServerError) {
                    //sb.append("\ndescribe : ");
                    //sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                    locationBean.setType(101);
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    // sb.append("\ndescribe : ");
                    // sb.append("网络不同导致定位失败，请检查网络是否通畅");
                    locationBean.setType(102);
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    //sb.append("\ndescribe : ");
                    //sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                    locationBean.setType(103);
                }
                //sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                //sb.append(location.getTime());
//                sb.append("\n定位类型 : ");// 定位类型
//                sb.append(location.getLocType());
                //sb.append("\n纬度 : ");// 纬度
                //sb.append(location.getLatitude());
                //sb.append("\n经度 : ");// 经度
                //sb.append(location.getLongitude());
//                sb.append("\n半径 : ");// 半径
//                sb.append(location.getRadius());
//                sb.append("\n国家码 : ");// 国家码
//                sb.append(location.getCountryCode());
//                sb.append("\n国家名称 : ");// 国家名称
//                sb.append(location.getCountry());
//                sb.append("\n城市编码 : ");// 城市编码
//                sb.append(location.getCityCode());
//                sb.append("\n城市 : ");// 城市
//                sb.append(location.getCity());
//                sb.append("\n区 : ");// 区
//                sb.append(location.getDistrict());
//                sb.append("\n街道 : ");// 街道
//                sb.append(location.getStreet());
                //sb.append("\n地址信息 : ");// 地址信息
               // sb.append(location.getAddrStr());
//                sb.append("\n方向: ");
//                sb.append(location.getDirection());// 方向
               // sb.append("\n位置语义化信息: ");
               // sb.append(location.getLocationDescribe());// 位置语义化信息
                //sb.append("\nPOI信息: ");// POI信息
              /*  if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }*/
//                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
//                    sb.append("\n速度 : ");
//                    sb.append(location.getSpeed());// 速度 单位：km/h
//                    sb.append("\n卫星数目 : ");
//                    sb.append(location.getSatelliteNumber());// 卫星数目
//                    sb.append("\n海拔高度 : ");
//                    sb.append(location.getAltitude());// 海拔高度 单位：米
//                    sb.append("\ndescribe : ");
//                    sb.append("gps定位成功");
//                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
//                    // 运营商信息
//                    if (location.hasAltitude()) {// *****如果有海拔高度*****
//                        sb.append("\nheight : ");
//                        sb.append(location.getAltitude());// 单位：米
//                    }
//                    sb.append("\n运营商信息 : ");// 运营商信息
//                    sb.append(location.getOperators());
//                    sb.append("\ndescribe : ");
//                    sb.append("网络定位成功");
//                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//                    sb.append("\ndescribe : ");
//                    sb.append("离线定位成功，离线定位结果也是有效的");
//                } else

                EventBus.getDefault().postSticky(locationBean);
            }
        }
    };

}
