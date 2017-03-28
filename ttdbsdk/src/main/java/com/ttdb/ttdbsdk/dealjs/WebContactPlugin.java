package com.ttdb.ttdbsdk.dealjs;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.webkit.JavascriptInterface;

import com.ttdb.ttdbsdk.activity.TTMainActivity;
import com.ttdb.ttdbsdk.activity.TTWebActivity;
import com.ttdb.ttdbsdk.bean.DetaileList;
import com.ttdb.ttdbsdk.bean.GoodsNumEvent;
import com.ttdb.ttdbsdk.common._Toast;
import com.ttdb.ttdbsdk.myapp.TTApplication;
import com.ttdb.ttdbsdk.utils.Constants;
import com.ttdb.ttdbsdk.utils.Share;

import org.greenrobot.eventbus.EventBus;

import static com.ttdb.ttdbsdk.utils.Constants.DAYSIGN_NEW;
import static com.ttdb.ttdbsdk.utils.Constants.DOMAIN;

public class WebContactPlugin  {

    private TTWebActivity activity;
    private Handler handler;
    public WebContactPlugin(Activity activity) {
        this.activity = (TTWebActivity) activity;
        handler = new Handler();
    }


    /**
     * 跳转到商品详情
     */
    @JavascriptInterface
    public void act_detail(String act_id) {
        String url = Constants.ACTIVITY_DETAIL + "&act_id=" + act_id + activity.getParameter(act_id);
        activity.startActivity(new Intent(activity, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 跳到计算详情
     */
    @JavascriptInterface
    public void calc_detal(String act_id) {
        String url = Constants.ACTIVITY_DETAIL_CALC + activity.getParameter(act_id);
        activity.startActivity(new Intent(activity, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 跳转到图文详情
     */
    @JavascriptInterface
    public void pic_detail(String act_id) {
        String url = Constants.ACTIVITY_DETAIL_INTRO + "&act_id=" + act_id + activity.getParameter();
        activity.startActivity(new Intent(activity, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 跳转到他人夺宝记录
     */
    @JavascriptInterface
    public void user_record(String cid) {
        String url = Constants.USER_RECORD + activity.getParams(cid);
        activity.startActivity(new Intent(activity, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 跳转到结果页面
     */
    @JavascriptInterface
    public void pay_handler(String hb_ids) {
        String url = Constants.PAY_HANDLER + activity.getParameter(hb_ids) + "&hbids=" + hb_ids;
        activity.startActivity(new Intent(activity, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 关闭所有窗口，显示首页
     */
    @JavascriptInterface
    public void act_duobao() {
        Intent intent = new Intent(activity, TTMainActivity.class);
        intent.putExtra("datainfo", TTApplication.pUserInfo);
        activity.startActivity(intent);
        TTApplication.getInstance().exit();
    }

    /**
     * 立即抢购
     *
     * @param act_id 商品id
     */
    @JavascriptInterface
    public void oneprice_order(String act_id) {
        String url = DOMAIN + "/index.php?tp=front/confirm_order" + "&act_id=" + act_id + activity.getParameter();
        Intent intent = new Intent(activity, TTWebActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);

    }

    /**
     * 跳转到显示全部号码
     */
    @JavascriptInterface
    public void showmorenum(String act_id, String orderid, String m_id) {
        String url = Constants.RESULT_MORENUM + activity.getParameter();
        activity.startActivity(new Intent(activity, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 跳到消息详细
     */
    @JavascriptInterface
    public void news_detail(String news_id) {
        String url = Constants.NEWS + activity.getParameter() + "&op=detail&news_id={" + news_id + "}";
        activity.startActivity(new Intent(activity, TTWebActivity.class).putExtra("url", url));
    }


    /**
     * 删除全部消息
     */
    @JavascriptInterface
    public void alldel() {
        activity.showPopupWindow("&op=alldel", "是否删除");
    }

    /**
     * 全部已读
     */
    @JavascriptInterface
    public void allread() {
        activity.showPopupWindow("&op=allread", "是否全部已读");
    }

    /**
     * 跳转到绑定手机
     */
    @JavascriptInterface
    public void bindphone() {
        String url = Constants.UPDATE_PHONE + activity.getParameter();
        activity.startActivity(new Intent(activity, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 跳转到修改昵称
     */
    @JavascriptInterface
    public void chname() {
        String url = Constants.UPDATE_NAME + activity.getParameter();
        activity.startActivity(new Intent(activity, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 跳到清单页
     */
    @JavascriptInterface
    public void add_num(String act_id, String num) {
        final DetaileList detaileList = new DetaileList();
        detaileList.setActId(act_id);
        detaileList.setNumber(num);
        handler.post(new Runnable() {
            @Override
            public void run() {
                activity.startActivity(new Intent(activity, TTMainActivity.class)
                        .putExtra("detaileList", detaileList)
                        .putExtra("datainfo", TTApplication.pUserInfo)
                        .putExtra("indexFragment", 2));
                TTApplication.getInstance().exit();
            }
        });
    }

    /**
     * 跳转到清单
     */
    @JavascriptInterface
    public void mycar() {
        Intent intent = new Intent(activity, TTMainActivity.class);
        intent.putExtra("datainfo", TTApplication.pUserInfo);
        intent.putExtra("indexFragment", 2);
        activity.startActivity(intent);
        TTApplication.getInstance().exit();
    }

    /**
     * 跳转到往期揭晓
     */
    @JavascriptInterface
    public void act_record(String act_info_id) {
        String url = Constants.ACTIVITY_RECORD + "&act_info_id=" + act_info_id + activity.getParameter(act_info_id);
        activity.startActivity(new Intent(activity, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 去抽奖
     */
    @JavascriptInterface
    public void to_choujiang(String url, String title) {
        activity.startActivity(new Intent(activity, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 0元购取分享
     */
    @JavascriptInterface
    public void common_share(final String share_title, final String share_content, final String share_link, final String share_icon, int count) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                 Share.getInstance(activity).showShare(share_title, share_content, share_link, share_icon);
            }
        });

    }

    /**
     * 浏览至少十秒 完成按钮
     */
    @JavascriptInterface
    public void newer_about() {
        String url = DOMAIN + "/index.php?appid=0&tp=control/newer_about_us" + activity.getParameter();
        Intent intent = new Intent();
        intent.setClass(activity, TTWebActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }

    /**
     * 跳转今日签到
     */
    @JavascriptInterface
    public void daysign() {
        String url = DAYSIGN_NEW + activity.getParameter();
        Intent intent = new Intent(activity, TTWebActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }

    /**
     * 分享签到
     */
    @JavascriptInterface
    public void sign_share(final String title, final String content, final String link, final String icon, String buqian, String date) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Share.getInstance(activity).showShare( title, content, link, icon);
            }
        });

    }

    /**
     * 跳转到个人信息
     */
    @JavascriptInterface
    public void uinfo() {
        String url = Constants.UINFO + activity.getParameter();
        activity.startActivity(new Intent(activity, TTWebActivity.class).putExtra("url", url));
    }

    /**
     * 跳转到支付
     */
    @JavascriptInterface
    public void recharge() {
        String url = DOMAIN + "/index.php?tp=front/recharge" + activity.getParameter() + "&umstat=bdtb-jd";
        Intent intent = new Intent(activity, TTWebActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }

    /**
     * 分享到任意平台
     */
    @JavascriptInterface
    public void newer_share(final String title, final String content, final String link, final String icon) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                 Share.getInstance(activity).showShare(title, content, link, icon);
            }
        });
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