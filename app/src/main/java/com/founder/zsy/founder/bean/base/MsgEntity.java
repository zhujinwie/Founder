package com.founder.zsy.founder.bean.base;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MsgEntity implements Serializable{
    @SerializedName("status")
    private int status;

    @SerializedName("msg")
    private String msg;

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

    @Override
    public String toString() {
        return "MsgEntity{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
