package com.founder.zsy.founder.ui.homepage;

import java.util.Map;

public class HomeContract {

    interface View {

        void getPolicySuccess();

        void onError();

    }

    interface Presenter{

        void getPolicy(Map<String,String> params);

        void getPolicy(String tel);
    }


}
