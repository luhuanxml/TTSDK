package com.ttdb.ttdbsdk.adapter;

import android.view.View;
import android.widget.TextView;

import com.ttdb.ttdbsdk.R;

/**
 * Created by Administrator on 2017/3/15 0015.
 */

public class NearItemHolder extends RViewHolder<String> {
    private TextView searchItem;
    public NearItemHolder(View itemView) {
        super(itemView);
        searchItem= (TextView) itemView.findViewById(R.id.search_item);
    }

    @Override
    public void setData(String sName) {
        searchItem.setText(sName);
    }
}
