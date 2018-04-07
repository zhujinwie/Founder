package com.founder.zsy.founder.util;

import java.security.MessageDigest;

/**
 * MD5 加密类
 * 用于登陆，保单号查询加密
 *
 * **/
public class MD5Util {

    /**
     *@Description: MD5加密 32位小写
     * **/
    public static String encrypt32(String encryptStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            encryptStr = hexValue.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encryptStr;
    }

    /**
     * @Description: MD5加密 16位小写
     * **/
    public static String encrypt16(String encryptStr){
        return encrypt32(encryptStr).substring(8,24);
    }




}
