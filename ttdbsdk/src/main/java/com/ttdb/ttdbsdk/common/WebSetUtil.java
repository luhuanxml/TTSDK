package com.ttdb.ttdbsdk.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ttdb.ttdbsdk.utils.LoadingImage;

/**
 * Created by Administrator on 2016/12/19 0019.
 * 用于设置一般的webview的情况
 */

public class WebSetUtil {
    private static volatile WebSetUtil instance;

    private WebSetUtil() {

    }

    public static WebSetUtil getInstance() {
        if (instance == null) {
            synchronized (WebSetUtil.class) {
                if (instance == null) {
                    instance = new WebSetUtil();
                }
            }
        }
        return instance;
    }

    public WebView setScrollBarEnabled(WebView webView, boolean enabled) {
        webView.setHorizontalScrollBarEnabled(enabled);//水平不显示
        webView.setVerticalScrollBarEnabled(enabled); //垂直不显示
        return webView;
    }

    //webSetting 设置
    @SuppressLint("SetJavaScriptEnabled")
    @SuppressWarnings("deprecation")
    public WebSetUtil setWebSetting(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCacheMaxSize(Integer.MAX_VALUE);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("GBK");
        webSettings.setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setUseWideViewPort(true);
        return instance;
    }
    //加载webview时监听
    public WebSetUtil setWebAliLoadingListener(WebView webview,
                                               final Activity mActivity) {
        final LoadingImage loading = new LoadingImage(mActivity);
        webview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,
                                                    String url) {
                try {
                    //微信
                    if (url.contains("weixin://wap/pay?")) {
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        mActivity.startActivity(intent);
                        //支付宝
                    } else if (url.contains("alipays://platformapi/startApp")) {
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        mActivity.startActivity(intent);
                    } else {
                        view.loadUrl(url);
                    }
                } catch (Exception e) {
                    Log.e("sb", "shouldOverrideUrlLoading: " + e);
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view,
                                        WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.e("sb", "onReceivedError: " + error);
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler,
                                           SslError error) {
//                super.onReceivedSslError(view, handler, error);
                Log.e("sb", "onReceivedSslError: " + error);
                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view,
                                      String url,
                                      Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e("sb", "onPageStarted: " + url);
                loading.buildProgressDialog("拼命加载中");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("sb", "onPageFinished: " + url);
                loading.cancelProgressDialog();
            }
        });
        return instance;
    }

    //加载webview时监听
    public WebSetUtil setWebLoadingListener(WebView webview, Context context) {
        final LoadingImage loading = new LoadingImage(context);
        webview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,
                                                    String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view,
                                        WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onPageStarted(WebView view,
                                      String url,
                                      Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loading.buildProgressDialog("拼命加载中");
            }

            @Override
            public void onPageFinished(WebView view,
                                       String url) {
                super.onPageFinished(view, url);
                loading.cancelProgressDialog();
            }
        });
        return instance;
    }

    /**
     * 使用webview的标题为页面头部标题
     * WebChromeClient不要赋予实质对象，会导致重用
     *
     * @param webView  WebView控件
     * @param textView 要设置标题的TextView 控件
     *                 将两个控件对象传入方法中，将webview的标题设置为textview的值
     */
    public WebSetUtil setTitle(WebView webView, final TextView textView) {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, final String title) {
                super.onReceivedTitle(view, title);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (title.length()>6) {
                            textView.setText(title.substring(0,4));
                        }else {
                            textView.setText(title);
                        }
                    }
                });
            }
        });
        return instance;
    }
}
