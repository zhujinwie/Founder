package com.founder.zsy.founder.ui.homepage;

import com.founder.zsy.founder.bean.TotalEntity;
import com.founder.zsy.founder.ui.base.BasePresenter;

import java.util.Map;

public interface HomeContract {

    interface View {

        void getPolicySuccess(TotalEntity totalEntity);

        void showError(int code);

        void showLoading();
    }

    abstract class Presenter extends BasePresenter<View>{

        abstract void getPolicy(Map<String,String> params);

        abstract void getPolicy02(Map<String,String> params);
    }


}
