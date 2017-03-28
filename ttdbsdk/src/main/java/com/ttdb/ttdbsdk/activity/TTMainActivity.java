package com.ttdb.ttdbsdk.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.ttdb.ttdbsdk.R;
import com.ttdb.ttdbsdk.bean.GoodsNumEvent;
import com.ttdb.ttdbsdk.bean.ImageEvent;
import com.ttdb.ttdbsdk.bean.IndexEvent;
import com.ttdb.ttdbsdk.bean.PUserInfo;
import com.ttdb.ttdbsdk.common._Picasso;
import com.ttdb.ttdbsdk.fragment.TTBaseFragment;
import com.ttdb.ttdbsdk.fragment.TTClassifyFragment;
import com.ttdb.ttdbsdk.fragment.TTDetailedlistFragment;
import com.ttdb.ttdbsdk.fragment.TTHomeFragment;
import com.ttdb.ttdbsdk.fragment.TTMyFragment;
import com.ttdb.ttdbsdk.fragment.TTPrizesFragment;
import com.ttdb.ttdbsdk.view.AddCartAnimation;
import com.ttdb.ttdbsdk.view.BadgeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class TTMainActivity extends TTBaseActivity {
    public List<Fragment> fragmentList = new ArrayList<>();
    private RadioGroup main_radiogroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private RadioButton radioButton5;

    private ImageView flyimg;
    private ImageView flyend;
    private RelativeLayout rootView;


    public View viewbadge4;


    private BadgeView badgeView;
    int indexId;
    //四个fragment切换
    int indexFragment;
    final int HOME = 0;
    final int DETAIL = 1;
    final int CLASSIFY = 2;
    final int PRIZES = 3;
    final int MY = 4;

    //三方用户传过来的信息
    public PUserInfo pUserInfo;

    private String goodCount = "0";

    public String getGoodCount() {
        return goodCount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        setContentView(R.layout.ttdb_activity_main);
        getIntentData();
        init();
        initView();
        setOnClick();
        switch (indexFragment) {
            case HOME:
                setIndex(HOME);
                break;
            case DETAIL:
                setIndex(DETAIL);
                break;
            case CLASSIFY:
                setIndex(CLASSIFY);
                break;
            case PRIZES:
                setIndex(PRIZES);
                break;
            case MY:
                setIndex(MY);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMAIN_FlyImg(ImageEvent event) {
        if (event.getImgUrl() != null) {
            AddCartAnimation.AddToCart(flyimg,flyend,mContext,rootView,1,_Picasso.loadImg(event.getImgUrl()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMAIN_indexId(IndexEvent event) {
        if (event.getIndex() != null) {
            indexId = event.getIndex();
            setIndex(indexId);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMAIN_GoodNum(GoodsNumEvent event) {
        if (event.getNum() != null) {
            goodCount = event.getNum();
            setBadge(goodCount);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void getIntentData() {
        Intent intent = getIntent();
        pUserInfo = (PUserInfo) intent.getSerializableExtra("datainfo");
        pUserInfo.getUid();
        indexFragment = intent.getIntExtra("indexFragment", 0);
    }

    private void init() {
        TTHomeFragment fragment1 = new TTHomeFragment();
        sendBundle(fragment1);
        fragmentList.add(fragment1);

        TTPrizesFragment fragment2 = new TTPrizesFragment();
        sendBundle(fragment2);
        fragmentList.add(fragment2);

        TTClassifyFragment fragment3 = new TTClassifyFragment();
        sendBundle(fragment3);
        fragmentList.add(fragment3);

        TTDetailedlistFragment fragment4 = new TTDetailedlistFragment();
        sendBundle(fragment4);
        fragmentList.add(fragment4);

        TTMyFragment fragment5 = new TTMyFragment();
        sendBundle(fragment5);
        fragmentList.add(fragment5);
    }

    public void sendBundle(TTBaseFragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("datainfo", pUserInfo);
        fragment.setArguments(bundle);
    }

    private void initView() {
        flyimg= (ImageView) findViewById(R.id.fly_img);
        flyend= (ImageView) findViewById(R.id.end_fly);
        rootView= (RelativeLayout) findViewById(R.id.root_view);

        main_radiogroup = (RadioGroup) findViewById(R.id.main_rg);
        radioButton1 = (RadioButton) findViewById(R.id.btn1);
        radioButton2 = (RadioButton) findViewById(R.id.btn2);
        radioButton3 = (RadioButton) findViewById(R.id.btn3);
        radioButton4 = (RadioButton) findViewById(R.id.btn4);
        radioButton5 = (RadioButton) findViewById(R.id.btn5);

        viewbadge4 = findViewById(R.id.badge_4);
        badgeView = new BadgeView(this);
        setDrawableTop(R.drawable.ttdb_s_home, radioButton1);
        setDrawableTop(R.drawable.ttdb_s_lowprice, radioButton2);
        setDrawableTop(R.drawable.ttdb_s_classify, radioButton3);
        setDrawableTop(R.drawable.ttdb_s_buy, radioButton4);
        setDrawableTop(R.drawable.ttdb_s_my, radioButton5);
    }

    private void setDrawableTop(int drawableId, RadioButton radioButton) {
        Drawable drawable = getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, 80, 80);
        radioButton.setCompoundDrawables(null, drawable, null, null);
    }

    private void setOnClick() {
        main_radiogroup.setOnCheckedChangeListener(groupchecked);
    }

    RadioGroup.OnCheckedChangeListener groupchecked = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            indexId = main_radiogroup.indexOfChild(group.findViewById(checkedId));
            setRadioGroup(indexId);
        }
    };

    public void setRadioGroup(int i) {
        getFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, fragmentList.get(i))
                .commitAllowingStateLoss();
    }

    public void setIndex(int indexId) {
        ((RadioButton) main_radiogroup.getChildAt(indexId)).setChecked(true);
    }

    @SuppressLint("RtlHardcoded")
    public void setBadge(String goodGount) {
        badgeView.setTargetView(viewbadge4);
        badgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        badgeView.setTextColor(getResources().getColor(R.color.white));
        badgeView.setBackground(12, getResources().getColor(R.color.red));
        badgeView.setText(goodGount);
        badgeView.setVisibility(View.VISIBLE);
    }
}
