package com.founder.zsy.founder.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.founder.zsy.founder.R;
import com.founder.zsy.founder.api.FoRetrofit;
import com.founder.zsy.founder.bean.LoginEntity;
import com.founder.zsy.founder.bean.TotalEntity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.title01)
    TextView title01;
    @BindView(R.id.title02)
    TextView title02;
    @BindView(R.id.title03)
    TextView title03;

    @BindView(R.id.edit_01)
    EditText edit01;
    @BindView(R.id.edit_02)
    EditText edit02;
    @BindView(R.id.edit_03)
    EditText edit03;

    @BindView(R.id.result_test)
    TextView resultTv;


    Unbinder binder;
    int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        binder = ButterKnife.bind(this);
    }

    @OnClick(R.id.login_test)
    void login() {
        type = 1;
        clearAll();
        title01.setText("TEL");
        title02.setText("Pwd");
    }

    /**
     * 保单号+身份证
     **/
    @OnClick(R.id.policy_test_1)
    void getPolicy01() {
        type = 2;
        clearAll();
        title01.setText("保单号");
        title02.setText("身份证");
    }

    @OnClick(R.id.policy_test_2)
    void getPolicy02() {
        type = 3;
        clearAll();
        title01.setText("姓名");
        title02.setText("页码");
        title03.setText("页数");
    }

    @OnClick(R.id.laln_test)
    void LaLn() {
        type = 4;
        clearAll();
        title01.setText("AgentId");
        title02.setText("La");
        title03.setText("Ln");
    }


    @OnClick(R.id.start_test)
    void startTest() {

        switch (type) {

            case 1:
                loginTest();
                break;

            case 2:
                policy01Test();
                break;

            case 3:
                policy02Test();
                break;


            case 4:
                LaLnTest();
                break;

        }

    }

    void clearAll() {


        title01.setText("");
        title02.setText("");
        title03.setText("");

        edit01.setText("");
        edit02.setText("");
        edit03.setText("");

        resultTv.setText("");

    }

    void policy01Test() {

        Log.d("Test","保单号测试！");
        final Map<String, String> params = new HashMap<>();
        params.put("INSURE_NO", edit01.getText().toString());
        params.put("INSURERID", edit02.getText().toString());

        FoRetrofit.getRestApi().getPolicy(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TotalEntity>() {
                    @Override
                    public void accept(TotalEntity totalEntity) throws Exception {
                        resultTv.setText(totalEntity.toString());
                        Toast.makeText(TestActivity.this, "获取到total参数！", Toast.LENGTH_SHORT).show();
                        Log.d("Test","totla="+totalEntity.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Test","捕获到异常！ throw="+throwable.getMessage());
                        throwable.printStackTrace();
                    }
                });

    }

    void policy02Test(){

        String name=edit01.getText().toString();
        String page=edit02.getText().toString();
        String size=edit03.getText().toString();
        Map<String,String > params=new HashMap<>();
        params.put("page",page);
        params.put("page_size",size);
        params.put("INSURERNAME",name);
        FoRetrofit.getRestApi().getPolicy02(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<TotalEntity>() {
                    @Override
                    public void accept(TotalEntity totalEntity) throws Exception {
                        resultTv.setText(totalEntity.toString());
                        Toast.makeText(TestActivity.this, "获取到total参数！", Toast.LENGTH_SHORT).show();
                        Log.d("Test","totla="+totalEntity.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Test","捕获到异常！ throw="+throwable.getMessage());
                        throwable.printStackTrace();
                    }
                });
    }

    void loginTest(){

        String tel=edit01.getText().toString();
        String pwd=edit02.getText().toString();

        Map<String,String> params=new HashMap<>();
        params.put("tel",tel);
        params.put("password",pwd);

        FoRetrofit.getRestApi().login(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<LoginEntity>() {
                    @Override
                    public void accept(LoginEntity loginEntity) throws Exception {
                        resultTv.setText("登陸陳公公");
                        Toast.makeText(TestActivity.this, "登录成功你那个！", Toast.LENGTH_SHORT).show();
                        Log.d("Test","login="+loginEntity.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Test","捕获到异常！ throw="+throwable.getMessage());
                        throwable.printStackTrace();
                    }
                });
    }

    void LaLnTest(){

        String id=edit01.getText().toString();
        String la=edit02.getText().toString();
        String ln=edit03.getText().toString();
        Map<String,String> params=new HashMap<>();
        params.put("agentId",id);
        params.put("lat",la);
        params.put("lng",ln);
        FoRetrofit.getRestApi().uploadLaLn(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        resultTv.setText("OK!");
                        Toast.makeText(TestActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                        Log.d("Test","登录陈宫！");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Test","捕获到异常！ throw="+throwable.getMessage());
                        throwable.printStackTrace();
                    }
                });



    }



}