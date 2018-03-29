package com.founder.zsy.founder.bean;

import com.founder.zsy.founder.bean.base.PolicyEntity;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

public class Business implements Serializable {

    @SerializedName("list")
    private PolicyEntity[] list;

    public PolicyEntity[] getList() {
        return list;
    }

    public void setList(PolicyEntity[] list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Business{" +
                "list=" + Arrays.toString(list) +
                '}';
    }
}
