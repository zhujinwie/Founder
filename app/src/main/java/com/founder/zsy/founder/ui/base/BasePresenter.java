package com.founder.zsy.founder.ui.base;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V>   {

    private WeakReference<V> mWeakRef;

    public V mView;

    protected  void attachView(V v){
        mWeakRef=new WeakReference<>(v);
        mView=mWeakRef.get();
    }

    protected  void detachView(){

        if(mWeakRef!=null){
            mWeakRef.clear();
            mWeakRef=null;
        }
    }





}
