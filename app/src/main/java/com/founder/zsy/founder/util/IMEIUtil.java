package com.founder.zsy.founder.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v13.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.widget.Toast;


public class IMEIUtil {

    private static String[] permissions={Manifest.permission.READ_PHONE_STATE};
    public static String getIMEI(final Activity activity){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if(activity.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){
                TelephonyManager manager= (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);

                return manager.getDeviceId();
            }else{
                //动态申请权限

                new AlertDialog.Builder(activity)
                        .setTitle("读取手机IMEI权限不可用")
                        .setMessage("移动客专需要获取IMEI码信息，以和您的工号相绑定;\n 否侧，您将无法登陆！")
                        .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(activity,permissions,321);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.finish();
                            }
                        })
                        .setCancelable(false)
                        .show();

            }

        }else{

            TelephonyManager manager= (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
            return manager.getDeviceId();
        }
        return null;
    }

}
