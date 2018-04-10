package com.founder.zsy.founder.bean;

import java.io.Serializable;

/**BDLocation类的简化版
 *
 */
public class LocationBean implements Serializable{

    private String desc; //位置描述
    private String la; // 纬度
    private String ln; // 经度
    private int type; // 定位类型 0 gps , 1 net , 2 offline , 101 102 103 error
    private String time; //
    private String poi;// 位置的poi信息

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPoi() {
        return poi;
    }

    public void setPoi(String poi) {
        this.poi = poi;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "LocationBean{" +
                "desc='" + desc + '\'' +
                ", la='" + la + '\'' +
                ", ln='" + ln + '\'' +
                ", type=" + type +
                ", time='" + time + '\'' +
                ", poi='" + poi + '\'' +
                '}';
    }
}
