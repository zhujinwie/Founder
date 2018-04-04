package com.founder.zsy.founder.bean.base;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileEntity implements Serializable {

    @SerializedName("office")
    private String office;

    @SerializedName("underdepart")
    private String underDepart;

    @SerializedName("departid")
    private String departid;

    @SerializedName("email")
    private String email;

    @SerializedName("byname")
    private String byName;

    @SerializedName("tel")
    private String tel;

    @SerializedName("createdate")
    private String createDate;

    @SerializedName("subsystem")
    private String subSystem;

    @SerializedName("agenttype")
    private String agentType;

    @SerializedName("agentname")
    private String agentName;

    @SerializedName("lastdate")
    private String lastDate;

    @SerializedName("agentId")
    private String agentId;

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getUnderDepart() {
        return underDepart;
    }

    public void setUnderDepart(String underDepart) {
        this.underDepart = underDepart;
    }

    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getByName() {
        return byName;
    }

    public void setByName(String byName) {
        this.byName = byName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSubSystem() {
        return subSystem;
    }

    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        return "ProfileEntity{" +
                "office='" + office + '\'' +
                ", underDepart='" + underDepart + '\'' +
                ", departid='" + departid + '\'' +
                ", email='" + email + '\'' +
                ", byName='" + byName + '\'' +
                ", tel='" + tel + '\'' +
                ", createDate='" + createDate + '\'' +
                ", subSystem='" + subSystem + '\'' +
                ", agentType='" + agentType + '\'' +
                ", agentName='" + agentName + '\'' +
                ", lastDate='" + lastDate + '\'' +
                ", agentId='" + agentId + '\'' +
                '}';
    }
}

