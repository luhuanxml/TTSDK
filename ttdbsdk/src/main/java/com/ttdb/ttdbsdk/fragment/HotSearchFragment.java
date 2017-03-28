package com.ttdb.ttdbsdk.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.ttdb.ttdbsdk.R;
import com.ttdb.ttdbsdk.flowlayout.FlowLayout;
import com.ttdb.ttdbsdk.mvp.hotsearch.HotSearchPresenter;
import com.ttdb.ttdbsdk.mvp.hotsearch.HotSearchView;

import java.util.ArrayList;
import java.util.List;

public class HotSearchFragment extends BaseSearchFragment implements HotSearchView {

    private FlowLayout flowLayout;

    private List<LinkedTreeMap<String,String>> hotList=new ArrayList<>();
    private HotSearchPresenter hotSearchPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hotSearchPresenter = new HotSearchPresenter(this);
    }

    @Override
    protected void initView(View view) {
        flowLayout= (FlowLayout) view.findViewById(R.id.hot_flowlayout);
    }

    protected void addView(final String searchContent){
        View itemView=inflater.inflate(R.layout.search_item,null);
        TextView textView= (TextView) itemView.findViewById(R.id.search_item);
        textView.setText(searchContent);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.search(searchContent);
            }
        });
        flowLayout.addView(itemView);
    }

    @Override
    public void onStart() {
        super.onStart();
        hotSearchPresenter.present();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot_search;
    }

    @Override
    public void showHotSearchList(List<LinkedTreeMap<String,String>> list) {
        hotList= list;
        flowLayout.removeAllViews();
        for (int i = 0; i < hotList.size(); i++) {
            addView(hotList.get(i).get("sname"));
        }
    }
}
