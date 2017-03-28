package com.ttdb.ttdbsdk.dealjs;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.ttdb.ttdbsdk.activity.NewAreaAcitity;
import com.ttdb.ttdbsdk.activity.TTMainActivity;
import com.ttdb.ttdbsdk.activity.TTWebActivity;
import com.ttdb.ttdbsdk.bean.DetaileList;
import com.ttdb.ttdbsdk.bean.GoodsNumEvent;
import com.ttdb.ttdbsdk.bean.ImageEvent;
import com.ttdb.ttdbsdk.common._Toast;
import com.ttdb.ttdbsdk.fragment.TTBaseFragment;
import com.ttdb.ttdbsdk.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import static com.ttdb.ttdbsdk.utils.Constants.ACT_0BUY;
import static com.ttdb.ttdbsdk.utils.Constants.DAYSIGN_NEW;
import static com.ttdb.ttdbsdk.utils.Constants.DOMAIN;
import static com.ttdb.ttdbsdk.utils.Constants.PROM;

/**
 * Created by Administrator on 2016/10/18.
 * 首页fragment处理js的类
 */

public class HomeContactPlugin  {
    private TTBaseFragment fragment;
    private TTMainActivity mActivity;
    private Handler handler;

    public HomeContactPlugin(TTBaseFragment fragment, TTMainActivity activity) {
        this.fragment = fragment;
        this.mActivity = activity;
        handler = new Handler();
    }

    /**
     * 晒单
     */
    @JavascriptInterface
    public void shaidan(){
        String url=DOMAIN +"/index.php?tp=front/shaidan_new&type=1"+mActivity.getParameter();
        mActivity.startActivity(new Intent(mActivity, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 跳转到商品详情
     */
    @JavascriptInterface
    public void act_detail(String act_id) {
        String url = Constants.ACTIVITY_DETAIL + "&act_id=" + act_id + fragment.getParameter(act_id);
        mActivity.startActivity(new Intent(mActivity, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 跳到清单页,在清单页url后加参数"&op=add&act_id={}&num={}"--立即参加
     * 这里不做页面跳转，而做fragment切换
     */
    @JavascriptInterface
    public void add_num(String act_id, String num) {
        final DetaileList detaileList = new DetaileList();
        detaileList.setActId(act_id);
        detaileList.setNumber(num);
        handler.post(new Runnable() {
            @Override
            public void run() {
                TTBaseFragment.detaileList = detaileList;
                mActivity.setIndex(3);
            }
        });
    }

    /**
     * 搜索结果列表界面清单点击
     */
    @JavascriptInterface
    public void fly( final String img) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new ImageEvent(img));
            }
        });
    }

    /**
     * 新手专区
     */
    @JavascriptInterface
    public void xinshou() {
        Intent intent=new Intent(mActivity, NewAreaAcitity.class);
        mActivity.startActivity(intent);
    }

    /**
     * 签到
     */
    @JavascriptInterface
    public void qiandao() {
        String url = DAYSIGN_NEW + fragment.getParameter();
        Intent intent = new Intent(mActivity, TTWebActivity.class);
        intent.putExtra("url", url);
        mActivity.startActivity(intent);
    }

    /**
     * 热门活动
     */
    @JavascriptInterface
    public void huodong() {
        String url = ACT_0BUY + fragment.getParameter();
        Intent intent = new Intent(mActivity, TTWebActivity.class);
        intent.putExtra("url", url);
        mActivity.startActivity(intent);
    }

    /**
     * 招募徒弟
     */
    @JavascriptInterface
    public void zhaomu() {
        String url = PROM + fragment.getParameter();
        Intent intent = new Intent(mActivity, TTWebActivity.class);
        intent.putExtra("url", url);
        mActivity.startActivity(intent);
    }

    /**
     * 最新揭晓全部
     */
    @JavascriptInterface
    public void zuixin() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mActivity.setIndex(1);
            }
        });

    }

    /**
     * 热门商品全部
     */
    @JavascriptInterface
    public void remen() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mActivity.setIndex(2);
            }
        });
    }

    /**
     * 大眼睛
     */
    @JavascriptInterface
    public void html5(String url,String title){
        Intent intent = new Intent(mActivity, TTWebActivity.class);
        intent.putExtra("url", url);
        mActivity.startActivity(intent);
    }

    /**
     * 弹出提示框
     */
    @JavascriptInterface
    public void showToast(String msg) {
        _Toast.show(msg);
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
