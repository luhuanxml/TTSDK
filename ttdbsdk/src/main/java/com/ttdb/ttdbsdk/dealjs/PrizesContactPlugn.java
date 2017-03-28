package com.ttdb.ttdbsdk.dealjs;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.webkit.JavascriptInterface;

import com.ttdb.ttdbsdk.activity.TTMainActivity;
import com.ttdb.ttdbsdk.activity.TTWebActivity;
import com.ttdb.ttdbsdk.bean.GoodsNumEvent;
import com.ttdb.ttdbsdk.common._Toast;
import com.ttdb.ttdbsdk.fragment.TTBaseFragment;
import com.ttdb.ttdbsdk.fragment.TTPrizesFragment;
import com.ttdb.ttdbsdk.utils.Constants;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/10/18.
 * 最新揭晓fragment处理js的类
 */

public class PrizesContactPlugn  {
    private TTBaseFragment fragment;
    private TTMainActivity activity;
    private Context context;
    private Handler handler;
    public PrizesContactPlugn(TTPrizesFragment fragment, TTMainActivity activity) {
        this.fragment=fragment;
        this.activity=activity;
        context=activity;
        handler=new Handler();
    }

    /**
     *   跳转到商品详情
     */
    @JavascriptInterface
    public void act_detail(String act_id){
        String url= Constants.ACTIVITY_DETAIL+"&act_id="+act_id+fragment.getParameter(act_id);
        activity.startActivity(new Intent(context, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 清单数量
     */
    @JavascriptInterface
    public void show_number(final String num){
        handler.post(new Runnable() {
            @Override
            public void run() {
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
