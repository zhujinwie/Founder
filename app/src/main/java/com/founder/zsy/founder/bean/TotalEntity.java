package com.founder.zsy.founder.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TotalEntity implements Serializable {

    @SerializedName("status")
    private int status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("business")
    private Business bus;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Business getBus() {
        return bus;
    }

    public void setBus(Business bus) {
        this.bus = bus;
    }

    @Override
    public String toString() {
        return "TotalEntity{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", bus=" + bus +
                '}';
    }
}
