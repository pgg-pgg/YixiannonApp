package com.pgg.yixiannonapp.adapter.classify;

import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.Classify.ClassifyTypeEntity;

import java.util.List;

public class ClassifyRecycleLeftAdapter extends BaseQuickAdapter<ClassifyTypeEntity,BaseViewHolder> {


    public ClassifyRecycleLeftAdapter(int layoutResId, List<ClassifyTypeEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassifyTypeEntity item) {
        helper.setText(R.id.tv_type_name,item.getTypeName());
        helper.convertView.findViewById(R.id.v_is_check).setVisibility(item.getIsSelected()?View.VISIBLE:View.INVISIBLE);
        helper.convertView.findViewById(R.id.ll_type_container).setBackgroundColor(item.getIsSelected()?Color.WHITE:Color.LTGRAY);
        helper.addOnClickListener(R.id.ll_type_container);
    }
}
