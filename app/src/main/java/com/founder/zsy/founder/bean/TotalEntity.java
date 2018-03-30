package com.founder.zsy.founder.bean;

import com.founder.zsy.founder.bean.base.PolicyEntity;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TotalEntity implements Serializable {

    @SerializedName("status")
    private int status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("business")
    private List<PolicyEntity> bus;

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

    public List<PolicyEntity> getBus() {
        return bus;
    }

    public void setBus(List<PolicyEntity> bus) {
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
