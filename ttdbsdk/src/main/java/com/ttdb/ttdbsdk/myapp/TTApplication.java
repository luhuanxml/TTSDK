package com.ttdb.ttdbsdk.myapp;

import android.app.Activity;
import android.app.Application;
import com.ttdb.ttdbsdk.bean.PUserInfo;
import com.ttdb.ttdbsdk.common._Picasso;
import com.ttdb.ttdbsdk.common._Toast;
import com.ttdb.ttdbsdk.utils.SP;

import java.util.LinkedList;
import java.util.List;

public class TTApplication extends Application  {
    private static TTApplication instance;
    public static PUserInfo pUserInfo;
    public static String puid;
    public String imei;

    private List<Activity> activityList = new LinkedList<>();
    // 单例模式获取唯一的MyApplication实例
    public static TTApplication getInstance() {
        if (null == instance) {
            instance = new TTApplication();
        }
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        _Toast.init(getApplicationContext());
        _Picasso.init(getApplicationContext());
        SP.init(getApplicationContext());
        //ShareSDK.initSDK(getApplicationContext(),"7c662114d2b0");
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // 遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }
}
