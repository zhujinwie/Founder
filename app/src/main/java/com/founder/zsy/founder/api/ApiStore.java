package com.founder.zsy.founder.api;

import com.founder.zsy.founder.bean.TotalEntity;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiStore {

    /**
     * 查询 保单信息
     * @param params  保单号与 身份证号
     * @return 包含状态码的保单信息
     * **/
    @FormUrlEncoded
    @POST("find")
    Flowable<TotalEntity> getPolicy(@FieldMap Map<String,String> params);

    /**
     * 查询 保单信息
     * @param tel 电话号
     * @return 包含状态码的保单信息
     * **/
    @FormUrlEncoded
    @POST("find")
    Flowable<TotalEntity> getPolicy(@Field("TEL") String tel);


    /**
     * 上传 定位坐标
     *
     * @param  params 经纬度
     * @return
     * **/
    @FormUrlEncoded
    @POST("getLocation")
    Flowable<String> uploadLaLn(@FieldMap Map<String,String> params);


}
