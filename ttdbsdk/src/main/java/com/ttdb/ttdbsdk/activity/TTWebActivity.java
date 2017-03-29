package com.ttdb.ttdbsdk.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ttdb.ttdbsdk.R;
import com.ttdb.ttdbsdk.common.OkGo;
import com.ttdb.ttdbsdk.common.WebSetUtil;
import com.ttdb.ttdbsdk.dealjs.WebContactPlugin;
import com.ttdb.ttdbsdk.myapp.TTApplication;
import com.ttdb.ttdbsdk.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import static com.ttdb.ttdbsdk.utils.Constants.DOMAIN;

public class TTWebActivity extends TTBaseActivity implements PlatformActionListener {
    private static final String TAG = "TTWebActivity";
    private String weburl;
    public WebView webview;
    public RelativeLayout relatParent;
    private ImageView ivBack;
    private TextView tvTitle;
    private Handler handler;

    PopupWindow mPopupWindow;

    @SuppressLint("AddJavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ttdb_common);
        handler=new Handler();
        TTApplication.getInstance().addActivity(this);
        Bundle b = getIntent().getExtras();
        weburl = b.getString("url");
        initView();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        WebContactPlugin web=new WebContactPlugin(TTWebActivity.this);
        WebSetUtil.getInstance()
                .setWebSetting(webview)
                .setWebLoadingListener(webview, mContext)
                .setTitle(webview, tvTitle)
                .setScrollBarEnabled(webview, false)
                .addJavascriptInterface(web, "contact");
        Log.d(TAG, "onCreate: "+tvTitle.getText().toString()+"  url="+weburl);
        webview.loadUrl(weburl);
    }

    private void initView(){
        webview = (WebView) findViewById(R.id.webview);
        tvTitle = (TextView) findViewById(R.id.txtTitle);
        relatParent = (RelativeLayout) findViewById(R.id.comLayout);
        ivBack = (ImageView) findViewById(R.id.back);
    }

    public void showPopupWindow(final String parme, String question) {
        @SuppressLint("InflateParams") View popupView = LayoutInflater.from(mContext).inflate(R.layout.ttdb_layout_popupwindow, null);
        popupView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newurl = Constants.NEWS + getParameter() + parme;
                startActivity(new Intent(mContext, TTWebActivity.class).putExtra("url", newurl));
                finish();
            }
        });
        popupView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        ((TextView)popupView.findViewById(R.id.question)).setText(question);
        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ttdb_shape_popup));
        mPopupWindow.showAtLocation(relatParent, Gravity.CENTER, 0, 0);
    }

    //该方法不再UI线程，先切换到UI线程，再去做其他操作，防止线程异常
    @Override
    public void onComplete(final Platform platform, int i, HashMap<String, Object> hashMap) {
        Log.d(TAG, "onComplete: ");
        handler.post(new Runnable() {
            @Override
            public void run() {
                String url= DOMAIN + "/index.php?tp=control/daysign&op=sign_new&type=2" +getParameter("sign_new")+"&&share_type="+platform.getId();
                OkGo.get(url, new OkGo.OkCallBack() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject jsonObj=new JSONObject(s);
                            final String msg=jsonObj.getString("msg");
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(TTWebActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    webview.loadUrl(weburl);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                });
            }
        });

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Log.d(TAG, "onError: ");
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Log.d(TAG, "onCancel: ");
    }
}
