package com.ttdb.ttdbsdk.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by CK on 2016/10/20.
 */
public abstract class RAdapter<T> extends RecyclerView.Adapter<RViewHolder<T>> {
    private List<T> mList;
    private OnItemClickListener mListener = null;

    public RAdapter(List<T> list) {
        this.mList = list;
    }

    public void refresh(List<T> list){
        mList=list;
        notifyDataSetChanged();
    }


    @Override
    public RViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {

        return getHolder();
    }


    @Override
    public void onBindViewHolder(RViewHolder<T> holder, @SuppressLint("RecyclerView") final int position) {
        holder.setData(mList.get(position));
        if (mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(position, mList.get(position),v);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public abstract RViewHolder<T> getHolder();

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T t, View view);
    }
}
