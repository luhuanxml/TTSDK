package com.ttdb.ttdbsdk.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ttdb.ttdbsdk.R;
import com.ttdb.ttdbsdk.bean.DetaileList;
import com.ttdb.ttdbsdk.common.WebSetUtil;
import com.ttdb.ttdbsdk.dealjs.DetailedListContactPlugin;
import com.ttdb.ttdbsdk.utils.Constants;
import com.ttdb.ttdbsdk.view.PullToRefreshView;

import static com.ttdb.ttdbsdk.R.id.back;

public class TTDetailedlistFragment extends TTBaseFragment {
    private WebView webview;
    private PullToRefreshView mPullToRefreshView;
    String url;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        if (detaileList != null && !detaileList.getNumber().equals("") && !detaileList.getActId().equals("")) {
            url = Constants.CAR + getParameter() + "&op=add" + "&act_id=" + detaileList.getActId() + "&num=" + detaileList.getNumber();
        } else {
            url = Constants.CAR + getParameter();
        }
    }

    public void getIntentData() {
        Intent intent = getActivity().getIntent();
        if (intent.getSerializableExtra("detaileList") != null) {
            detaileList = (DetaileList) intent.getSerializableExtra("detaileList");
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                webview.loadUrl(Constants.CAR + getParameter());
                webview.setWebViewClient(new WebViewClient() {
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
        webview = (WebView) view.findViewById(R.id.web_detailelist);
        DetailedListContactPlugin detailedlist = new DetailedListContactPlugin(this, getActivity());
        WebSetUtil.getInstance()
                .setWebSetting(webview)
                .setWebLoadingListener(webview, mContext)
                .setScrollBarEnabled(webview, false)
                .addJavascriptInterface(detailedlist, "contact");
        webview.loadUrl(url);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ttdb_fragment_detailelist;
    }


}
