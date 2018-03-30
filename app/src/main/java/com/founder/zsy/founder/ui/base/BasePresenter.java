package com.founder.zsy.founder.ui.base;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V>   {

    private WeakReference<V> mWeakRef;

    public V mView;

    public  void attachView(V v){
        mWeakRef=new WeakReference<>(v);
        mView=mWeakRef.get();
    }

    public  void detachView(){

        if(mWeakRef!=null){
            mWeakRef.clear();
            mWeakRef=null;
        }
    }

    public boolean isViewAttach(){

        return mWeakRef != null && mWeakRef.get() != null;
    }

    public V getView(){

        return mWeakRef == null ? null : mWeakRef.get();
    }








}
