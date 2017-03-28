package com.ttdb.ttdbsdk.mvp.hotsearch;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15 0015.
 */

public interface HotSearchView {
    void showHotSearchList(List<LinkedTreeMap<String,String>> list);
}
