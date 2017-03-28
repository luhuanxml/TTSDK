package com.ttdb.ttdbsdk.mvp.goods;
import com.ttdb.ttdbsdk.common.OkGo;
import com.ttdb.ttdbsdk.common.ParamsUtil;
import com.ttdb.ttdbsdk.common.Sign;
import com.ttdb.ttdbsdk.myapp.TTApplication;
import com.ttdb.ttdbsdk.utils.SP;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

import static com.ttdb.ttdbsdk.utils.Constants.APPID;
import static com.ttdb.ttdbsdk.utils.Constants.DOMAIN;
import static com.ttdb.ttdbsdk.utils.Constants.PAPPID;
import static com.ttdb.ttdbsdk.utils.Constants.PT;
import static com.ttdb.ttdbsdk.utils.Constants.SDK_VER;
import static com.ttdb.ttdbsdk.utils.Constants.SIGN_KEY_TTDB;

public class GoodModelImp implements GoodModel {

    @Override
    public void getGoodList(String page, String type, final Listener listener) {
        HashMap<String, String> params = new HashMap<>();
        params.put("newer", "1");
        params.put("page", page);
        params.put("pt", PT);
        params.put("ver", SDK_VER);
        params.put("pappid",PAPPID);
        params.put("uid", TTApplication.pUserInfo.getUid());
        params.put("imei", SP.get("imei","0"));
        params.put("qyb", "0");
        params.put("appid", APPID);
        params.put("sort", "asc");
        params.put("type", type);
        params.put("device_type", URLEncoder.encode(android.os.Build.MODEL));
        params.put("sign", Sign.getMD5(SIGN_KEY_TTDB));
        String url = DOMAIN + "/index.php?tp=ios/category&op=getlist" + ParamsUtil.provide(params);
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
