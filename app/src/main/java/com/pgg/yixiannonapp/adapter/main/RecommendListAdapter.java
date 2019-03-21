package com.pgg.yixiannonapp.adapter.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.MainEntity;
import com.pgg.yixiannonapp.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendListAdapter extends BaseAdapter {

    private Context context;
    private List<MainEntity.RecommendEntity> recommendEntities;

    public RecommendListAdapter(Context context, List<MainEntity.RecommendEntity> recommendEntities) {
        this.context = context;
        this.recommendEntities = recommendEntities;
    }

    @Override
    public int getCount() {
        return recommendEntities.size();
    }

    @Override
    public MainEntity.RecommendEntity getItem(int position) {
        return recommendEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_recommend_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MainEntity.RecommendEntity recommendEntity = recommendEntities.get(position);
        List<Integer> goodsLabel = recommendEntity.getGoodsLabel();
        if (goodsLabel != null && goodsLabel.size() >= 3) {
            holder.tv_multi_label.setVisibility(goodsLabel.get(0) != 0 ? View.VISIBLE : View.GONE);
            holder.iv_goods_label1.setVisibility(goodsLabel.get(1)!=0?View.VISIBLE:View.GONE);
            holder.iv_goods_label2.setVisibility(goodsLabel.get(2)!=0?View.VISIBLE:View.GONE);
        }
        GlideUtils.loadUrlImage(context,recommendEntity.getGoodsImageUrl(),holder.iv_recommend_goods);
        holder.tv_recommend_desc.setText(recommendEntity.getGoodsDesc());
        holder.tv_recommend_goods_view.setText(recommendEntity.getGoodsName());
        holder.tv_recommend_price.setText(recommendEntity.getGoodsPrice()+" 元/斤");
        holder.tv_recommend_address.setText(recommendEntity.getAddress());
        holder.tv_recommend_man_name.setText(recommendEntity.getManName());
        holder.tv_recommend_time.setText(recommendEntity.getReleaseTime());
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.iv_recommend_goods)
        ImageView iv_recommend_goods;
        @BindView(R.id.tv_multi_label)
        TextView tv_multi_label;
        @BindView(R.id.tv_recommend_goods_view)
        TextView tv_recommend_goods_view;

        @BindView(R.id.tv_recommend_price)
        TextView tv_recommend_price;

        @BindView(R.id.tv_recommend_address)
        TextView tv_recommend_address;

        @BindView(R.id.tv_recommend_man_name)
        TextView tv_recommend_man_name;

        @BindView(R.id.tv_recommend_time)
        TextView tv_recommend_time;

        @BindView(R.id.tv_recommend_desc)
        TextView tv_recommend_desc;

        @BindView(R.id.iv_goods_label1)
        ImageView iv_goods_label1;

        @BindView(R.id.iv_goods_label2)
        ImageView iv_goods_label2;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
