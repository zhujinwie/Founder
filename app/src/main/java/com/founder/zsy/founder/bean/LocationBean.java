package com.founder.zsy.founder.bean;

import java.io.Serializable;

/**BDLocation类的简化版
 *
 */
public class LocationBean implements Serializable{


    private String la; // 纬度
    private String ln; // 经度
    private int exception;// 0: OK
    // 1 :server error , 2: network error , 3: phone error

    public String getLa() {
        return la;
    }

    public void setLa(String la) {
        this.la = la;
    }

    public String getLn() {
        return ln;
    }

    public void setLn(String ln) {
        this.ln = ln;
    }

    public int getException() {
        return exception;
    }

    public void setException(int exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "LocationBean{" +
                "la='" + la + '\'' +
                ", ln='" + ln + '\'' +
                ", exception=" + exception +
                '}';
    }
}
