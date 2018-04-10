package com.founder.zsy.founder.ui.mine;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.founder.zsy.founder.R;
import com.founder.zsy.founder.adapter.MessageAdapter;
import com.founder.zsy.founder.bean.LocationBean;
import com.founder.zsy.founder.bean.base.MsgEntity;
import com.founder.zsy.founder.ui.base.BaseFragment;
import com.founder.zsy.founder.ui.login.LoginActivity;
import com.founder.zsy.founder.ui.profile.ProfileActivity;
import com.founder.zsy.founder.util.UserInfoHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment implements MineContract.View {


    @BindView(R.id.img)
    CircleImageView img;
    @BindView(R.id.name)
    TextView name;
    Unbinder unbinder;
    @BindView(R.id.rcv)
    RecyclerView rcv;
    private List<LocationBean> list;
    private Map<String,String> params;
    private MessageAdapter adapter;
    MinePresenter presenter;

    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initData();
        presenter=new MinePresenter();
        presenter.attachView(this);

        return view;
    }

    private void initData() {

        if(UserInfoHelper .isLogin(getContext())){
            name.setText(UserInfoHelper.getCurrentUser(getContext()).getAgentName());
        }
        params=new HashMap<>();
        list=new ArrayList<>(50);
        adapter=new MessageAdapter(list);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.setItemAnimator(new DefaultItemAnimator());
        rcv.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
        presenter.detachView();
    }

    @OnClick(R.id.parent)
    public void onViewClicked() {
        if (!UserInfoHelper.isLogin(getContext())) {
            startActivity(new Intent(getContext(), LoginActivity.class));
        }else{
            startActivity(new Intent(getContext(), ProfileActivity.class));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LocationBean bean) {

        Log.d("Test","MyFragment 接收到定位信心!");
        if(bean == null) onError(0);
        if(bean.getType() == 101 || bean.getType() ==102 || bean.getType() == 103 ){
            onError(bean.getType());
        }else if( !UserInfoHelper.isLogin(getContext())){
            onError(1);
        }else{
            params.clear();
            //Log.d("Test","agentId="+UserInfoHelper.getCurrentUser(getContext()).getAgentId());
            params.put("agentId",UserInfoHelper.getCurrentUser(getContext()).getAgentId());
            params.put("lat",bean.getLa());
            params.put("lng",bean.getLn());
           //TODO test
            params.put("desc",bean.getPoi());
            //params.put("desc",bean.getDesc());
            presenter.uploadLaLn(params);
            if(list.size()== 50){
                list.clear();
            }
            list.add(bean);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEventMainThread2(String bean) {

        if("login!".equals(bean) && UserInfoHelper.isLogin(getContext())){
            name.setText(TextUtils.isEmpty(UserInfoHelper.getCurrentUser(getContext()).getAgentName())?"这个人很懒":UserInfoHelper.getCurrentUser(getContext()).getAgentName());
        }else if("logout!".equals(bean)){
            name.setText("登录/注册");
        }else{
            name.setText("这个人很懒...");
        }

    }

    @Override
    public void uploadSuccess(MsgEntity msg) {

        if(msg == null){
            onError(0);
        }else if(msg.getStatus() == 0){
            //TODO 上传定位成功！更新通知栏
            adapter.addData(list);
            adapter.notifyDataSetChanged();
            rcv.scrollToPosition(adapter.getItemCount()-1);
        }else if(msg.getStatus() == 2){
            onError(2);
        }else{
            onError(0);
        }

    }

    @Override
    public void onError(int code) {
        switch(code){
            case 0:
                Toast.makeText(getContext(), "网络错误！上传定位失败", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext())
                        .setTitle("请先登录！")
                        .setMessage("注意！ 未登录状态下无法上传坐标！")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .setNegativeButton("前往登录！", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(getActivity(),LoginActivity.class));
                            }
                        });

                builder.create();
                break;
            case 2:
                Toast.makeText(getContext(), "服务器网络错误！", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getContext(), "百度定位出现问题！", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
