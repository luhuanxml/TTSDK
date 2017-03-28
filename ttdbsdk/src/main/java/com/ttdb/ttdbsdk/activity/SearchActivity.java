package com.ttdb.ttdbsdk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ttdb.ttdbsdk.R;
import com.ttdb.ttdbsdk.adapter.PagerAdapter;
import com.ttdb.ttdbsdk.bean.CarCountEvent;
import com.ttdb.ttdbsdk.common.ParamsUtil;
import com.ttdb.ttdbsdk.common.Sign;
import com.ttdb.ttdbsdk.common._Toast;
import com.ttdb.ttdbsdk.fragment.HotSearchFragment;
import com.ttdb.ttdbsdk.fragment.NearSearchFragment;
import com.ttdb.ttdbsdk.myapp.TTApplication;
import com.ttdb.ttdbsdk.utils.SP;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.ttdb.ttdbsdk.fragment.NearSearchFragment.NEAR_SEARCH_LIST_KEY;
import static com.ttdb.ttdbsdk.utils.Constants.APPID;
import static com.ttdb.ttdbsdk.utils.Constants.DOMAIN;
import static com.ttdb.ttdbsdk.utils.Constants.PAPPID;
import static com.ttdb.ttdbsdk.utils.Constants.PT;
import static com.ttdb.ttdbsdk.utils.Constants.SDK_VER;
import static com.ttdb.ttdbsdk.utils.Constants.SIGN_KEY_TTDB;

public class SearchActivity extends TTBaseActivity implements View.OnClickListener {
    ViewPager viewPager;
    View nearLine;
    View hotLine;
    TextView searchBtn;
    TextView nearSeachBtn;
    TextView hotSearchBtn;
    ImageView back;
    EditText search_content;

    PagerAdapter pagerAdapter;

    ArrayList<Fragment> fragments;
    HotSearchFragment hotSearchFragment;
    NearSearchFragment nearSearchFragment;

    String count;//清单种类数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        TTApplication.getInstance().addActivity(this);
        fragments = new ArrayList<>();
        hotSearchFragment = new HotSearchFragment();
        nearSearchFragment = new NearSearchFragment();
        fragments.add(nearSearchFragment);
        fragments.add(hotSearchFragment);
        initView();
        setViewPager();
        setLitener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        nearLine = findViewById(R.id.near_line);
        hotLine = findViewById(R.id.hot_line);
        searchBtn = (TextView) findViewById(R.id.search_btn);
        nearSeachBtn = (TextView) findViewById(R.id.near_search_btn);
        hotSearchBtn = (TextView) findViewById(R.id.hot_search_btn);
        search_content = (EditText) findViewById(R.id.search_content);
        back = (ImageView) findViewById(R.id.back);
        changeLine(true, false);
    }

    private void setViewPager() {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
    }

    private void setLitener() {
        searchBtn.setOnClickListener(this);
        nearSeachBtn.setOnClickListener(this);
        hotSearchBtn.setOnClickListener(this);
        back.setOnClickListener(this);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    changeLine(true, false);
                } else if (position == 1) {
                    changeLine(false, true);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CarCountEvent countEvent) {
        count = countEvent.getCount();
        Log.d("luhuan3", "onEventMainThread: "+count);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.back) {
            finish();
        } else if (viewId == R.id.near_search_btn) {
            changeLine(true, false);
            viewPager.setCurrentItem(0);
        } else if (viewId == R.id.hot_search_btn) {
            changeLine(false, true);
            viewPager.setCurrentItem(1);
        } else if (viewId == R.id.search_btn) {
            if (search_content.getText().toString().equals("")) {
                _Toast.show("请输入搜索的商品");
            } else {
                List<String> list = SP.getAll(NEAR_SEARCH_LIST_KEY,new ArrayList<String>());
                if (!list.contains(search_content.getText().toString())) {
                    list.add(0, search_content.getText().toString());
                }
                //一次只加一个，当大于10的时候，把下标10也就是第11个删除掉保证Hawk存值不超过10个
                if (list.size() > 10) list.remove(10);
                SP.putAll(NEAR_SEARCH_LIST_KEY,list);
                search(search_content.getText().toString());
                search_content.setText("");
            }
        }
    }

    public void search(@NonNull String key) {
        HashMap<String, String> params = new HashMap<>();
        params.put("appid", APPID);
        params.put("pappid", PAPPID);
        params.put("pt", PT);
        params.put("op", "getlist");
        params.put("uid", TTApplication.pUserInfo.getUid());
        params.put("imei",  SP.get("imei","0"));
        params.put("ver", SDK_VER);
        params.put("title", URLEncoder.encode(key));
        params.put("sign", Sign.getMD5(TTApplication.pUserInfo.getUid() + SIGN_KEY_TTDB));
        String url = DOMAIN + "/index.php?tp=front/search" + ParamsUtil.provide(params);
        Intent intent = new Intent(this, GoodsActivity.class);
        intent.putExtra("url", url);
        if (count != null) {
            intent.putExtra("count", count);
        }
        startActivity(intent);
    }

    private void changeLine(boolean isNearVisible, boolean isHotVisible) {
        if (isNearVisible) nearLine.setVisibility(View.VISIBLE);
        else nearLine.setVisibility(View.INVISIBLE);

        if (isHotVisible) hotLine.setVisibility(View.VISIBLE);
        else hotLine.setVisibility(View.INVISIBLE);
    }
}
