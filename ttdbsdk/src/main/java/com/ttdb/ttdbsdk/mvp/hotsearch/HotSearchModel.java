package com.ttdb.ttdbsdk.mvp.hotsearch;

import com.ttdb.ttdbsdk.mvp.BaseModel;

/**
 * Created by Administrator on 2017/3/15 0015.
 */

public interface HotSearchModel extends BaseModel {
    void getHotSearchList(Listener listener);
}
