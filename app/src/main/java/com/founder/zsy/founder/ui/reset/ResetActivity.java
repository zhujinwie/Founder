package com.founder.zsy.founder.ui.reset;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.platform.comapi.map.C;
import com.founder.zsy.founder.R;
import com.founder.zsy.founder.bean.LoginEntity;
import com.founder.zsy.founder.util.Code;
import com.founder.zsy.founder.util.IMEIUtil;
import com.founder.zsy.founder.util.MD5Util;
import com.founder.zsy.founder.util.UserInfoHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ResetActivity extends AppCompatActivity implements ResetContract.View{

    Unbinder binder;

    @BindView(R.id.toolbar2)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.agent_edit)
    EditText agentEt;
    @BindView(R.id.code_edit)
    EditText codeEt;
    @BindView(R.id.paw1_edit)
    EditText pawEt1;
    @BindView(R.id.paw2_edit)
    EditText pawEt2;
    @BindView(R.id.showCode)
    ImageView codeIv;
    ResetPresenter presenter;
    private String code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        binder=ButterKnife.bind(this);

        toolbarTitle.setText("密码重设");
        toolbar.setNavigationIcon(R.mipmap.left_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        code= Code.getInstance().getCode().toLowerCase();
        presenter=new ResetPresenter();
        presenter.attachView(this);

    }


    @OnClick({R.id.showCode,R.id.reset})
    void onViewClicked(View view){

        int id=view.getId();
        switch(id){

            case R.id.showCode:

                codeIv.setImageBitmap(Code.getInstance().createBitmap());
                code=Code.getInstance().getCode().toLowerCase();
                break;

            case R.id.reset:

                if(agentEt.getText().toString().trim().length() ==0){
                    agentEt.setError("请输入正确的工号！");
                    return;
                }
                if(codeEt.getText().toString().length() == 0 || !codeEt.getText().toString().trim().equals(code)){
                    codeEt.setError("请输入正确的验证码！");
                    codeEt.setText("");
                    codeIv.setImageBitmap(Code.getInstance().createBitmap());
                    code= Code.getInstance().getCode().toLowerCase();
                    return;
                }
                if(pawEt1.getText().toString().trim().length() == 0){
                    pawEt1.setError("请输入正确的密码！");
                    pawEt1.setText("");
                    pawEt2.setText("");
                    return;
                }

                if(pawEt2.getText().toString().trim().length()== 0 || !pawEt1.getText().toString().trim().equals(pawEt2.getText().toString().trim())){
                    pawEt2.setError("密码设置不一致！");
                    pawEt1.setText("");
                    pawEt2.setText("");
                    return;
                }

                Map<String,String> params=new HashMap<>();
                params.put("agentId", MD5Util.encrypt32(agentEt.getText().toString()));
                params.put("password",MD5Util.encrypt32(pawEt1.getText().toString()));
                params.put("macId", MD5Util.encrypt32(IMEIUtil.getIMEI(ResetActivity.this)));
                presenter.resetPwd(params);
                showLoading();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        presenter.detachView();
    }

    @Override
    public void resetSuccess(LoginEntity loginEntity) {

        if(loginEntity == null)
            return;
        else if(loginEntity.getStatus() == 1){
            Toast.makeText(this, "工号不存在，请重新输入！", Toast.LENGTH_SHORT).show();
        }else if(loginEntity.getStatus() == 2){
            Toast.makeText(this, "请使用自己的手机进行修改操作！", Toast.LENGTH_SHORT).show();
        }else if(loginEntity.getStatus() == 3){
            Toast.makeText(this, "后台繁忙,请稍后重试！", Toast.LENGTH_SHORT).show();
        }else{

            UserInfoHelper.setUserInfo(this,loginEntity.getProfile());
            EventBus.getDefault().postSticky("login!");
            finish();
        }
    }

    @Override
    public void showError(int code) {

        if(code == 1){

            Toast.makeText(this, "服务器不可用...", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showLoading() {

    }
}
