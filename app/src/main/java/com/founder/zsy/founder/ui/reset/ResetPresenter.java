package com.founder.zsy.founder.ui.reset;

import com.founder.zsy.founder.api.FoRetrofit;
import com.founder.zsy.founder.bean.LoginEntity;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ResetPresenter extends ResetContract.Presenter {
    @Override
    void resetPwd(Map<String, String> params) {
        FoRetrofit.getRestApi().updatePwd(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginEntity>() {
                    @Override
                    public void accept(LoginEntity loginEntity) throws Exception {
                        mView.resetSuccess(loginEntity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onComplete();
                        throwable.printStackTrace();
                    }
                });
    }
}
