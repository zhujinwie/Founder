package com.founder.zsy.founder.bean;

import com.founder.zsy.founder.bean.base.ProfileEntity;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginEntity implements Serializable {

    @SerializedName("status")
    private int status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("business")
    private ProfileEntity profile;

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

    public ProfileEntity getProfile() {
        return profile;
    }

    public void setProfile(ProfileEntity profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "LoginEntity{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", profile=" + profile +
                '}';
    }
}
