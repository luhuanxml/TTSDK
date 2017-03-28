package com.ttdb.ttdbsdk.mvp.goods;


import com.ttdb.ttdbsdk.mvp.BaseModel;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public interface GoodModel extends BaseModel {
    void getGoodList(String page, String type, Listener listener);
}
