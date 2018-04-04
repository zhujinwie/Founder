package com.founder.zsy.founder.ui.reset;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.founder.zsy.founder.R;
import com.founder.zsy.founder.bean.LoginEntity;
import com.founder.zsy.founder.util.UserInfoHelper;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ResetActivity extends AppCompatActivity implements ResetContract.View{

    Unbinder binder;

    @BindView(R.id.toolbar2)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    ResetPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        binder=ButterKnife.bind(this);

        toolbarTitle.setText("个人主页");
        toolbar.setNavigationIcon(R.mipmap.left_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        presenter=new ResetPresenter();
        presenter.attachView(this);

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

    }

    @Override
    public void showLoading() {

    }
}
