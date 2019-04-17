package com.pgg.yixiannonapp.adapter.order;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.order.OrderGoods;
import com.pgg.yixiannonapp.utils.GlideUtils;

import java.util.List;

public class OrderGoodsAdapter extends BaseQuickAdapter<OrderGoods,BaseViewHolder> {

    public OrderGoodsAdapter(int layoutResId, List<OrderGoods> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderGoods item) {
        ImageView mGoodsIconIv = helper.convertView.findViewById(R.id.mGoodsIconIv);
        TextView mGoodsDescTv = helper.convertView.findViewById(R.id.mGoodsDescTv);
        TextView mGoodsSkuTv = helper.convertView.findViewById(R.id.mGoodsSkuTv);
        TextView mGoodsPriceTv = helper.convertView.findViewById(R.id.mGoodsPriceTv);
        TextView mGoodsCountTv = helper.convertView.findViewById(R.id.mGoodsCountTv);
        GlideUtils.loadImage(mContext,item.getGoodsIcon(),mGoodsIconIv);
        mGoodsDescTv.setText(item.getGoodsDesc());
        mGoodsSkuTv.setText(item.getGoodsSku());
        mGoodsPriceTv.setText(item.getGoodsPrice()+"元/斤");
        mGoodsCountTv.setText(item.getGoodsCount()+"");
    }
}
