package com.ttdb.ttdbsdk.dealjs;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.ttdb.ttdbsdk.activity.TTMainActivity;
import com.ttdb.ttdbsdk.activity.TTWebActivity;
import com.ttdb.ttdbsdk.bean.CarCountEvent;
import com.ttdb.ttdbsdk.bean.GoodsNumEvent;
import com.ttdb.ttdbsdk.bean.ImageEvent;
import com.ttdb.ttdbsdk.common._Picasso;
import com.ttdb.ttdbsdk.common._Toast;
import com.ttdb.ttdbsdk.fragment.TTBaseFragment;
import com.ttdb.ttdbsdk.view.AddCartAnimation;

import org.greenrobot.eventbus.EventBus;

import static com.ttdb.ttdbsdk.utils.Constants.DOMAIN;

/**
 * Created by Administrator on 2016/10/18.
 * 首页fragment处理js的类
 */

public class ClassifyContactPlugin {
    private TTBaseFragment fragment;
    private TTMainActivity mActivity;
    private Handler handler;

    public ClassifyContactPlugin(TTBaseFragment fragment, TTMainActivity activity) {
        this.fragment = fragment;
        this.mActivity = activity;
        handler = new Handler();
    }

    /**
     * 跳转到夺宝详细页面
     * @param act_id 商品id
     */
    @JavascriptInterface
    public void act_detail(String act_id) {
        String url = DOMAIN + "/index.php?tp=front/activity_detail&act_id=" + act_id+mActivity.getParameter(act_id);
        Intent intent = new Intent(mActivity,TTWebActivity.class);
        intent.putExtra("url", url);
        mActivity.startActivity(intent);
    }

    /**
     * 搜索结果列表界面清单点击
     */
    @JavascriptInterface
    public void flycar(final String count, final String img) {
        Log.d("luhuan", "flycar: " + count);
        handler.post(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new ImageEvent(img));
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
        Intent intent = new Intent(mActivity,TTWebActivity.class);
        intent.putExtra("url", url);
        mActivity.startActivity(intent);
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
}
