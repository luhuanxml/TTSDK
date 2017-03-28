package com.ttdb.ttdbsdk.mvp.goods;

import com.ttdb.ttdbsdk.bean.GoodEntity;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public interface GoodView {
    void showGoodList(GoodEntity goodEntity);
    void loadMoreGoodList(GoodEntity goodEntity);
}
