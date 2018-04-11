package com.founder.zsy.founder.ui.login;

import android.text.TextUtils;

import com.founder.zsy.founder.api.FoRetrofit;
import com.founder.zsy.founder.bean.LoginEntity;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends LoginContract.Presenter {

    @Override
    void login(Map<String, String> params) {

        FoRetrofit.getRestApi().login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginEntity>() {
                    @Override
                    public void accept(LoginEntity loginEntity) throws Exception {
                        mView.loginSuccrss(loginEntity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onComplete();
                        throwable.printStackTrace();
                        mView.showError(TextUtils.isEmpty(throwable.getMessage())?"消失在异次元了...":throwable.getMessage());
                        }
                });
    }
}
