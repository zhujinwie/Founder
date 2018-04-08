package com.founder.zsy.founder.ui.homepage;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.founder.zsy.founder.R;
import com.founder.zsy.founder.bean.TotalEntity;
import com.founder.zsy.founder.ui.DetailsActivity;
import com.founder.zsy.founder.ui.login.LoginActivity;
import com.founder.zsy.founder.util.Code;
import com.founder.zsy.founder.util.MD5Util;
import com.founder.zsy.founder.util.UserInfoHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeContract.View{


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.text1_edit)
    EditText text1Edit;
    @BindView(R.id.text1)
    TextInputLayout text1;
    @BindView(R.id.code_edit)
    EditText codeEdit;
    @BindView(R.id.code)
    TextInputLayout codes;
    @BindView(R.id.showCode)
    ImageView showCode;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.select)
    TextView select;
    @BindView(R.id.policy_style)
    TextView policyTv;
    @BindView(R.id.name_style)
    TextView nameTv;
    @BindView(R.id.paw1)
    ImageView pawTv;
    @BindView(R.id.code_rl)
    RelativeLayout codeRl;
    @BindView(R.id.policy_rl)
    RelativeLayout policyRl;
    @BindView(R.id.name_rl)
    RelativeLayout nameRl;
    @BindView(R.id.text2_edit)
    EditText text2Edit;
    Unbinder unbinder;
    private String code,name,policyNum;
    private HomePresenter presenter;
    private Map<String,String> params;
    private int style;// 0: 保单查询 ， 1:姓名查询
    private int[] titleColors={R.color.colorAccent,R.color.white};
    private int[] backGrounds={R.drawable.tv_left_cornor_red,R.drawable.tv_left_cornor_white,R.drawable.tv_right_cornor_red,R.drawable.tv_right_cornor_white};
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        showCode.setImageBitmap(Code.getInstance().createBitmap());
        code = Code.getInstance().getCode().toLowerCase();
        toolbarTitle.setText("首页");
        presenter=new HomePresenter();
        presenter.attachView(this);
        params=new HashMap<>();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
    }

    @OnClick({R.id.showCode, R.id.select,R.id.name_style,R.id.policy_style})
    public void onViewClicked(View view) {

        if(!UserInfoHelper.isLogin(getContext())){

            new AlertDialog.Builder(getContext())
                    .setTitle("请先登录！")
                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    })
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                    }).show();
        }


        switch (view.getId()) {
            case R.id.name_style:

                policyRl.setVisibility(View.GONE);
                nameRl.setVisibility(View.VISIBLE);
                nameTv.setTextColor(getContext().getResources().getColor(titleColors[1]));
                nameTv.setBackgroundResource(backGrounds[2]);
                policyTv.setTextColor(getContext().getResources().getColor(titleColors[0]));
                policyTv.setBackgroundResource(backGrounds[1]);
                style=1;
                break;
            case R.id.policy_style:

                nameRl.setVisibility(View.GONE);
                policyRl.setVisibility(View.VISIBLE);
                nameTv.setTextColor(getContext().getResources().getColor(titleColors[0]));
                nameTv.setBackgroundResource(backGrounds[3]);
                policyTv.setTextColor(getContext().getResources().getColor(titleColors[1]));
                policyTv.setBackgroundResource(backGrounds[0]);

                style=0;
                break;
            case R.id.showCode:

                showCode.setImageBitmap(Code.getInstance().createBitmap());
                code = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.select:

                if(style == 0){

                    if (text1Edit.getText().toString().trim().length() != 0) {
                        if (codeEdit.getText().toString().trim().equals(code)) {
                            showLoading();
                            name="-1";
                            policyNum=text1Edit.getText().toString().trim();
                            policyNum=MD5Util.getMD5_32_Value(policyNum);
                            params=new HashMap<>();
                            params.put("insureNo", policyNum);
                            presenter.getPolicy(params);
                        }else {
                            Toast.makeText(getActivity(), "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "请输入保单号", Toast.LENGTH_SHORT).show();
                    }
                }else{

                    if(text2Edit.getText().toString().trim().length() != 0){
                        if(codeEdit.getText().toString().trim().equals(code)){
                            showLoading();
                            policyNum="-1";
                            name=text2Edit.getText().toString().trim();
                            name=MD5Util.getMD5_32_Value(name);
                            params=new HashMap<>();
                            params.put("page","1");
                            params.put("page_size","10");
                            params.put("insurerName",name);
                            presenter.getPolicy02(params);
                        }else{
                            Toast.makeText(getActivity(),"请输入正确的验证码",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "请输入姓名", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void getPolicySuccess(TotalEntity totalEntity) {

        onComplete();
        Intent intent=new Intent(getContext(), DetailsActivity.class);
        Log.d("Test","intent.num = "+policyNum+" ; intent.name= "+name);
        intent.putExtra(DetailsActivity.EXTRA_CODE,policyNum);
        intent.putExtra(DetailsActivity.EXTRA_NAME,name);
        startActivity(intent);
    }

    @Override
    public void showError(int code) {

        Log.d("Home","界面报错了！...");
        if(code == 1){
            //没有内容
            Toast.makeText(getContext(), "保单号/姓名输入错误！", Toast.LENGTH_SHORT).show();
        }
        if(code == 2){
            //账号异常
            Toast.makeText(getContext(), "服务器错误！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        nameTv.setClickable(false);
        policyTv.setClickable(false);
    }

    @Override
    public void onComplete() {
        progressBar.setVisibility(View.INVISIBLE);
        nameTv.setClickable(true);
        policyTv.setClickable(true);
    }

}
