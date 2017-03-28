package com.ttdb.ttdbsdk.bean;

/**
 * Created by Administrator on 2017/3/17 0017.
 */

public class AliPayBean {
    private String orderInfo;
    private String order_num;
    private String order_sign;
    private String md5_sign;
    private String money;

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getOrder_sign() {
        return order_sign;
    }

    public void setOrder_sign(String order_sign) {
        this.order_sign = order_sign;
    }

    public String getMd5_sign() {
        return md5_sign;
    }

    public void setMd5_sign(String md5_sign) {
        this.md5_sign = md5_sign;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
