package com.ttdb.ttdbsdk.common;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class _Toast {
    private static Context mContext;
    public static void init(Context context){
        mContext=context;
    }

    public static void show(String msg){
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
    public static void show(Throwable throwable){
        Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
