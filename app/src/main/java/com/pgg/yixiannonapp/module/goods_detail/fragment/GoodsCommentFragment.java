package com.pgg.yixiannonapp.module.goods_detail.fragment;

import android.content.Context;
import android.widget.TextView;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.module.goods_detail.GoodsDetailActivity;

public class GoodsCommentFragment extends BaseFragment {
    public TextView tv_comment_count, tv_good_comment;
    public GoodsDetailActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (GoodsDetailActivity) context;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_goods_comment;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }
}
