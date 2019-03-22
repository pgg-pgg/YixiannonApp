package com.pgg.yixiannonapp.adapter.classify;

import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.ClassifyTypeEntity;
import com.pgg.yixiannonapp.widget.GridViewChannelView;

import java.util.List;

public class ClassifyRecycleRightAdapter extends BaseQuickAdapter<ClassifyTypeEntity.ClassifyDescEntity,BaseViewHolder> {


    public ClassifyRecycleRightAdapter(int layoutResId, List<ClassifyTypeEntity.ClassifyDescEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassifyTypeEntity.ClassifyDescEntity item) {
        helper.setText(R.id.tv_classify_title,item.getTypeName());
        GridViewChannelView gr_classify_goods = helper.convertView.findViewById(R.id.gr_classify_goods);
        gr_classify_goods.setAdapter(new ClassifyDescGridViewAdapter(mContext,item.getGoodsTypeEntities()));
    }
}
