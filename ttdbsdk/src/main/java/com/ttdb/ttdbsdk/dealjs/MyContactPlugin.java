package com.ttdb.ttdbsdk.dealjs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ttdb.ttdbsdk.R;
import com.ttdb.ttdbsdk.activity.PayActivity;
import com.ttdb.ttdbsdk.activity.TTMainActivity;
import com.ttdb.ttdbsdk.activity.TTWebActivity;
import com.ttdb.ttdbsdk.bean.GoodsNumEvent;
import com.ttdb.ttdbsdk.common.OkGo;
import com.ttdb.ttdbsdk.common.Sign;
import com.ttdb.ttdbsdk.common._Toast;
import com.ttdb.ttdbsdk.fragment.TTMyFragment;
import com.ttdb.ttdbsdk.gsonparse.ShareQQData;
import com.ttdb.ttdbsdk.gsonparse.ShareToQQ;
import com.ttdb.ttdbsdk.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import static com.ttdb.ttdbsdk.utils.Constants.DOMAIN;

public class MyContactPlugin {
    private TTMyFragment fragment;
    private TTMainActivity activity;
    private Context context;
    private Handler handler;

    public MyContactPlugin(TTMyFragment fragment, TTMainActivity activity) {
        this.fragment = fragment;
        this.activity = activity;
        context = activity;
        handler=new Handler();
    }

    /**
     * 充值
     */
    @JavascriptInterface
    public void recharge(){
        String url=DOMAIN + "/index.php?appid=0&tp=front/recharge"+fragment.getParameter();
        Intent intent = new Intent(activity,PayActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }

    /**
     * 跳转到个人信息
     */
    @JavascriptInterface
    public void uinfo() {
        String url = Constants.UINFO + fragment.getParameter();
        activity.startActivity( new Intent(context, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 跳转到我的夺宝记录
     */
    @JavascriptInterface
    public void my_act_record() {
        String url = Constants.MY_ACT_RECORD + fragment.getParameter();
        activity.startActivity(new Intent(context, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 跳转到我的消息
     */
    @JavascriptInterface
    public void news() {
        String url = Constants.NEWS + fragment.getParameter();
        activity.startActivity(new Intent(context, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 跳转到地址管理
     */
    @JavascriptInterface
    public void addr() {
        String url = Constants.MANAGEADD + fragment.getParameter();
        activity.startActivity( new Intent(context, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 跳转到中奖记录
     */
    @JavascriptInterface
    public void award_record() {
        String url = Constants.AWARD_RECORD + fragment.getParameter();
        activity.startActivity( new Intent(context, TTWebActivity.class).putExtra("url", url));
    }

    @JavascriptInterface
    public void qqgroup() {
        String url=DOMAIN+"/index.php?tp=ios/qq&appid=0";
        OkGo.get(url, new OkGo.OkCallBack() {
            @Override
            public void onSuccess(String s) {
                ShareToQQ shareToQQ = new Gson().fromJson(s, ShareToQQ.class);
                ShareQQData shareQQData = shareToQQ.getData();
                final String status=shareToQQ.getStatus();
                final String key = shareQQData.getAndroid();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(status.equals("1")){
                            Intent intent = new Intent();
                            intent.setData(Uri.parse(Constants.SHARETOQQ + key));
                            try {
                                activity.startActivity(intent);
                            } catch (Exception e) {
                                // 未安装手Q或安装的版本不支持
                                Toast.makeText(activity, "未安装手Q或安装的版本不支持", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(activity, "请求失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(IOException e) {

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
