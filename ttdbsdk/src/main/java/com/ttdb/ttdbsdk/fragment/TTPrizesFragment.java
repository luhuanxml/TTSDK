package com.ttdb.ttdbsdk.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ttdb.ttdbsdk.R;
import com.ttdb.ttdbsdk.activity.TTMainActivity;
import com.ttdb.ttdbsdk.common.WebSetUtil;
import com.ttdb.ttdbsdk.dealjs.PrizesContactPlugn;
import com.ttdb.ttdbsdk.utils.Constants;
import com.ttdb.ttdbsdk.view.PullToRefreshView;

import static com.ttdb.ttdbsdk.R.id.back;

public class TTPrizesFragment extends TTBaseFragment {
    private WebView webView;
    private PullToRefreshView mPullToRefreshView;
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mPullToRefreshView= (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                webView.loadUrl(Constants.ACTIVITY_NEW +getParameter());
                webView.setWebViewClient(new WebViewClient(){
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
            };
        });
        view.findViewById(back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.setIndex(0);
            }
        });
        webView = (WebView) view.findViewById(R.id.web_prizes);
        WebSettings webSet = webView.getSettings();
        webSet.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new PrizesContactPlugn(this, (TTMainActivity) getActivity()), "contact");
        PrizesContactPlugn prizes=new PrizesContactPlugn(this, (TTMainActivity) getActivity());
        WebSetUtil.getInstance()
                .setWebSetting(webView)
                .setWebLoadingListener(webView, mContext)
                .setScrollBarEnabled(webView, false)
                .addJavascriptInterface(prizes, "contact");
    }

    @Override
    public void onStart() {
        super.onStart();
        String url=Constants.ACTIVITY_NEW +getParameter();
        webView.loadUrl(url);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ttdb_fragment_prizes;
    }


}
