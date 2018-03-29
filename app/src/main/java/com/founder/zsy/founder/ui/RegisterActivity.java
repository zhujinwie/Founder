package com.founder.zsy.founder.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.founder.zsy.founder.util.CountDownTimerUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.toolbar2)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.phone)
    TextInputLayout phone;
    @BindView(R.id.code_edit)
    EditText codeEdit;
    @BindView(R.id.code)
    TextInputLayout code;
    @BindView(R.id.paw_edit)
    EditText pawEdit;
    @BindView(R.id.paw)
    TextInputLayout paw;
    @BindView(R.id.nike_edit)
    EditText nikeEdit;
    @BindView(R.id.nick)
    TextInputLayout nick;
    @BindView(R.id.getCode)
    TextView getCode;
    private CountDownTimer mCountDownTimer;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bind = ButterKnife.bind(this);
        toolbarTitle.setText("注册");
        StatusBarCompat.compat(this, getResources().getColor(R.color.bgred));
        mCountDownTimer = new CountDownTimerUtils(getCode, 60000, 1000);
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

    @OnClick({R.id.getCode, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.getCode:
                mCountDownTimer.start();
                break;
            case R.id.register:
                if (phoneEdit.getText().toString().trim().length() == 11) {
                    if (pawEdit.getText().toString().trim().length() >= 6) {
                        if (nikeEdit.getText().toString().trim().length() > 0) {
                            SharedPreferencesUtils.put(this, "phone", phoneEdit.getText().toString().trim());
                            SharedPreferencesUtils.put(this, "paw", pawEdit.getText().toString().trim());
                            SharedPreferencesUtils.put(this, "nick", nikeEdit.getText().toString().trim());
                            Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "密码不能少于6位", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
