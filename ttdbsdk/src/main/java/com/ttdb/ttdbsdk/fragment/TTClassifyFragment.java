package com.ttdb.ttdbsdk.fragment;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ttdb.ttdbsdk.R;
import com.ttdb.ttdbsdk.activity.TTMainActivity;
import com.ttdb.ttdbsdk.common.ParamsUtil;
import com.ttdb.ttdbsdk.common.Sign;
import com.ttdb.ttdbsdk.common.WebSetUtil;
import com.ttdb.ttdbsdk.dealjs.ClassifyContactPlugin;
import com.ttdb.ttdbsdk.utils.Constants;
import com.ttdb.ttdbsdk.utils.SP;
import com.ttdb.ttdbsdk.view.PullToRefreshView;

import java.net.URLEncoder;
import java.util.HashMap;

import static android.content.ContentValues.TAG;
import static com.ttdb.ttdbsdk.R.id.back;
import static com.ttdb.ttdbsdk.utils.Constants.APPID;
import static com.ttdb.ttdbsdk.utils.Constants.DOMAIN;
import static com.ttdb.ttdbsdk.utils.Constants.PAPPID;
import static com.ttdb.ttdbsdk.utils.Constants.PT;
import static com.ttdb.ttdbsdk.utils.Constants.SDK_VER;
import static com.ttdb.ttdbsdk.utils.Constants.SIGN_KEY_TTDB;

public class TTClassifyFragment extends TTBaseFragment {

    private WebView webView;
    private PullToRefreshView mPullToRefreshView;

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                webView.loadUrl(Constants.MAIN_PAGE + getMainParameter());
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        mPullToRefreshView.onHeaderRefreshComplete();
                    }
                });
            }
        });
        view.findViewById(back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.setIndex(0);
            }
        });
        webView = (WebView) view.findViewById(R.id.web_classify);
        ClassifyContactPlugin classify = new ClassifyContactPlugin(this, (TTMainActivity) getActivity());
        WebSetUtil.getInstance()
                .setWebSetting(webView)
                .setWebLoadingListener(webView, mContext)
                .setScrollBarEnabled(webView, false)
                .addJavascriptInterface(classify, "contact");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_classify;
    }

    @Override
    public void onStart() {
        super.onStart();
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "1");
        params.put("pt", PT);
        params.put("ver", SDK_VER);
        params.put("uid", pdata.getUid());
        params.put("pappid", PAPPID);
        params.put("imei", SP.get("imei","0"));
        params.put("device_type", URLEncoder.encode(android.os.Build.MODEL));
        params.put("qyb", "0");
        params.put("appid", APPID);
        params.put("sign", Sign.getMD5(pdata.getUid() + SIGN_KEY_TTDB));
        String url = DOMAIN + "/index.php?tp=front/category" + ParamsUtil.provide(params);
        Log.d(TAG, "onStart: " + url);
        webView.loadUrl(url);
    }
}
