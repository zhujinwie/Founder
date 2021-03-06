package com.founder.zsy.founder.bean.base;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PolicyEntity implements Serializable {

    /**
     * create table XH_PERSONALINFO                              保单信息表
     (
     INSURE_NO        VARCHAR2(100) not null,             保单号
     CUST_NO          VARCHAR2(100),                      客户号
     INSURER_NAME     VARCHAR2(30),                       投保人姓名
     INSURER_SEX      VARCHAR2(10),                       投保人性别
     INSURER_BIRTHDAY VARCHAR2(20),                       投保人出生日期
     INSURER_ID       VARCHAR2(20),                       投保人身份证号
     INSURED_NAME     VARCHAR2(30),                       被保险人姓名
     INSURED_SEX      VARCHAR2(10),                       被保险人性别
     INSURED_BIRTHDAY VARCHAR2(20),                       被保险人出生日期
     INSURED_ID       VARCHAR2(20),                       被保险人身份证号
     RELATIONSHIP     VARCHAR2(20)                        投被保险人关系
     )

     *
     * insurer  投保人，客户
     * insured  被保险人
     * agentcode
     * **/

    @SerializedName("INSURER_NAME")
    private String insurerName;

    @SerializedName("CUST_NO")
    private String customerNum;

    @SerializedName("INSURER_ID")
    private String insurerID;

    @SerializedName("INSURED_SEX")
    private String insuredSex;

    @SerializedName("INSURED_NAME")
    private String insuredName;

    @NonNull
    @SerializedName("INSURE_NO")
    private String insureNum;

    @SerializedName("AGENTNAME")
    private String agentName;

    @SerializedName("INSURED_ID")
    private String insuredId;

    @SerializedName("INSURER_SEX")
    private String insurerSex;

    @SerializedName("AGENTCODE")
    private String agentCode;

    @SerializedName("INSURER_BIRTHDAY")
    private String insurerBir;

    @SerializedName("RELATIONSHIP")
    private String relationShip;

    @SerializedName("INSURED_BIRTHDAY")
    private String insuredBir;

    @SerializedName("PAYTODATE")
    private String payToDate;

    @SerializedName("COVERAGE")
    private String coverAge;

    @SerializedName("LASTPAY_RESULT")
    private String lastPayResult;

    @SerializedName("RENEWAL_CARD")
    private String renewalCard;

    @SerializedName("PREMIUM")
    private String premiun;

    @SerializedName("FAIL_REASON")
    private String failReason;

    @SerializedName("INSTYPE_NAME")
    private String instypeName;

    @SerializedName("PAY_DATE")
    private String payDate;

    @SerializedName("INS_STATE")
    private String insState;

    public String getInsState() {
        return insState;
    }

    public void setInsState(String insState) {
        this.insState = insState;
    }

    public String getPayToDate() {
        return payToDate;
    }

    public void setPayToDate(String payToDate) {
        this.payToDate = payToDate;
    }

    public String getCoverAge() {
        return coverAge;
    }

    public void setCoverAge(String coverAge) {
        this.coverAge = coverAge;
    }

    public String getLastPayResult() {
        return lastPayResult;
    }

    public void setLastPayResult(String lastPayResult) {
        this.lastPayResult = lastPayResult;
    }

    public String getRenewalCard() {
        return renewalCard;
    }

    public void setRenewalCard(String renewalCard) {
        this.renewalCard = renewalCard;
    }

    public String getPremiun() {
        return premiun;
    }

    public void setPremiun(String premiun) {
        this.premiun = premiun;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getInstypeName() {
        return instypeName;
    }

    public void setInstypeName(String instypeName) {
        this.instypeName = instypeName;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getInsurerName() {
        return insurerName;
    }

    public void setInsurerName(String insurerName) {
        this.insurerName = insurerName;
    }

    public String getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(String customerNum) {
        this.customerNum = customerNum;
    }

    public String getInsurerID() {
        return insurerID;
    }

    public void setInsurerID(String insurerID) {
        this.insurerID = insurerID;
    }

    public String getInsuredSex() {
        return insuredSex;
    }

    public void setInsuredSex(String insuredSex) {
        this.insuredSex = insuredSex;
    }

    public String getInsuredName() {
        return insuredName;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }

    public String getInsureNum() {
        return insureNum;
    }

    public void setInsureNum(String insureNum) {
        this.insureNum = insureNum;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getInsuredId() {
        return insuredId;
    }

    public void setInsuredId(String insuredId) {
        this.insuredId = insuredId;
    }

    public String getInsurerSex() {
        return insurerSex;
    }

    public void setInsurerSex(String insurerSex) {
        this.insurerSex = insurerSex;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getInsurerBir() {
        return insurerBir;
    }

    public void setInsurerBir(String insurerBir) {
        this.insurerBir = insurerBir;
    }

    public String getRelationShip() {
        return relationShip;
    }

    public void setRelationShip(String relationShip) {
        this.relationShip = relationShip;
    }

    public String getInsuredBir() {
        return insuredBir;
    }

    public void setInsuredBir(String insuredBir) {
        this.insuredBir = insuredBir;
    }

    @Override
    public String toString() {
        return "PolicyEntity{" +
                "insurerName='" + insurerName + '\'' +
                ", customerNum='" + customerNum + '\'' +
                ", insurerID='" + insurerID + '\'' +
                ", insuredSex='" + insuredSex + '\'' +
                ", insuredName='" + insuredName + '\'' +
                ", insureNum='" + insureNum + '\'' +
                ", agentName='" + agentName + '\'' +
                ", insuredId='" + insuredId + '\'' +
                ", insurerSex='" + insurerSex + '\'' +
                ", agentCode='" + agentCode + '\'' +
                ", insurerBir='" + insurerBir + '\'' +
                ", relationShip='" + relationShip + '\'' +
                ", insuredBir='" + insuredBir + '\'' +
                ", payToDate='" + payToDate + '\'' +
                ", coverAge='" + coverAge + '\'' +
                ", lastPayResult='" + lastPayResult + '\'' +
                ", renewalCard='" + renewalCard + '\'' +
                ", premiun='" + premiun + '\'' +
                ", failReason='" + failReason + '\'' +
                ", instypeName='" + instypeName + '\'' +
                ", payDate='" + payDate + '\'' +
                ", insState='" + insState + '\'' +
                '}';
    }
}
