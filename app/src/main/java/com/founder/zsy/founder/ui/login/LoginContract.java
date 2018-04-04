package com.founder.zsy.founder.ui.login;

import com.founder.zsy.founder.bean.LoginEntity;
import com.founder.zsy.founder.ui.base.BasePresenter;

import java.util.Map;

public interface LoginContract {

    interface View{

        void loginSuccrss(LoginEntity loginEntity);

        void showError(int code);

        void showLoading();

        void onComplete();
    }

    abstract class Presenter extends BasePresenter<View>{

        abstract  void login(Map<String,String> params);

    }


}
