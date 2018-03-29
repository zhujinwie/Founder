package com.founder.zsy.founder.ui.mine;

public class MineContract {

    interface View{

        void uploadSuccess();

        void onError();
    }

    abstract class Presenter{

        abstract void uploadLaLn();
    }

}
