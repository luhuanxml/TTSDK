package com.ttdb.ttdbsdk.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ttdb.ttdbsdk.activity.TTMainActivity;
import com.ttdb.ttdbsdk.bean.PUserInfo;
import com.ttdb.ttdbsdk.gsonparse.Data;
import com.ttdb.ttdbsdk.gsonparse.LoginInfo;
import com.ttdb.ttdbsdk.myapp.TTApplication;
import com.ttdb.ttdbsdk.utils.SP;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

import static com.ttdb.ttdbsdk.utils.Constants.APPID;
import static com.ttdb.ttdbsdk.utils.Constants.LOADING;
import static com.ttdb.ttdbsdk.utils.Constants.PAPPID;
import static com.ttdb.ttdbsdk.utils.Constants.PT;
import static com.ttdb.ttdbsdk.utils.Constants.SIGN_KEY;

/**
 * Created by luhuan on 2016/10/19.
 * 天天夺宝入口类
 */

public class SDKEntry {
    private Gson gson;
    private Handler handler;
    private PUserInfo pUserInfo;
    private Activity mActivity;
    private String ver;
    private String imei;
    private String uid;

    @SuppressLint("HardwareIds")
    private SDKEntry(Activity mActivity) {
        gson = new Gson();
        handler = new Handler();
        pUserInfo = new PUserInfo();
        this.mActivity = mActivity;
        imei = ((TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        uid = "0";
        try {
            ver = mActivity.getPackageManager().getPackageInfo(this.mActivity.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static SDKEntry getInstance(Activity mActivity) {
        return new SDKEntry(mActivity);
    }

    public void gotoTT(@NonNull final String puid,
                       @NonNull String pusername,
                       @NonNull String plogo,
                       String pphone){
        if (NetStatus.isConnected(mActivity)) {
            get(puid,pusername,plogo,pphone);
        }else {
            Toast.makeText(mActivity, "网络开小差了。。。", Toast.LENGTH_SHORT).show();
        }
    }

    private void get(@NonNull final String puid,
                       @NonNull String pusername,
                       @NonNull String plogo,
                       String pphone) {
        HashMap<String, String> params = new HashMap<>();
        params.put("puid", puid);
        params.put("plogo", plogo);
        params.put("pphone", pphone);
        params.put("pusername", pusername);
        params.put("device_type", URLEncoder.encode(android.os.Build.MODEL));
        params.put("pt", PT);
        params.put("appid", APPID);
        params.put("pappid", PAPPID);
        params.put("uid", uid);
        params.put("imei", imei);
        params.put("ver", ver);
        String sign = Sign.getMD5(uid + imei + PAPPID + puid + SIGN_KEY);
        SP.put("imei",imei);
        String url = LOADING + ParamsUtil.provide(params) + "&sign=" + sign;
        OkGo.get(url, new OkGo.OkCallBack() {
            @Override
            public void onSuccess(String s) {
                LoginInfo loginInfo = gson.fromJson(s, LoginInfo.class);
                if (loginInfo.getStatus().equals("1")) {
                    Data data = loginInfo.getData();
                    pUserInfo.setAppid(data.getAppid());
                    pUserInfo.setIp(data.getIp());
                    pUserInfo.setIsnew(data.getIsnew());
                    pUserInfo.setLogo(data.getLogo());
                    pUserInfo.setPhone(data.getPhone());
                    pUserInfo.setRegtype(data.getRegtype());
                    pUserInfo.setUid(data.getUid());
                    pUserInfo.setDomain_name(data.getDomain_name());
                    pUserInfo.setUsername(data.getUsername());
                    pUserInfo.setHuodong_url(data.getHuodong_url());
                    pUserInfo.setCar_count(data.getCar_count());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            TTApplication.puid = puid;
                            TTApplication.pUserInfo = pUserInfo;
                            Intent intent = new Intent(mActivity, TTMainActivity.class);
                            intent.putExtra("datainfo", TTApplication.pUserInfo);
                            mActivity.startActivity(intent);
                        }
                    });
                } else {
                    _Toast.show(loginInfo.getMsg());
                }
            }

            @Override
            public void onFailure(IOException e) {
                new IOException(e);
            }
        });
    }
}
