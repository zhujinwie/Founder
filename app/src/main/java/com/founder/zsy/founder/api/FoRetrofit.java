package com.founder.zsy.founder.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoRetrofit {

    public static final int TIME_OUT=15;

    private static final String BASE_URL="http://192.168.0.122:8080/XinhuaInsurance/";

    private static Retrofit mRestRetrofit;

    public static synchronized ApiStore getRestApi(){

        if(mRestRetrofit == null ){

            OkHttpClient.Builder builder=new OkHttpClient.Builder();
            builder.retryOnConnectionFailure(false)
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT,TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT,TimeUnit.SECONDS);


            mRestRetrofit= new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(BASE_URL)
                            .build();

        }

        return mRestRetrofit.create(ApiStore.class);
    }


}
