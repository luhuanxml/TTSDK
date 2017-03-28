package com.ttdb.ttdbsdk.adapter;

import android.view.View;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.ttdb.ttdbsdk.R;

public class HotItemHolder extends RViewHolder<LinkedTreeMap<String,String>> {
    private TextView searchItem;
    public HotItemHolder(View itemView) {
        super(itemView);
        searchItem= (TextView) itemView.findViewById(R.id.search_item);
    }

    @Override
    public void setData(LinkedTreeMap<String,String> map) {
        searchItem.setText(map.get("sname"));
    }
}
