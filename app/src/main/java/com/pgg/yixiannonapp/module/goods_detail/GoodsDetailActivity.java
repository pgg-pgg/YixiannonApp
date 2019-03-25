package com.pgg.yixiannonapp.module.goods_detail;

import android.widget.TextView;

import com.gxz.PagerSlidingTabStrip;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.goods_detail.ItemTitlePagerAdapter;
import com.pgg.yixiannonapp.base.BaseCommonActivity;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.module.goods_detail.fragment.GoodsCommentFragment;
import com.pgg.yixiannonapp.module.goods_detail.fragment.GoodsDetailFragment;
import com.pgg.yixiannonapp.module.goods_detail.fragment.GoodsInfoFragment;
import com.pgg.yixiannonapp.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GoodsDetailActivity extends BaseCommonActivity {

    @BindView(R.id.psts_tabs)
    public PagerSlidingTabStrip psts_tabs;
    @BindView(R.id.vp_content)
    public NoScrollViewPager vp_content;
    @BindView(R.id.tv_title)
    public TextView tv_title;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private GoodsInfoFragment goodsInfoFragment;
    private GoodsDetailFragment goodsDetailFragment;
    private GoodsCommentFragment goodsCommentFragment;
    @Override
    public void initContentView() {
        setContentView(R.layout.activity_goods_detail);
    }

    @Override
    public void initView() {
        fragmentList.add(goodsInfoFragment = new GoodsInfoFragment());
        fragmentList.add(goodsDetailFragment = new GoodsDetailFragment());
        fragmentList.add(goodsCommentFragment = new GoodsCommentFragment());
        vp_content.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),
                fragmentList, new String[]{"商品", "详情", "评价"}));
        vp_content.setOffscreenPageLimit(3);
        psts_tabs.setViewPager(vp_content);
    }

    @Override
    public void initPresenter() {

    }
}
