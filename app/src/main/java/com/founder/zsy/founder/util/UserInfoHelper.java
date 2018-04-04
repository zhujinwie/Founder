package com.founder.zsy.founder.util;

import android.content.Context;

import com.founder.zsy.founder.bean.base.ProfileEntity;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by 祝锦伟 on 2018/4/2.
 */

public class UserInfoHelper {

    public static final String KEY_CURRENT_USER="user";

    public static final String CURRENT_USER="current_user";

    private static Map<String,ProfileEntity> userEntityMap=new HashMap<>();

    public static ProfileEntity getCurrentUser(Context context){

        ProfileEntity currentUser=userEntityMap.get(KEY_CURRENT_USER);

        if(currentUser!=null) return currentUser;

        ProfileEntity user= SharedPreferencesUtils.getObject(context,CURRENT_USER,ProfileEntity.class);

        if(user ==null) return null;

        userEntityMap.put(KEY_CURRENT_USER,user);

        return user;

    }

    public static void clearUserInfo(Context context){

        userEntityMap.clear();
        SharedPreferencesUtils.putObject(context,CURRENT_USER,null);
    }

    public static void setUserInfo(Context context,ProfileEntity userInfo){

        userEntityMap.put(KEY_CURRENT_USER,userInfo);
        SharedPreferencesUtils.putObject(context,CURRENT_USER,userInfo);

    }

    public static boolean isLogin(Context context){

        return UserInfoHelper.getCurrentUser(context)!=null;

    }



}
