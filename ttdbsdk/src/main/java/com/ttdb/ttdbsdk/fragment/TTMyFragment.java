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
import com.ttdb.ttdbsdk.common.WebSetUtil;
import com.ttdb.ttdbsdk.dealjs.MyContactPlugin;
import com.ttdb.ttdbsdk.utils.Constants;
import com.ttdb.ttdbsdk.view.PullToRefreshView;

public class TTMyFragment extends TTBaseFragment {
    private WebView webview;
    private PullToRefreshView mPullToRefreshView;


    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mPullToRefreshView= (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                webview.loadUrl(Constants.UCENTER +getParameter());
                webview.setWebViewClient(new WebViewClient(){
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
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.setIndex(0);
            }
        });
        webview= (WebView) view.findViewById(R.id.web_mine);
        MyContactPlugin my=new MyContactPlugin(this, (TTMainActivity) getActivity());
        WebSetUtil.getInstance()
                .setWebSetting(webview)
                .setWebLoadingListener(webview, mContext)
                .setScrollBarEnabled(webview, false)
                .addJavascriptInterface(my, "contact");
        String url=Constants.UCENTER + getParameter();
        Log.d("luhuan", "initView: "+url);
        webview.loadUrl(url);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ttdb_fragment_mine;
    }

}
