package com.founder.zsy.founder.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.founder.zsy.founder.R;
import com.founder.zsy.founder.bean.LoginEntity;
import com.founder.zsy.founder.ui.RegisterActivity;
import com.founder.zsy.founder.util.StatusBarCompat;
import com.founder.zsy.founder.util.UserInfoHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity implements LoginContract.View{
    @BindView(R.id.toolbar2)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.phone)
    TextInputLayout phone;
    @BindView(R.id.paw_edit)
    EditText pawEdit;
    @BindView(R.id.paw)
    TextInputLayout paw;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private Unbinder bind;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarCompat.compat(this, getResources().getColor(R.color.bgred));
        bind = ButterKnife.bind(this);
        toolbarTitle.setText("登录");
        toolbar.setNavigationIcon(R.mipmap.left_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        presenter=new LoginPresenter();
        presenter.attachView(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        presenter.detachView();
    }

    @OnClick({R.id.login_register, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login:

                    if ((phoneEdit.getText().toString().trim().length() ==11)
                            && pawEdit.getText().toString().trim().length() != 0) {

                        Map<String,String> params=new HashMap<>();
                        params.put("tel",phoneEdit.getText().toString().trim());
                        params.put("password", pawEdit.getText().toString().trim());
                        presenter.login(params);
                        showLoading();
                    } else {
                        Toast.makeText(this, "请输入正确的账户或密码", Toast.LENGTH_SHORT).show();
                    }
                break;
        }
    }

    @Override
    public void loginSuccrss(LoginEntity loginEntity) {
        Log.d("Test","loginEntity="+loginEntity);
        onComplete();
        if(loginEntity == null || loginEntity.getStatus() !=0 || loginEntity.getProfile() == null)
            showError(1);
        else{
            UserInfoHelper.setUserInfo(this,loginEntity.getProfile());
            EventBus.getDefault().postSticky("login!");
            Toast.makeText(this, "登录成功！正在跳转...", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    @Override
    public void showError(int i) {

        if(i==1)
            Toast.makeText(this, "账号密码错误，请重试！", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"网络异常，请检查网络连接！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onComplete() {

        progressBar.setVisibility(View.INVISIBLE);
    }


}
