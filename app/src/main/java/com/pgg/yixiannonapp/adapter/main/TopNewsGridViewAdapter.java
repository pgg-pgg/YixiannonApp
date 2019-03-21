package com.pgg.yixiannonapp.adapter.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.MainEntity;
import com.pgg.yixiannonapp.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopNewsGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<MainEntity.TopNewsEntity> topNewsEntityList;

    public TopNewsGridViewAdapter(Context mContext, List<MainEntity.TopNewsEntity> topNewsEntityList) {
        this.mContext = mContext;
        this.topNewsEntityList = topNewsEntityList;
    }


    @Override
    public int getCount() {
        return topNewsEntityList == null ? 0 : topNewsEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return topNewsEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_top_news_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MainEntity.TopNewsEntity topNewsEntity = topNewsEntityList.get(position);
        holder.tv_goods_name.setText(topNewsEntity.getTopName());
        holder.tv_top_desc.setText(topNewsEntity.getTopDesc());
        GlideUtils.loadImage(mContext,topNewsEntity.getLeftTopImageUrl(),holder.iv_left_top_view);
        if (topNewsEntity.getGoodsUrls()!=null&&topNewsEntity.getGoodsUrls().size()>=2){
            GlideUtils.loadImage(mContext,topNewsEntity.getGoodsUrls().get(0),holder.iv_goods_1);
            GlideUtils.loadImage(mContext,topNewsEntity.getGoodsUrls().get(1),holder.iv_goods_2);
        }
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.iv_left_top_view)
        ImageView iv_left_top_view;
        @BindView(R.id.iv_goods_1)
        ImageView iv_goods_1;
        @BindView(R.id.iv_goods_2)
        ImageView iv_goods_2;
        @BindView(R.id.tv_goods_name)
        TextView tv_goods_name;
        @BindView(R.id.tv_top_desc)
        TextView tv_top_desc;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
