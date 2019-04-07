package com.pgg.yixiannonapp.adapter.classify;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.Classify.ClassifyDescEntity;
import com.pgg.yixiannonapp.domain.Classify.ClassifyItemEntity;
import com.pgg.yixiannonapp.domain.Classify.ClassifyTypeEntity;
import com.pgg.yixiannonapp.widget.GridViewChannelView;

import java.util.List;

public class ClassifyRecycleRightAdapter extends BaseQuickAdapter<ClassifyItemEntity,BaseViewHolder> {


    public ClassifyRecycleRightAdapter(int layoutResId, List<ClassifyItemEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassifyItemEntity item) {
        ClassifyDescEntity classifyDescEntity = item.getClassifyDescEntity();
        helper.setText(R.id.tv_classify_title,classifyDescEntity.getTypeName());
        GridViewChannelView gr_classify_goods = helper.convertView.findViewById(R.id.gr_classify_goods);
        gr_classify_goods.setAdapter(new ClassifyDescGridViewAdapter(mContext,item.getGoodsTypeEntities()));
    }
}
