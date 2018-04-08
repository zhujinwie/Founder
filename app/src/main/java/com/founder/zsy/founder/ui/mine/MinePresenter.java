package com.founder.zsy.founder.ui.mine;

import com.founder.zsy.founder.api.FoRetrofit;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MinePresenter extends MineContract.Presenter{

    @Override
    void uploadLaLn(Map<String,String> params) {

        FoRetrofit.getRestApi().uploadLaLn(params).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mView.uploadSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if(throwable.getMessage().contains("IllegalArgumentException") || throwable.getMessage().contains("IllegalStateException"))
                            return;
                        else if(throwable.getMessage().contains("404"))
                            mView.onError(0);
                        throwable.printStackTrace();
                    }
                });
    }
}
