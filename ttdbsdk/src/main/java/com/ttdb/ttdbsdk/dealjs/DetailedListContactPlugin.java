package com.ttdb.ttdbsdk.dealjs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.ttdb.ttdbsdk.activity.TTMainActivity;
import com.ttdb.ttdbsdk.activity.TTWebActivity;
import com.ttdb.ttdbsdk.bean.GoodsNumEvent;
import com.ttdb.ttdbsdk.common._Toast;
import com.ttdb.ttdbsdk.fragment.TTBaseFragment;
import com.ttdb.ttdbsdk.fragment.TTDetailedlistFragment;
import com.ttdb.ttdbsdk.utils.Constants;

import org.greenrobot.eventbus.EventBus;

public class DetailedListContactPlugin{
    private Handler handler;
    private TTBaseFragment fragment;
    private TTMainActivity activity;
    private Context context;

    public DetailedListContactPlugin(TTDetailedlistFragment fragment, Activity activity) {
        this.fragment = fragment;
        this.activity = (TTMainActivity) activity;
        context = activity;
        handler = new Handler();
    }

    /**
     * 跳转到商品详情
     */
    @JavascriptInterface
    public void act_detail(String act_id) {
        String url = Constants.ACTIVITY_DETAIL + "&act_id=" + act_id + fragment.getParameter(act_id);
        activity.startActivity(new Intent(context, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 随便看看，关闭所有窗口，显示首页
     */
    @JavascriptInterface
    public void gosee() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                activity.setIndex(0);
            }
        });
    }

    /**
     * 跳转到结算页面
     */
    @JavascriptInterface
    public void checkout() {
        String url = Constants.PAY + fragment.getParameter();
        activity.startActivity(new Intent(context, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 跳转到计算详情
     */
    @JavascriptInterface
    public void calc_detal(String act_id) {
        String url = Constants.ACTIVITY_DETAIL_CALC + fragment.getParameter(act_id);
        activity.startActivity( new Intent(context, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 清单数量
     */
    @JavascriptInterface
    public void show_number(final String num){
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("", "show_number: "+num);
                EventBus.getDefault().post(new GoodsNumEvent(num));
            }
        });
    }

    /**
     * 用于toast提示
     */
    @JavascriptInterface
    public void showToast(String msg) {
        _Toast.show(msg);
    }

    @JavascriptInterface
    public void html5(String url, String title) {
        Intent intent = new Intent(activity,TTWebActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }
}
