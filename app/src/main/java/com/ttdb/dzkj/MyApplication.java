package com.ttdb.dzkj;

import android.app.Application;

import com.ttdb.ttdbsdk.common._Picasso;
import com.ttdb.ttdbsdk.common._Toast;
import com.ttdb.ttdbsdk.utils.SP;

/**
 * Created by Administrator on 2016/10/24.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        _Toast.init(getApplicationContext());
        _Picasso.init(getApplicationContext());
        SP.init(getApplicationContext());
    }
}
