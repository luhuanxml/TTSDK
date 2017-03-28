package com.ttdb.ttdbsdk.common;
import java.util.HashMap;
import java.util.Iterator;

public class ParamsUtil {
    public static String provide(HashMap<String,String> params) {
        String url = "";//添加url参数
        if (params != null) {
            Iterator<String> it = params.keySet().iterator();
            StringBuilder sb = new StringBuilder();
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                sb.append("&").append(key).append("=").append(value);
            }
            url += sb.toString();
        }
        return url;
    }
}
