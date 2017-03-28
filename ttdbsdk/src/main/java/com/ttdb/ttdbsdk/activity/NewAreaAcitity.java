package com.ttdb.ttdbsdk.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttdb.ttdbsdk.R;
import com.ttdb.ttdbsdk.adapter.GridAdapter;
import com.ttdb.ttdbsdk.banner.Banner;
import com.ttdb.ttdbsdk.banner.Transformer;
import com.ttdb.ttdbsdk.banner.listener.OnBannerListener;
import com.ttdb.ttdbsdk.bean.GoodEntity;
import com.ttdb.ttdbsdk.bean.IosIndexEntity;
import com.ttdb.ttdbsdk.common.ParamsUtil;
import com.ttdb.ttdbsdk.common.Sign;
import com.ttdb.ttdbsdk.common._Picasso;
import com.ttdb.ttdbsdk.common._Toast;
import com.ttdb.ttdbsdk.mvp.goods.GoodPresenter;
import com.ttdb.ttdbsdk.mvp.goods.GoodView;
import com.ttdb.ttdbsdk.mvp.newarea.NewAreaPresenter;
import com.ttdb.ttdbsdk.mvp.newarea.NewAreaView;
import com.ttdb.ttdbsdk.utils.Constants;
import com.ttdb.ttdbsdk.utils.SP;
import com.ttdb.ttdbsdk.view.CircleImageView;
import com.ttdb.ttdbsdk.view.GlideImageLoader;
import com.ttdb.ttdbsdk.view.GridViewWithHeaderAndFooter;
import com.ttdb.ttdbsdk.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.ttdb.ttdbsdk.utils.Constants.APPID;
import static com.ttdb.ttdbsdk.utils.Constants.DOMAIN;
import static com.ttdb.ttdbsdk.utils.Constants.PAPPID;
import static com.ttdb.ttdbsdk.utils.Constants.PT;
import static com.ttdb.ttdbsdk.utils.Constants.SIGN_KEY_TTDB;

public class NewAreaAcitity extends TTBaseActivity implements NewAreaView, GoodView {
    NewAreaAcitity mActivity;
    LayoutInflater inflater;
    GridViewWithHeaderAndFooter gridView;
    PullToRefreshView pullToRefreshView;
    View xuanfuInclude;
    TextView text_renqi_xuanfu;
    TextView text_zuixin_xuanfu;
    TextView text_jindu_xuanfu;
    TextView text_total_xuanfu;
    View view_renqi_xuanfu;
    View view_zuixin_xuanfu;
    View view_jindu_xuanfu;
    View view_total_xuanfu;
    LinearLayout line_renqi_xuanfu;
    LinearLayout line_zuixin_xuanfu;
    LinearLayout line_jindu_xuanfu;
    LinearLayout line_total_xuanfu;
    ImageView back;

    //头部控件
    CircleImageView circle1;
    CircleImageView circle2;
    CircleImageView circle3;
    CircleImageView circle4;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    View index_include1;
    View index_include2;
    View index_include3;
    View index_include4;
    Banner banner;
    TextView text_renqi;
    TextView text_zuixin;
    TextView text_jindu;
    TextView text_total;
    View view_renqi;
    View view_zuixin;
    View view_jindu;
    View view_total;
    LinearLayout line_renqi;
    LinearLayout line_zuixin;
    LinearLayout line_jindu;
    LinearLayout line_total;

    private GoodPresenter goodPresenter;
    private NewAreaPresenter newAreaPresenter;

    List<IosIndexEntity.DataBean.EyeBean> eyeList = new ArrayList<>();
    List<IosIndexEntity.DataBean.IconBean> iconList = new ArrayList<>();
    List<GoodEntity.DataBean> goodList = new ArrayList<>();

    //上平列表请求参数
    int page;
    String style;
    private GridAdapter gridAdapter;
    private View viewHeader1;
    private View viewHeader2;

    //总需人次的两种情况
    private static int HIGH_TO_LOW = 11;
    private static int LOW_TO_HIGH = 22;
    private int totolPerson=LOW_TO_HIGH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_area);
        mActivity = this;
        inflater = LayoutInflater.from(mActivity);
        goodPresenter = new GoodPresenter(this);
        newAreaPresenter = new NewAreaPresenter(this);
        initView();
        setGridView();
        setListener();
        newAreaPresenter.present();
    }

    private void initView() {
        gridView = (GridViewWithHeaderAndFooter) findViewById(R.id.gridview);
        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_refresh_loadmore);
        xuanfuInclude = findViewById(R.id.xuanfu);
        text_renqi_xuanfu = (TextView) xuanfuInclude.findViewById(R.id.text_renqi);
        text_zuixin_xuanfu = (TextView) xuanfuInclude.findViewById(R.id.text_zuixin);
        text_jindu_xuanfu = (TextView) xuanfuInclude.findViewById(R.id.text_jindu);
        text_total_xuanfu = (TextView) xuanfuInclude.findViewById(R.id.text_total);
        view_renqi_xuanfu = xuanfuInclude.findViewById(R.id.view_renqi);
        view_zuixin_xuanfu = xuanfuInclude.findViewById(R.id.view_zuixin);
        view_jindu_xuanfu = xuanfuInclude.findViewById(R.id.view_jindu);
        view_total_xuanfu = xuanfuInclude.findViewById(R.id.view_total);
        line_renqi_xuanfu = (LinearLayout) xuanfuInclude.findViewById(R.id.line_renqi);
        line_zuixin_xuanfu = (LinearLayout) xuanfuInclude.findViewById(R.id.line_zuixin);
        line_jindu_xuanfu = (LinearLayout) xuanfuInclude.findViewById(R.id.line_jindu);
        line_total_xuanfu = (LinearLayout) xuanfuInclude.findViewById(R.id.line_total);
        back = (ImageView) findViewById(R.id.new_area_back);
    }

    private void setGridView() {
        viewHeader1 = inflater.inflate(R.layout.new_area_head, null);
        initHeaderView1(viewHeader1);
        setHeader1Listener();
        gridView.addHeaderView(viewHeader1);
        viewHeader2 = inflater.inflate(R.layout.include_head, null);
        initHeaderView2(viewHeader2);
        setHeader2Listener();
        gridView.addHeaderView(viewHeader2);
        gridAdapter = new GridAdapter(mActivity, goodList);
        gridView.setAdapter(gridAdapter);
        gridView.setNumColumns(2);
    }

    private void setListener() {
        pullToRefreshView.setEnablePullTorefresh(true);
        pullToRefreshView.setFooter(true);
        pullToRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                page = 1;
                goodPresenter.present(page, style);
            }
        });
        pullToRefreshView.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                goodPresenter.present(++page, style);
            }
        });
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (viewHeader1 != null) {
                    if (firstVisibleItem >= 2) {
                        xuanfuInclude.setVisibility(View.VISIBLE);
                    } else {
                        xuanfuInclude.setVisibility(View.GONE);
                    }
                }
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jump(goodList.get(position).getInfo_id());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        line_renqi_xuanfu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeHead(true, false, false, false);
            }
        });
        line_zuixin_xuanfu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeHead(false, true, false, false);
            }
        });
        line_jindu_xuanfu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeHead(false, false, true, false);
            }
        });
        line_total_xuanfu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeHead(false, false, false, true);
            }
        });
    }

    //总需人次排序
    private void totalRank(){
        if (totolPerson == LOW_TO_HIGH) {
            page = 1;
            style = "4";
            Drawable drawable=getResources().getDrawable(R.mipmap.jt_02);
            drawable.setBounds(0, 0, 32, 48);
            text_total.setCompoundDrawables(null,null,drawable,null);
            text_total_xuanfu.setCompoundDrawables(null,null,drawable,null);
            totolPerson=HIGH_TO_LOW;
        }else {
            Drawable drawable=getResources().getDrawable(R.mipmap.jt_03);
            drawable.setBounds(0, 0, 32, 48);
            text_total.setCompoundDrawables(null,null,drawable,null);
            text_total_xuanfu.setCompoundDrawables(null,null,drawable,null);
            page = 1;
            style = "5";
            totolPerson=LOW_TO_HIGH;
        }
    }

    //商品item跳转
    private void jump(String act_id) {
        String sign = Sign.getMD5(uid + act_id + SIGN_KEY_TTDB);
        HashMap<String, String> params = new HashMap<>();
        params.put("appid", APPID);
        params.put("pt", PT);
        params.put("info_id", act_id);
        params.put("pappid", PAPPID);
        params.put("uid", uid);
        params.put("ver", ver);
        params.put("imei", SP.get("imei","0"));
        params.put("sign", sign);
        String url = Constants.DOMAIN + "/index.php?appid=0&tp=front/activity_detail" + ParamsUtil.provide(params);
        Intent intent = new Intent(mContext, TTWebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("isBack", true);
        startActivity(intent);
    }

    private void changeHead(boolean renqi, boolean zuixin, boolean jindu, boolean total) {
        if (renqi) {
            text_renqi.setTextColor(getResources().getColor(R.color.ttdb_head_red));
            view_renqi.setVisibility(View.VISIBLE);
            text_renqi_xuanfu.setTextColor(getResources().getColor(R.color.ttdb_head_red));
            view_renqi_xuanfu.setVisibility(View.VISIBLE);
            page=1;
            style="1";
        } else {
            text_renqi.setTextColor(getResources().getColor(R.color.black));
            view_renqi.setVisibility(View.INVISIBLE);
            text_renqi_xuanfu.setTextColor(getResources().getColor(R.color.black));
            view_renqi_xuanfu.setVisibility(View.INVISIBLE);
        }

        if (zuixin) {
            text_zuixin.setTextColor(getResources().getColor(R.color.ttdb_head_red));
            view_zuixin.setVisibility(View.VISIBLE);
            text_zuixin_xuanfu.setTextColor(getResources().getColor(R.color.ttdb_head_red));
            view_zuixin_xuanfu.setVisibility(View.VISIBLE);
            page=1;
            style="2";
        } else {
            text_zuixin.setTextColor(getResources().getColor(R.color.black));
            view_zuixin.setVisibility(View.INVISIBLE);
            text_zuixin_xuanfu.setTextColor(getResources().getColor(R.color.black));
            view_zuixin_xuanfu.setVisibility(View.INVISIBLE);
        }

        if (jindu) {
            text_jindu.setTextColor(getResources().getColor(R.color.ttdb_head_red));
            view_jindu.setVisibility(View.VISIBLE);
            text_jindu_xuanfu.setTextColor(getResources().getColor(R.color.ttdb_head_red));
            view_jindu_xuanfu.setVisibility(View.VISIBLE);
            page=1;
            style="3";
        } else {
            text_jindu.setTextColor(getResources().getColor(R.color.black));
            view_jindu.setVisibility(View.INVISIBLE);
            text_jindu_xuanfu.setTextColor(getResources().getColor(R.color.black));
            view_jindu_xuanfu.setVisibility(View.INVISIBLE);
        }

        if (total) {
            text_total.setTextColor(getResources().getColor(R.color.ttdb_head_red));
            view_total.setVisibility(View.VISIBLE);
            text_total_xuanfu.setTextColor(getResources().getColor(R.color.ttdb_head_red));
            view_total_xuanfu.setVisibility(View.VISIBLE);
            totalRank();
        } else {
            text_total.setTextColor(getResources().getColor(R.color.black));
            view_total.setVisibility(View.INVISIBLE);
            text_total_xuanfu.setTextColor(getResources().getColor(R.color.black));
            view_total_xuanfu.setVisibility(View.INVISIBLE);
            Drawable drawable=getResources().getDrawable(R.mipmap.jt_01);
            drawable.setBounds(0, 0, 32, 48);
            text_total.setCompoundDrawables(null,null,drawable,null);
            text_total_xuanfu.setCompoundDrawables(null,null,drawable,null);
        }
        goodPresenter.present(page,style);
    }

    private void initHeaderView1(View viewHeader) {
        banner = (Banner) viewHeader.findViewById(R.id.banner);

        index_include1 = viewHeader.findViewById(R.id.include_1);
        text1 = (TextView) index_include1.findViewById(R.id.index_text);
        circle1 = (CircleImageView) index_include1.findViewById(R.id.index_icon);

        index_include2 = viewHeader.findViewById(R.id.include_2);
        text2 = (TextView) index_include2.findViewById(R.id.index_text);
        circle2 = (CircleImageView) index_include2.findViewById(R.id.index_icon);

        index_include3 = viewHeader.findViewById(R.id.include_3);
        text3 = (TextView) index_include3.findViewById(R.id.index_text);
        circle3 = (CircleImageView) index_include3.findViewById(R.id.index_icon);

        index_include4 = viewHeader.findViewById(R.id.include_4);
        text4 = (TextView) index_include4.findViewById(R.id.index_text);
        circle4 = (CircleImageView) index_include4.findViewById(R.id.index_icon);

    }

    private void setHeader1Listener() {
        // 添加大眼睛点击
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                HashMap<String, String> params = new HashMap<>();
                params.put("pt", PT);
                params.put("appid", APPID);
                params.put("pappid", PAPPID);
                params.put("uid", uid);
                params.put("imei", SP.get("imei","0"));
                params.put("ver", ver);
                params.put("sign", Sign.getMD5(uid + SIGN_KEY_TTDB));
                String url = eyeList.get(position).getUrl() + ParamsUtil.provide(params);
                Intent intent = new Intent(mActivity, TTWebActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

        index_include1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpIndex(0);
            }
        });
        index_include2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpIndex(1);
            }
        });
        index_include3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpIndex(2);
            }
        });
        index_include4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpIndex(3);
            }
        });
    }

    private void jumpIndex(int position) {
        IosIndexEntity.DataBean.IconBean iconBean = iconList.get(position);
        String sign;
        String baseUrl;
        if (iconBean.getType().equals("3")) {
            sign = Sign.getMD5(uid + iconBean.getId() + SIGN_KEY_TTDB);
            baseUrl = iconList.get(position).getUrl() + iconBean.getId();
        } else {
            sign = Sign.getMD5(uid + SIGN_KEY_TTDB);
            baseUrl = iconList.get(position).getUrl();
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("pt", PT);
        params.put("uid", uid);
        params.put("imei",SP.get("imei","0"));
        params.put("ver", ver);
        params.put("sign", sign);
        String url = baseUrl + ParamsUtil.provide(params);
        Intent intent = new Intent(mActivity, TTWebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    private void initHeaderView2(View viewHeader2) {
        text_renqi = (TextView) viewHeader2.findViewById(R.id.text_renqi);
        text_zuixin = (TextView) viewHeader2.findViewById(R.id.text_zuixin);
        text_jindu = (TextView) viewHeader2.findViewById(R.id.text_jindu);
        text_total = (TextView) viewHeader2.findViewById(R.id.text_total);
        view_renqi = viewHeader2.findViewById(R.id.view_renqi);
        view_zuixin = viewHeader2.findViewById(R.id.view_zuixin);
        view_jindu = viewHeader2.findViewById(R.id.view_jindu);
        view_total = viewHeader2.findViewById(R.id.view_total);
        line_renqi = (LinearLayout) viewHeader2.findViewById(R.id.line_renqi);
        line_zuixin = (LinearLayout) viewHeader2.findViewById(R.id.line_zuixin);
        line_jindu = (LinearLayout) viewHeader2.findViewById(R.id.line_jindu);
        line_total = (LinearLayout) viewHeader2.findViewById(R.id.line_total);
        changeHead(true, false, false, false);
    }

    private void setHeader2Listener() {
        line_renqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeHead(true, false, false, false);
            }
        });
        line_zuixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeHead(false, true, false, false);
            }
        });
        line_jindu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeHead(false, false, true, false);
            }
        });
        line_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeHead(false, false, false, true);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    @Override
    public void showNewArea(IosIndexEntity iosIndexEntity) {
        if (iosIndexEntity.getStatus().equals("1")) {
            eyeList = iosIndexEntity.getData().getEye();
            iconList = iosIndexEntity.getData().getIcon();
            _Picasso.loadImg(DOMAIN + iconList.get(0).getPic(),circle1);
            _Picasso.loadImg(DOMAIN + iconList.get(1).getPic(),circle2);
            _Picasso.loadImg(DOMAIN + iconList.get(2).getPic(),circle3);
            _Picasso.loadImg(DOMAIN + iconList.get(3).getPic(),circle4);
            text1.setText(iconList.get(0).getTitle());
            text2.setText(iconList.get(1).getTitle());
            text3.setText(iconList.get(2).getTitle());
            text4.setText(iconList.get(3).getTitle());
            List<String> bannerImages = new ArrayList<>();
            for (IosIndexEntity.DataBean.EyeBean eyeBean : eyeList) {
                bannerImages.add(eyeBean.getPic());
            }
            banner.setImages(bannerImages)
                    .setBannerAnimation(Transformer.Default)
                    .setImageLoader(new GlideImageLoader())
                    .isAutoPlay(true).start();
        }
    }

    @Override
    public void showGoodList(GoodEntity goodEntity) {
        goodList = goodEntity.getData();
        gridAdapter.refresh(goodList);
        pullToRefreshView.onHeaderRefreshComplete();
    }

    @Override
    public void loadMoreGoodList(GoodEntity goodEntity) {
        if (goodEntity.getData() == null) {
            page = page - 1;
            _Toast.show("没有更多了");
        } else {
            goodList.addAll(goodEntity.getData());
            gridAdapter.refresh(goodList);
            _Toast.show("加载成功");
        }
        pullToRefreshView.onFooterRefreshComplete();
    }

}
