package com.founder.zsy.founder.ui.homepage;

import com.founder.zsy.founder.api.FoRetrofit;
import com.founder.zsy.founder.bean.TotalEntity;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends HomeContract.Presenter{

    @Override
    public void getPolicy(Map<String,String> params) {

        FoRetrofit.getRestApi().getPolicy(params).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<TotalEntity>() {
                    @Override
                    public void accept(TotalEntity totalEntity) throws Exception {
                        mView.getPolicySuccess(totalEntity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }

    @Override
    public void getPolicy02(Map<String ,String> params) {

        FoRetrofit.getRestApi().getPolicy02(params).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<TotalEntity>() {
                    @Override
                    public void accept(TotalEntity totalEntity) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }




}
