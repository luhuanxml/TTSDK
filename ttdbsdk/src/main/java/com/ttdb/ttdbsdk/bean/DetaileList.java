package com.ttdb.ttdbsdk.bean;

import java.io.Serializable;

public class DetaileList implements Serializable{
    private String actId;
    private String number;

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
