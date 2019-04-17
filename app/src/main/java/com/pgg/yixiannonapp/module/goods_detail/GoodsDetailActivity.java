package com.pgg.yixiannonapp.module.goods_detail;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gxz.PagerSlidingTabStrip;
import com.kennyc.view.MultiStateView;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.goods_detail.ItemTitlePagerAdapter;
import com.pgg.yixiannonapp.base.BaseCommonActivity;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.MainEntity;
import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.domain.UserStateBean;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.module.goods_detail.fragment.GoodsCommentFragment;
import com.pgg.yixiannonapp.module.goods_detail.fragment.GoodsDetailFragment;
import com.pgg.yixiannonapp.module.goods_detail.fragment.GoodsInfoFragment;
import com.pgg.yixiannonapp.net.httpData.HttpData;
import com.pgg.yixiannonapp.utils.SPUtils;
import com.pgg.yixiannonapp.widget.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
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
    private boolean isLogin;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private GoodsInfoFragment goodsInfoFragment;
    private GoodsDetailFragment goodsDetailFragment;
    private GoodsCommentFragment goodsCommentFragment;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_goods_detail);
    }

    @OnClick({R.id.tv_shop_business, R.id.btn_add_cart, R.id.btn_buy_cur})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_shop_business:
                if (isLogin){
                    //todo:跳转到商家信息
                }else {
                    showToast("请先登录后再选购商品");
                }
                break;
            case R.id.btn_add_cart:
                if (isLogin){
                    //todo:添加购物车
                    if (listener!=null){
                        listener.addCartGoodsData();
                    }
                }else {
                    showToast("请先登录后再选购商品");
                }
                break;
            case R.id.btn_buy_cur:
                if (isLogin){
                    //todo:购买商品
                    if (submitOrderListener!=null){
                        submitOrderListener.submitOrder();
                    }
                }else {
                    showToast("请先登录后再选购商品");
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(UserStateBean userStateBean) {
        if (userStateBean != null) {
            isLogin = !userStateBean.user_state.equals("0");
        }
    }


    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        isLogin = (SPUtils.get(getContext(),Constant.USER_STATE,"0")+"").equals("1");
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
                if (recommendEntityResults.getCode() == 0) {
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
        }, mTitle);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void initPresenter() {

    }
    public void setOnAddCartListener(OnAddCartListener listener){
        this.listener = listener;
    }
    private OnAddCartListener listener;

    public interface OnAddCartListener{
        void addCartGoodsData();
    }

    private OnSubmitOrderListener submitOrderListener;
    public void setOnSubmitOrderListener(OnSubmitOrderListener listener){
        this.submitOrderListener = listener;
    }

    public interface OnSubmitOrderListener{
        void submitOrder();
    }
}
