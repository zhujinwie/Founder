package com.founder.zsy.founder.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.founder.zsy.founder.R;
import com.founder.zsy.founder.util.SharedPreferencesUtils;
import com.founder.zsy.founder.util.StatusBarCompat;
import com.founder.zsy.founder.bean.LoginBean;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity {
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
    private Unbinder bind;

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @OnClick({R.id.login_register, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login:
                if (SharedPreferencesUtils.contains(this, "phone")) {
                    if (phoneEdit.getText().toString().trim().equals(SharedPreferencesUtils.get(this, "phone", ""))
                            && pawEdit.getText().toString().trim().equals(SharedPreferencesUtils.get(this, "paw", ""))) {
                        LoginBean loginBean=new LoginBean();
                        loginBean.setKey("success");
                        EventBus.getDefault().postSticky(loginBean);
                        finish();
                    } else {
                        Toast.makeText(this, "请输入正确的账户或密码", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请前往注册账户", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
