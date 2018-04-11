package com.founder.zsy.founder.util;

import android.content.Context;
import android.text.TextUtils;

import java.util.UUID;

public class IDUtil {

    public String getUUID(Context context){

        String uuid="";

        if(SharedPreferencesUtils.contains(context,"uuid")){
            uuid= (String) SharedPreferencesUtils.get(context,"uuid","");
        }

       if(TextUtils.isEmpty(uuid)){

            uuid= UUID.randomUUID().toString();
            SharedPreferencesUtils.put(context,"uuid",uuid);
       }

       return uuid;
    }

}
