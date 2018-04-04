package com.founder.zsy.founder.ui.reset;

import com.founder.zsy.founder.bean.LoginEntity;
import com.founder.zsy.founder.ui.base.BasePresenter;

import java.util.Map;

public interface ResetContract {

    interface View{

        void resetSuccess(LoginEntity loginEntity);

        void showError(int code);

        void showLoading();
    }

    abstract class Presenter extends BasePresenter<View>{

        abstract void resetPwd(Map<String,String> params);
    }



}
