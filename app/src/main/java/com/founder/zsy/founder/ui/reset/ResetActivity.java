package com.founder.zsy.founder.ui.reset;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.founder.zsy.founder.R;
import com.founder.zsy.founder.bean.LoginEntity;
import com.founder.zsy.founder.util.Code;
import com.founder.zsy.founder.util.IDUtil;
import com.founder.zsy.founder.util.MD5Util;
import com.founder.zsy.founder.util.UserInfoHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.showCode)
    ImageView codeIv;
    @BindView(R.id.paw1_edit)
    EditText paw1Et;
    @BindView(R.id.paw2_edit)
    EditText paw2Et;

    ResetPresenter presenter;
    private String code;
    private IDUtil idUtil;


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

        presenter=new ResetPresenter();
        presenter.attachView(this);

        codeIv.setImageBitmap(Code.getInstance().createBitmap());
        code= Code.getInstance().getCode().toLowerCase();

        idUtil=new IDUtil();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        presenter.detachView();
    }

    @OnClick({R.id.showCode,R.id.reset})
    void onClickView(View v){

        switch(v.getId()){

            case R.id.showCode:

                codeIv.setImageBitmap(Code.getInstance().createBitmap());
                code=Code.getInstance().getCode().toLowerCase();

                break;

            case R.id.reset:

                //空判定
                if(TextUtils.isEmpty(agentEt.getText().toString())){
                    agentEt.setError("工号不能为空！");
                    codeIv.setImageBitmap(Code.getInstance().createBitmap());
                    code=Code.getInstance().getCode().toLowerCase();
                    return;
                }

                if(TextUtils.isEmpty(codeEt.getText().toString())){

                    codeEt.setError("验证码不能为空! ");
                    codeIv.setImageBitmap(Code.getInstance().createBitmap());
                    code=Code.getInstance().getCode().toLowerCase();
                    return;
                }

                if(TextUtils.isEmpty(paw1Et.getText().toString())){

                    paw1Et.setError("密码不能为空！");
                    codeIv.setImageBitmap(Code.getInstance().createBitmap());
                    code=Code.getInstance().getCode().toLowerCase();
                    return;
                }

                if(TextUtils.isEmpty(paw2Et.getText().toString())){

                    paw2Et.setError("确认密码不能为空！");
                    codeIv.setImageBitmap(Code.getInstance().createBitmap());
                    code=Code.getInstance().getCode().toLowerCase();
                    return;
                }
                //验证码判定
                if(!code.equals(codeEt.getText().toString().trim().toLowerCase())){
                    Toast.makeText(this, "验证码输入错误！", Toast.LENGTH_SHORT).show();
                    codeEt.setText("");
                    codeIv.setImageBitmap(Code.getInstance().createBitmap());
                    code=Code.getInstance().getCode().toLowerCase();
                    return;
                }
                //重复密码判定
                if(!paw1Et.getText().toString().equals(paw2Et.getText().toString())){

                    Toast.makeText(this,"密码输入不一致！",Toast.LENGTH_SHORT).show();
                    codeIv.setImageBitmap(Code.getInstance().createBitmap());
                    code=Code.getInstance().getCode().toLowerCase();
                    return;
                }
                //上传新密码

                Map<String,String> params=new HashMap<>();
               // params.put("agentId",agentEt.getText().toString());
               // params.put("password",paw2Et.getText().toString());
               // params.put("macId", TextUtils.isEmpty(IMEIUtil.IMEI)?IMEIUtil.getImei(this):IMEIUtil.IMEI);

                params.put("agentId", MD5Util.getMD5_32_Value(agentEt.getText().toString()));
                params.put("password",paw2Et.getText().toString());
                params.put("macId",MD5Util.getMD5_32_Value(idUtil.getUUID(this)));
                presenter.resetPwd(params);
                showLoading();
                break;
        }
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

    }

    @Override
    public void showLoading() {

    }
}
