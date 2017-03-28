package com.ttdb.ttdbsdk.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ttdb.ttdbsdk.R;
import com.ttdb.ttdbsdk.bean.AliPayBean;
import com.ttdb.ttdbsdk.common.ParamsUtil;
import com.ttdb.ttdbsdk.common.Pay;
import com.ttdb.ttdbsdk.common.Sign;
import com.ttdb.ttdbsdk.common.WebSetUtil;
import com.ttdb.ttdbsdk.common._Toast;
import com.ttdb.ttdbsdk.myapp.TTApplication;
import com.ttdb.ttdbsdk.utils.SP;

import java.util.HashMap;

import static android.content.ContentValues.TAG;
import static com.ttdb.ttdbsdk.utils.Constants.APPID;
import static com.ttdb.ttdbsdk.utils.Constants.DOMAIN;
import static com.ttdb.ttdbsdk.utils.Constants.PAPPID;
import static com.ttdb.ttdbsdk.utils.Constants.PT;
import static com.ttdb.ttdbsdk.utils.Constants.SDK_PAY_FLAG;
import static com.ttdb.ttdbsdk.utils.Constants.SDK_VER;
import static com.ttdb.ttdbsdk.utils.Constants.SIGN_KEY_TTDB;

public class PayActivity extends TTBaseActivity {
    private String url;
    public WebView webview;
    public RelativeLayout relatParent;
    private ImageView ivBack;
    private TextView tvTitle;
    public PayActivity payActivity;
    //这里填入你的应用从官方网站申请到的合法appid.
    private static final String WECHAT_APP_ID="";
    //第三方app和微信通信的openapi接口
    private IWXAPI wechatApi;

    @SuppressLint("AddJavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ttdb_common);
        payActivity = this;
        registerWechatPayApi();
        TTApplication.getInstance().addActivity(this);
        Bundle b = getIntent().getExtras();
        url = b.getString("url");
        initView();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        PayContactPlugin web = new PayContactPlugin();
        WebSetUtil.getInstance()
                .setWebSetting(webview)
                .setWebLoadingListener(webview, this)
                .setTitle(webview, tvTitle)
                .setScrollBarEnabled(webview, false)
                .addJavascriptInterface(web, "contact");
        Log.d(TAG, "onCreate: " + tvTitle.getText().toString() + "  url=" + url);
        webview.loadUrl(url);
    }

    /**
     * 微信支付注册
     */
    private void registerWechatPayApi(){
        //获取微信api实例
        wechatApi= WXAPIFactory.createWXAPI(this,WECHAT_APP_ID,true);
        //将应用的appid注册到微信
        wechatApi.registerApp(WECHAT_APP_ID);
    }

    private void initView() {
        webview = (WebView) findViewById(R.id.webview);
        tvTitle = (TextView) findViewById(R.id.txtTitle);
        relatParent = (RelativeLayout) findViewById(R.id.comLayout);
        ivBack = (ImageView) findViewById(R.id.back);
    }

    private class PayContactPlugin {
        //阿里 alipay_pay(data.orderInfo,data.order_num,data.order_sign,data.md5_sign,data.money);
        //微信 contact.charge(cash,type);
        //微信扫码  contact.to_choujiang(paysign,titles);

        /**
         * 支付宝充值
         */
        @JavascriptInterface
        public void alipay_pay(String orderInfo,
                               String order_num,
                               String order_sign,
                               String md5_sign,
                               String money) {
            AliPayBean aliPayBean = new AliPayBean();
            aliPayBean.setMd5_sign(md5_sign);
            aliPayBean.setMoney(money);
            aliPayBean.setOrder_num(order_num);
            aliPayBean.setOrder_sign(order_sign);
            aliPayBean.setOrderInfo(orderInfo);
            Pay.getInstance(payActivity).alipay(aliPayBean, mHandler);
        }

        /**
         * 微信充值
         * @param cash 充值金额
         * @param type 充值类型  1支付宝 2 微信
         */
        @JavascriptInterface
        public void charge(String cash, String type) {

            // TODO: 2017/3/27 0027  发送支付信息
            wechatApi.sendReq(Pay.wechatPay(cash));
            _Toast.show(type);
//            String url = Constants.PAY_PAGE + "/index.php?tp=orders/add&appid=3" +
//                    "&uid="+uid+"&money="+money+"&paytype=1&pttype=1&sign=" + strSign
//                    + "&imei=" + imei + "&ver=" + ver;
            // sign=md5($uid.$money.$paytype.$pttype.$key);
            if (type.equals("33")) {
                String sign = Sign.getMD5(TTApplication.pUserInfo.getUid()
                        + cash + type + SDK_PAY_FLAG + SIGN_KEY_TTDB);
                HashMap<String, String> params = new HashMap<>();
                params.put("appid", APPID);
                params.put("pappid", PAPPID);
                params.put("pt", PT);
                params.put("uid", TTApplication.pUserInfo.getUid());
                params.put("imei", SP.get("imei","0"));
                params.put("ver", SDK_VER);
                params.put("sign", sign);
                String url = DOMAIN + "/index.php?tp=orders/add" + ParamsUtil.provide(params);
                Intent intent=new Intent(mContext,TTWebActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
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

        }
    }
}
