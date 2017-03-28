package com.ttdb.ttdbsdk.mvp.hotsearch;

import com.ttdb.ttdbsdk.common.OkGo;
import com.ttdb.ttdbsdk.common.ParamsUtil;
import com.ttdb.ttdbsdk.common.Sign;
import com.ttdb.ttdbsdk.myapp.TTApplication;
import com.ttdb.ttdbsdk.utils.SP;

import java.io.IOException;
import java.util.HashMap;

import static com.ttdb.ttdbsdk.utils.Constants.APPID;
import static com.ttdb.ttdbsdk.utils.Constants.DOMAIN;
import static com.ttdb.ttdbsdk.utils.Constants.PAPPID;
import static com.ttdb.ttdbsdk.utils.Constants.PT;
import static com.ttdb.ttdbsdk.utils.Constants.SDK_VER;
import static com.ttdb.ttdbsdk.utils.Constants.SIGN_KEY_TTDB;

/**
 * Created by Administrator on 2017/3/15 0015.
 */

class HOtSearchModelImp implements HotSearchModel {
    @Override
    public void getHotSearchList(final Listener listener) {
        HashMap<String, String> params = new HashMap<>();
        params.put("appid", APPID);
        params.put("pappid", PAPPID);
        params.put("pt", PT);
        params.put("uid", TTApplication.pUserInfo.getUid());
        params.put("imei", SP.get("imei","0"));
        params.put("ver", SDK_VER);
        params.put("sign", Sign.getMD5(TTApplication.pUserInfo.getUid() + SIGN_KEY_TTDB));

        String url=DOMAIN +"/system.php?tp=gethots"+ ParamsUtil.provide(params);
        OkGo.get(url, new OkGo.OkCallBack() {
            @Override
            public void onSuccess(String s) {
                listener.onSuccees(s);
            }

            @Override
            public void onFailure(IOException e) {
                listener.onFailure(e);
            }
        });
    }
}
