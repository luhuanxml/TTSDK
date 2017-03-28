package com.ttdb.ttdbsdk.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ttdb.ttdbsdk.R;
import com.ttdb.ttdbsdk.activity.SearchActivity;
import com.ttdb.ttdbsdk.activity.TTMainActivity;
import com.ttdb.ttdbsdk.common.WebSetUtil;
import com.ttdb.ttdbsdk.dealjs.HomeContactPlugin;
import com.ttdb.ttdbsdk.utils.Constants;
import com.ttdb.ttdbsdk.utils.SP;
import com.ttdb.ttdbsdk.view.PullToRefreshView;

import static android.content.ContentValues.TAG;

public class TTHomeFragment extends TTBaseFragment implements View.OnClickListener{
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
        view.findViewById(R.id.back).setOnClickListener(this);
        view.findViewById(R.id.search).setOnClickListener(this);
        webView = (WebView) view.findViewById(R.id.web_home);
        HomeContactPlugin home = new HomeContactPlugin(this, (TTMainActivity) getActivity());
        WebSetUtil.getInstance()
                .setWebSetting(webView)
                .setWebLoadingListener(webView, mContext)
                .setScrollBarEnabled(webView, false)
                .addJavascriptInterface(home, "contact");
    }

    @Override
    public void onStart() {
        super.onStart();
        String url=Constants.MAIN_PAGE + getMainParameter();
        Log.d("luhuan1", "onStart: "+url);
        webView.loadUrl(url);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.ttdb_fragment_home;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            mActivity.finish();
        }else if (v.getId() == R.id.search) {
            SP.put("good_count",mActivity.getGoodCount());
            Intent intent=new Intent(mActivity, SearchActivity.class);
            startActivity(intent);
        }
    }
}
