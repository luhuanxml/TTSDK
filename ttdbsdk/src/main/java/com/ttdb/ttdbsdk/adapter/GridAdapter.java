package com.ttdb.ttdbsdk.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ttdb.ttdbsdk.R;
import com.ttdb.ttdbsdk.bean.GoodEntity;

import java.util.List;

public class GridAdapter extends BaseAdapter {
    private List<GoodEntity.DataBean> list;
    private LayoutInflater inflater;

    public GridAdapter(Context context,List<GoodEntity.DataBean> list) {
        this.list = list;
        inflater=LayoutInflater.from(context);
    }

    public void refresh(List<GoodEntity.DataBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewGridViewHolder holder;
        if (convertView == null) {
            convertView=inflater.inflate(R.layout.new_area_item,null);
            holder=new NewGridViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (NewGridViewHolder) convertView.getTag();
        }
        holder.setData(list.get(position));
        return convertView;
    }
}
