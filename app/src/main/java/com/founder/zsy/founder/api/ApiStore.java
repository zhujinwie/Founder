package com.founder.zsy.founder.api;

import com.founder.zsy.founder.bean.LoginEntity;
import com.founder.zsy.founder.bean.TotalEntity;
import com.founder.zsy.founder.bean.base.MsgEntity;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiStore {

    /**
     * 查询 保单信息
     * @param params  保单号与 身份证号
     * @return 包含状态码的保单信息
     * **/
    @POST("Personal/find.do")
    Flowable<TotalEntity> getPolicy(@QueryMap Map<String,String> params);

    /**
     * 查询 保单信息
     * @param params 投保人姓名 頁碼 頁數
     * @return 包含状态码的保单信息
     * **/
    @POST("Personal/find.do")
    Flowable<TotalEntity> getPolicy02(@QueryMap Map<String,String> params);


    /**
     * 上传 定位坐标
     *
     * @param  params 经纬度，agentId, desc
     * @return
     * **/
    @POST("Agent/getLocation.do")
    Flowable<MsgEntity> uploadLaLn(@QueryMap Map<String,String> params);

    /**
     * 登录
     * @param params
     * **/
    @POST("Agent/login.do")
    Flowable<LoginEntity> login(@QueryMap Map<String,String> params);

    /**
     * 修改密码
     * @param params agentId,password,macId,tel
     * **/
    @POST("Agent/Upassword.do")
    Flowable<LoginEntity> updatePwd(@QueryMap Map<String,String> params);



}
