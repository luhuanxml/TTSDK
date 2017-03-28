package com.ttdb.ttdbsdk.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ttdb.ttdbsdk.R;
import com.ttdb.ttdbsdk.bean.CarCountEvent;
import com.ttdb.ttdbsdk.bean.ImageEvent;
import com.ttdb.ttdbsdk.bean.IndexEvent;
import com.ttdb.ttdbsdk.common.WebSetUtil;
import com.ttdb.ttdbsdk.common._Picasso;
import com.ttdb.ttdbsdk.common._Toast;
import com.ttdb.ttdbsdk.myapp.TTApplication;
import com.ttdb.ttdbsdk.utils.SP;
import com.ttdb.ttdbsdk.view.AddCartAnimation;
import com.ttdb.ttdbsdk.view.BadgeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static android.content.ContentValues.TAG;
import static com.ttdb.ttdbsdk.utils.Constants.DOMAIN;

public class GoodsActivity extends TTBaseActivity {
    private String url;
    public WebView webview;
    public RelativeLayout relativeLayout;
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView goodCar;
    private BadgeView badgeView;

    private ImageView flystart;
    private View flyend;

    String goodGount = "0";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void flyGoodToCar(ImageEvent event){
        if (event.getImgUrl() != null) {
            AddCartAnimation.AddToCart(flystart,flyend,mContext,relativeLayout,1,_Picasso.loadImg(event.getImgUrl()));
        }

    }

    @SuppressLint("AddJavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ttdb_goods);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        TTApplication.getInstance().addActivity(this);
        Bundle b = getIntent().getExtras();
        url = b.getString("url");
        if (b.getString("count")!=null) goodGount=b.getString("count");
        else goodGount= SP.get("good_count","0");
        initView();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        goodCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new IndexEvent(IndexEvent.PRIZES));
                TTApplication.getInstance().exit();
                finish();
            }
        });
        GoodsContactPlugin web = new GoodsContactPlugin();
        WebSetUtil.getInstance()
                .setWebSetting(webview)
                .setWebLoadingListener(webview, mContext)
                .setTitle(webview, tvTitle)
                .setScrollBarEnabled(webview, false)
                .addJavascriptInterface(web, "contact");
        Log.d(TAG, "onCreate: " + tvTitle.getText().toString() + "  url=" + url);
        webview.loadUrl(url);
    }
    private void initView(){
        badgeView = new BadgeView(this);
        webview = (WebView) findViewById(R.id.webview);
        tvTitle = (TextView) findViewById(R.id.txtTitle);
        goodCar = (ImageView) findViewById(R.id.img_goodcar);
        setBadge(goodGount);
        relativeLayout = (RelativeLayout) findViewById(R.id.comLayout);
        ivBack = (ImageView) findViewById(R.id.back);

        flystart= (ImageView) findViewById(R.id.fly_img);
        flyend=findViewById(R.id.end_fly);
    }

    @SuppressLint("RtlHardcoded")
    private void setBadge(String goodGount) {
        badgeView.setTargetView(goodCar);
        badgeView.setVisibility(View.VISIBLE);
        badgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        badgeView.setTextColor(getResources().getColor(R.color.white));
        badgeView.setBackground(12, getResources().getColor(R.color.transparent));
        badgeView.setText(goodGount);
    }

    private class GoodsContactPlugin  {
        private Handler handler;

        private GoodsContactPlugin() {
            handler = new Handler();
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
                    EventBus.getDefault().post(new CarCountEvent(count));
                    goodGount=count;
                    setBadge(count);
                    _Toast.show("加入清单成功");
                }
            });
        }

        /**
         * 跳转到夺宝详细页面
         *
         * @param act_id 商品id
         */
        @JavascriptInterface
        public void act_detail(String act_id) {
            String url = DOMAIN + "/index.php?tp=front/activity_detail&act_id=" + act_id + getParameter(act_id);
            Intent intent = new Intent(mContext, TTWebActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        }

        /**
         * 清单数量
         */
        @JavascriptInterface
        public void show_number(final String num){
            Log.d("luhuan", "show_number: "+num);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    goodGount=num;
                    setBadge(goodGount);
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

        }
    }
}
