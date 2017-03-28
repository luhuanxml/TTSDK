package com.ttdb.ttdbsdk.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ttdb.ttdbsdk.R;
import com.ttdb.ttdbsdk.bean.GoodEntity;
import com.ttdb.ttdbsdk.common._Picasso;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class NewGridViewHolder {
    private TextView new_area_item_name;
    private TextView new_area_item_jindu;
    private ImageView new_area_item_icon;
    private ProgressBar new_area_item_pbar;

    public NewGridViewHolder(View itemView) {
        new_area_item_name= (TextView) itemView.findViewById(R.id.new_area_item_name);
        new_area_item_jindu= (TextView) itemView.findViewById(R.id.new_area_item_jindu);
        new_area_item_icon= (ImageView) itemView.findViewById(R.id.new_area_item_icon);
        new_area_item_pbar= (ProgressBar) itemView.findViewById(R.id.new_area_item_pbar);
    }

    public void setData(GoodEntity.DataBean dataBean) {
        new_area_item_name.setText(dataBean.getName());
        _Picasso.loadImg(dataBean.getIcon(),new_area_item_icon);
        double joinPer=Double.valueOf(dataBean.getJoin_per());
        int joinPerI= (int) joinPer;
        new_area_item_pbar.setProgress(joinPerI);
        new_area_item_jindu.setText(String.format("%s%%", dataBean.getJoin_per()));
    }
}
