package com.ttdb.dzkj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ttdb.ttdbsdk.common.SDKEntry;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String logo="http://qzapp.qlogo.cn/qzapp/1104628215/17BA5BA7699E18D73C9A71B2263397C2/100";
        SDKEntry.getInstance(this).gotoTT("155781","%e5%be%b7%e9%82%a6%e6%80%bb%e7%ae%a1",logo,"15207158075");
    }
}
