package com.pgg.yixiannonapp.module.goods_detail;

import android.os.Bundle;
import android.widget.TextView;

import com.gxz.PagerSlidingTabStrip;
import com.kennyc.view.MultiStateView;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.goods_detail.ItemTitlePagerAdapter;
import com.pgg.yixiannonapp.base.BaseCommonActivity;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.MainEntity;
import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.module.goods_detail.fragment.GoodsCommentFragment;
import com.pgg.yixiannonapp.module.goods_detail.fragment.GoodsDetailFragment;
import com.pgg.yixiannonapp.module.goods_detail.fragment.GoodsInfoFragment;
import com.pgg.yixiannonapp.net.httpData.HttpData;
import com.pgg.yixiannonapp.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observer;

public class GoodsDetailActivity extends BaseCommonActivity {

    @BindView(R.id.psts_tabs)
    public PagerSlidingTabStrip psts_tabs;
    @BindView(R.id.vp_content)
    public NoScrollViewPager vp_content;
    @BindView(R.id.tv_title)
    public TextView tv_title;
    @BindView(R.id.mMultiStateView)
    MultiStateView mMultiStateView;

    private String mTitle;

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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mTitle = bundle.getString(Constant.GOODS_TITLE);
        }
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        HttpData.getInstance().getGoodsDetail(new Observer<Results<MainEntity.RecommendEntity>>() {
            @Override
            public void onCompleted() {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            }

            @Override
            public void onError(Throwable e) {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }

            @Override
            public void onNext(Results<MainEntity.RecommendEntity> recommendEntityResults) {
                if (recommendEntityResults.getCode()==0){
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    goodsDetailFragment = new GoodsDetailFragment();
                    goodsInfoFragment = new GoodsInfoFragment();
                    goodsCommentFragment = new GoodsCommentFragment();
                    goodsInfoFragment.setData(recommendEntityResults.getData());
                    goodsDetailFragment.setGoodsDetail(recommendEntityResults.getData());
                    goodsCommentFragment.setComments(recommendEntityResults.getData().getComments());
                    fragmentList.add(goodsInfoFragment);
                    fragmentList.add(goodsDetailFragment);
                    fragmentList.add(goodsCommentFragment);
                    vp_content.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),
                            fragmentList, new String[]{"商品", "详情", "评价"}));
                    vp_content.setOffscreenPageLimit(3);
                    psts_tabs.setViewPager(vp_content);
                }
            }
        },mTitle);

    }

    @Override
    public void initPresenter() {

    }
}
