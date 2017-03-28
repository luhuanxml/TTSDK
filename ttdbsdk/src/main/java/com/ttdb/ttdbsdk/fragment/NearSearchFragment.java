package com.ttdb.ttdbsdk.fragment;

import android.view.View;
import android.widget.TextView;

import com.ttdb.ttdbsdk.R;
import com.ttdb.ttdbsdk.flowlayout.FlowLayout;
import com.ttdb.ttdbsdk.utils.SP;

import java.util.ArrayList;
import java.util.List;

public class NearSearchFragment extends BaseSearchFragment {

    private TextView clearRecodeBtn;
    private FlowLayout flowLayout;

    List<String> list=new ArrayList<>();
    public final static String NEAR_SEARCH_LIST_KEY="near_search_list_key";

    @Override
    protected void initView(View view) {
        clearRecodeBtn= (TextView) view.findViewById(R.id.clear_recode_btn);
        flowLayout= (FlowLayout) view.findViewById(R.id.near_flowlayout);

        clearRecodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SP.delete(NEAR_SEARCH_LIST_KEY);
                list.clear();
                flowLayout.removeAllViews();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        flowLayout.removeAllViews();
        list=SP.getAll(NEAR_SEARCH_LIST_KEY,new ArrayList<String>());
        for (int i = 0; i <list.size(); i++) {
            addView(list.get(i));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_near_search;
    }

    @Override
    protected void addView(final String searchContent) {
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

}
