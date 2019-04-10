package com.pgg.yixiannonapp.adapter.goods_detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.MainEntity;

import java.util.List;

/**
 * item页底部的推荐商品适配器
 */
public class ItemRecommendAdapter implements Holder<List<MainEntity.RecommendEntity>> {
    private View rootview;
    private GridView gv_recommend_goods;

    @Override
    public View createView(final Context context) {
        rootview = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_recommend, null);
        gv_recommend_goods =  rootview.findViewById(R.id.gv_recommend_goods);
        return rootview;
    }

    @Override
    public void UpdateUI(final Context context, int position, final List<MainEntity.RecommendEntity> data) {
        gv_recommend_goods.setAdapter(new ItemRecommendGoodsAdapter(context, data));
    }
}
