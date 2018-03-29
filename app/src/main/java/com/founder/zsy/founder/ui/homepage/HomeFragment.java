package com.founder.zsy.founder.ui.homepage;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.founder.zsy.founder.R;
import com.founder.zsy.founder.ui.DetailsActivity;
import com.founder.zsy.founder.util.Code;

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
    @BindView(R.id.card)
    EditText card;
    @BindView(R.id.text2)
    TextInputLayout text2;
    @BindView(R.id.select)
    TextView select;
    Unbinder unbinder;
    private String code;

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
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.showCode, R.id.select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.showCode:
                showCode.setImageBitmap(Code.getInstance().createBitmap());
                code = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.select:
                if (text1Edit.getText().toString().trim().length() == 10) {
                    if (codeEdit.getText().toString().trim().equals(code)) {
                       if(card.getText().toString().trim().length()==18){
                            startActivity(new Intent(getContext(),DetailsActivity.class));
                       }else {
                           Toast.makeText(getActivity(), "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
                       }
                    }else {
                        Toast.makeText(getActivity(), "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "请输入的保单号", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void getPolicySuccess() {

    }

    @Override
    public void onError() {

    }
}
