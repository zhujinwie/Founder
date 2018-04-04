package com.founder.zsy.founder.ui.profile;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.founder.zsy.founder.R;
import com.founder.zsy.founder.bean.base.ProfileEntity;
import com.founder.zsy.founder.util.UserInfoHelper;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    Unbinder binder;

    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.tel_tv)
    TextView telTv;
    @BindView(R.id.email_tv)
    TextView emailTv;
    @BindView(R.id.office_tv)
    TextView officeTv;

    @BindView(R.id.port)
    CircleImageView portaitCiv;

    @BindView(R.id.toolbar2)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        binder= ButterKnife.bind(this);

        toolbarTitle.setText("个人主页");
        toolbar.setNavigationIcon(R.mipmap.left_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ProfileEntity user=UserInfoHelper.getCurrentUser(this);

        if(user!= null) {
            nameTv.setText(TextUtils.isEmpty(user.getAgentName())?"这个人很懒...":user.getAgentName());
            telTv.setText(TextUtils.isEmpty(user.getTel())?"这个人很懒...":user.getTel());
            emailTv.setText(TextUtils.isEmpty(user.getEmail())?"这个人很懒...":user.getEmail());
            officeTv.setText(TextUtils.isEmpty(user.getOffice())?"这个人很懒...":user.getOffice());

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }

    @OnClick(R.id.logout)
    void logout(){

        new AlertDialog.Builder(this)
                .setTitle("是否退出当前账号？")
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                })
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UserInfoHelper.clearUserInfo(ProfileActivity.this);
                        EventBus.getDefault().postSticky("logout!");
                        finish();
                    }
                }).show();


    }

}
