package com.ttdb.ttdbsdk.common;

import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.ttdb.ttdbsdk.activity.TTBaseActivity;
import com.ttdb.ttdbsdk.bean.AliPayBean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import static com.ttdb.ttdbsdk.utils.Constants.SDK_PAY_FLAG;

public class Pay {
    private static Pay instance;
    private TTBaseActivity activity;

    private Pay(TTBaseActivity activity) {
        this.activity=activity;
    }

    public static Pay getInstance(TTBaseActivity activity) {
        if (instance == null) {
            synchronized (Pay.class) {
                if (instance == null) {
                    instance = new Pay(activity);
                }
            }
        }
        return instance;
    }

    /**
     * 微信支付BaseReq对象获得方法
     * @param text 支付信息
     * @return BaseReq
     */
    public static BaseReq wechatPay(String text){
        //初始化获得WXTextObject对象
        WXTextObject textObject=new WXTextObject();
        textObject.text =text;
        //用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg= new WXMediaMessage();
        msg.mediaObject=textObject;
        msg.description=text;
        //构造一个Req
        SendMessageToWX.Req req =new SendMessageToWX.Req();
        //transaction字段用于唯一标识一个请求
        req.transaction=String.valueOf(System.currentTimeMillis());
        req.message=msg;
        return req;
    }

    /**
     * 支付宝支付
     * @param aliPayBean
     */
    public void alipay(AliPayBean aliPayBean, final Handler handler) {
        try {
            String signStr = new String(Base64.decode(aliPayBean.getOrderInfo(), 1), "utf-8");
            String sign = URLEncoder.encode(aliPayBean.getOrder_sign(), "UTF-8");
            //mcTradeNo = order_id;

            //  完整的符合支付宝参数规范的订单信息
            final String payInfo = signStr + "&sign=" + sign
                    + "&sign_type=RSA";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PayTask alipay = new PayTask(activity);
                    Map<String, String> result = alipay.payV2(payInfo, true);
                    Log.i("msp", result.toString());

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    handler.sendMessage(msg);
                }
            }).start();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}