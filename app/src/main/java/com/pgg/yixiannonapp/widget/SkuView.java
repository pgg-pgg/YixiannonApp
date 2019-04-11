package com.pgg.yixiannonapp.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.GoodsDetail.GoodsSku;
import com.pgg.yixiannonapp.domain.GoodsDetail.SkuChangeEvent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;

public class SkuView extends FrameLayout {

    private GoodsSku goodsSku;
    private TextView mSkuTitleTv;
    private TagFlowLayout mSkuContentView;
    private Context context;

    public SkuView(@NonNull Context context) {
        this(context,null);
    }

    public SkuView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SkuView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.layout_sku_view,this);
        mSkuTitleTv = view.findViewById(R.id.mSkuTitleTv);
        mSkuContentView = view.findViewById(R.id.mSkuContentView);
        this.context = context;
    }

    public void setGoodsSku(GoodsSku goodsSku){
        this.goodsSku = goodsSku;
        mSkuTitleTv.setText(goodsSku.getGoods_sku_title());
        mSkuContentView.setAdapter(new TagAdapter<String>(goodsSku.getSkuContent()) {
            @Override
            public View getView(FlowLayout parent, int position, String string) {
                TextView view = (TextView) LayoutInflater.from(context).inflate(R.layout.layout_sku_item,parent,false);
                view.setText(string);
                return view;
            }
        });
        mSkuContentView.getAdapter().setSelectedList(0);
        mSkuContentView.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                EventBus.getDefault().post(new SkuChangeEvent());
                return true;
            }
        });
    }

    public String getSkuInfo(){
        return mSkuTitleTv.getText().toString()+","+goodsSku.getSkuContent().get(mSkuContentView.getSelectedList().iterator().next());
    }
}
