package com.founder.zsy.founder.ui.mine;

import com.founder.zsy.founder.ui.base.BasePresenter;

import java.util.Map;

public interface MineContract {

    interface View{

        void uploadSuccess();

        void onError(int code);
    }

    abstract class Presenter extends BasePresenter<View>{

        abstract void uploadLaLn(Map<String,String> params);
    }

}
