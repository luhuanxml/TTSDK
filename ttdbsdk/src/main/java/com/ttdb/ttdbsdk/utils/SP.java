package com.ttdb.ttdbsdk.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by luhuan on 2017/3/20 0020.
 * SP存储 String 和 List;
 */

public class SP {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static SharedPreferences sp;
    private static String spName = "SP_DATA";

    public static void init(@NonNull Context context) {
        mContext = context;
        sp = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    public static void put(@NonNull String key, @NonNull String value) {
        if (mContext == null) {
            throw new RuntimeException("请先调用带有context，name参数的构造！");
        }
        sp.edit().putString(key, value).apply();
    }

    public static String get(@NonNull String key, @NonNull String defaultValue) {
        if (mContext == null) {
            throw new RuntimeException("请先调用带有context，name参数的构造！");
        }
        return sp.getString(key, defaultValue);
    }

    public static void putAll(String key, List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        sp.edit().putString(key, json).apply();
    }

    public static List<String> getAll(@NonNull String key, @NonNull List<String> defautList) {
        Gson gson = new Gson();
        List<String> list;
        String json = sp.getString(key, "0");
        if (!json.equals("0")) {
            list = gson.fromJson(json, new TypeToken<List<String>>() {
            }.getType());
            return list;
        } else {
            return defautList;
        }
    }

    public static void delete(@NonNull String key) {
        if (sp.contains(key)) {
            sp.edit().remove(key).apply();
        }
    }
}
