package com.founder.zsy.founder.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.founder.zsy.founder.R;
import com.founder.zsy.founder.bean.LoginEntity;
import com.founder.zsy.founder.ui.RegisterActivity;
import com.founder.zsy.founder.ui.base.BaseActivity;
import com.founder.zsy.founder.ui.reset.ResetActivity;
import com.founder.zsy.founder.util.IDUtil;
import com.founder.zsy.founder.util.MD5Util;
import com.founder.zsy.founder.util.StatusBarCompat;
import com.founder.zsy.founder.util.UserInfoHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends BaseActivity implements LoginContract.View{
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
    @BindView(R.id.reset_tv)
    TextView resetTv;
    private Unbinder bind;
    private IDUtil idUtil;
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
        idUtil=new IDUtil();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        presenter.detachView();
    }

    @OnClick({R.id.login_register, R.id.login,R.id.reset_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login:

                    if ((phoneEdit.getText().toString().trim().length() ==11)
                            && pawEdit.getText().toString().trim().length() != 0) {

                        Map<String,String> params=new HashMap<>();
                        //params.put("tel",phoneEdit.getText().toString().trim());
                        //params.put("password", pawEdit.getText().toString().trim());
                        params.put("tel", MD5Util.getMD5_32_Value(phoneEdit.getText().toString().trim()));
                        params.put("password",MD5Util.getMD5_32_Value(pawEdit.getText().toString().trim()));
                        params.put("macId",MD5Util.getMD5_32_Value(idUtil.getUUID(this)));
                        presenter.login(params);
                        showLoading();
                    } else {
                        Toast.makeText(this, "请输入正确的账户或密码", Toast.LENGTH_SHORT).show();
                    }
                break;

            case R.id.reset_tv:

                Intent intent=new Intent(this, ResetActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void loginSuccrss(LoginEntity loginEntity) {
        onComplete();
        if( loginEntity == null || loginEntity.getStatus() == 3){
            Toast.makeText(this, "请使用自己的手机登录!", Toast.LENGTH_SHORT).show();
        }
        else if(loginEntity.getStatus() == 1){
            Toast.makeText(this,"账号/密码错误，请重新输入！",Toast.LENGTH_SHORT).show();
        }
        else if(loginEntity.getStatus() == 2){
            showError("服务器异常，请联系工作人员！");
        }
        else {
            UserInfoHelper.setUserInfo(this, loginEntity.getProfile());
            EventBus.getDefault().postSticky("login!");
            Toast.makeText(this, "登录成功！正在跳转...", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }

    }

    @Override
    public void showError(String  error) {

        if(error.contains("异次元"))
            Toast.makeText(this, "请求异常，请重试！", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        showDialog("登录中...");
    }

    @Override
    public void onComplete() {
        closeDialog();
    }

}
