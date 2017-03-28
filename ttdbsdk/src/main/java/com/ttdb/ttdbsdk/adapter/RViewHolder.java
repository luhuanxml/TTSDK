package com.ttdb.ttdbsdk.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class RViewHolder<T> extends RecyclerView.ViewHolder {
    public RViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setData(T t);
}
