package com.pgg.yixiannonapp.adapter.classify;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.Classify.ClassifyDescEntity;
import com.pgg.yixiannonapp.domain.Classify.ClassifyItemEntity;
import com.pgg.yixiannonapp.domain.Classify.ClassifyTypeEntity;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.module.WebSafeActivity;
import com.pgg.yixiannonapp.widget.GridViewChannelView;

import java.util.List;

public class ClassifyRecycleRightAdapter extends BaseQuickAdapter<ClassifyItemEntity,BaseViewHolder> {


    public ClassifyRecycleRightAdapter(int layoutResId, List<ClassifyItemEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ClassifyItemEntity item) {
        ClassifyDescEntity classifyDescEntity = item.getClassifyDescEntity();
        helper.setText(R.id.tv_classify_title,classifyDescEntity.getTypeName());
        GridViewChannelView gr_classify_goods = helper.convertView.findViewById(R.id.gr_classify_goods);
        gr_classify_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext,WebSafeActivity.class);
                intent.putExtra(Constant.CLICK_URL,item.getGoodsTypeEntities().get(position).getClickUrl());
                intent.putExtra(Constant.CLICK_TITLE,item.getGoodsTypeEntities().get(position).getGoodsName());
                mContext.startActivity(intent);
            }
        });
        gr_classify_goods.setAdapter(new ClassifyDescGridViewAdapter(mContext,item.getGoodsTypeEntities()));
    }
}
