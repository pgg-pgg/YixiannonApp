package com.pgg.yixiannonapp.adapter.classify;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.ClassifyTypeEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassifyDescGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<ClassifyTypeEntity.GoodsTypeEntity> goodsTypeEntities;

    public ClassifyDescGridViewAdapter(Context mContext, List<ClassifyTypeEntity.GoodsTypeEntity> goodsTypeEntities) {
        this.mContext = mContext;
        this.goodsTypeEntities = goodsTypeEntities;
    }


    @Override
    public int getCount() {
        Log.e("pgg======",goodsTypeEntities.size()+"");
        return goodsTypeEntities == null ? 0 : goodsTypeEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsTypeEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_channel_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ClassifyTypeEntity.GoodsTypeEntity goodsTypeEntity = goodsTypeEntities.get(position);
        holder.tvChannel.setText(goodsTypeEntity.getGoodsName());
        Glide.with(mContext)
                .load(goodsTypeEntity.getGoodsImageUrl())
                .into(holder.ivChannel);
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.iv_channel)
        ImageView ivChannel;
        @BindView(R.id.tv_channel)
        TextView tvChannel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
