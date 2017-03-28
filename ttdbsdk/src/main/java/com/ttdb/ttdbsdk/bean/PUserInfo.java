package com.ttdb.ttdbsdk.bean;

import java.io.Serializable;

public class PUserInfo implements Serializable{
    private String logo;
    private String username;
    private String phone;
    private String isnew;
    private String ip;
    private String regtype;
    private String appid;
    private String uid;
    private String domain_name;
    private String huodong_url;
    private String car_count;

    public String getCar_count() {
        return car_count;
    }

    public void setCar_count(String car_count) {
        this.car_count = car_count;
    }

    public String getHuodong_url() {
        return huodong_url;
    }

    public void setHuodong_url(String huodong_url) {
        this.huodong_url = huodong_url;
    }

    public String getDomain_name() {
        return domain_name;
    }

    public void setDomain_name(String domain_name) {
        this.domain_name = domain_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsnew() {
        return isnew;
    }

    public void setIsnew(String isnew) {
        this.isnew = isnew;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRegtype() {
        return regtype;
    }

    public void setRegtype(String regtype) {
        this.regtype = regtype;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
