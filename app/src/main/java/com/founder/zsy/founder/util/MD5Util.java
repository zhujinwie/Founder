package com.founder.zsy.founder.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String getMD5_32_Value(String secret){
        try {
            MessageDigest bmd5 = MessageDigest.getInstance("MD5");
            bmd5.update(secret.getBytes());
            int i;
            StringBuffer buf = new StringBuffer();
            byte[] b = bmd5.digest();// 加密
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";

    }

    public static String getMD5_16_Value(String s){

        return getMD5_32_Value(s).substring(8,24);
    }

}


