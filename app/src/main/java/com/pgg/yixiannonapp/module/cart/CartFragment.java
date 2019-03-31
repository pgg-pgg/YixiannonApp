package com.pgg.yixiannonapp.module.cart;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.cart.CartRecycleViewAdapter;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.CartGoods;
import com.pgg.yixiannonapp.domain.UserStateBean;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.module.login_register.login.LoginActivity;
import com.pgg.yixiannonapp.utils.SPUtils;
import com.pgg.yixiannonapp.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;

public class CartFragment extends BaseFragment {

    @BindView(R.id.mMultiStateView)
    MultiStateView mMultiStateView;
    @BindView(R.id.mCartGoodsRv)
    RecyclerView mCartGoodsRv;
    @BindView(R.id.layout_title)
    TitleBar layout_title;

    @BindView(R.id.rl_no_login)
    RelativeLayout rl_no_login;
    @BindView(R.id.tv_to_login)
    TextView tv_to_login;

    private boolean isLogin = false;


    @OnClick({R.id.tv_to_login})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_to_login:
                //去登录
                startActivity(new Intent(getContext(),LoginActivity.class));
                break;
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_cart;
    }

    @Override
    public void initView() {
        initTopTitle();
        EventBus.getDefault().register(this);
        isLogin = (SPUtils.get(getContext(), Constant.USER_STATE, "0") + "").equals("1");
        if (isLogin) {
            mMultiStateView.setVisibility(View.VISIBLE);
            rl_no_login.setVisibility(View.GONE);
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            List<CartGoods> goods = new ArrayList<>();
            for (int i = 0;i<5;i++){
                CartGoods cartGoods = new CartGoods();
                cartGoods.setGoodsCount(i+1);
                cartGoods.setGoodsDesc("aaaa"+i);
                cartGoods.setGoodsIcon(Constant.HOME_BANNER_FOUR);
                cartGoods.setGoodsId(i);
                cartGoods.setGoodsPrice(10l);
                cartGoods.setGoodsSku("10 元/斤");
                cartGoods.setSelected(true);
                goods.add(cartGoods);
            }
            mCartGoodsRv.setLayoutManager(new LinearLayoutManager(getContext()));
            mCartGoodsRv.setAdapter(new CartRecycleViewAdapter(R.layout.item_cart_goods,goods));
        } else {
            mMultiStateView.setVisibility(View.GONE);
            rl_no_login.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(UserStateBean userStateBean) {
        if (userStateBean != null) {
            if (userStateBean.user_state.equals("0")) {
                mMultiStateView.setVisibility(View.GONE);
                rl_no_login.setVisibility(View.VISIBLE);
            } else {
                mMultiStateView.setVisibility(View.VISIBLE);
                rl_no_login.setVisibility(View.GONE);
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                List<CartGoods> goods = new ArrayList<>();
                for (int i = 0;i<5;i++){
                    CartGoods cartGoods = new CartGoods();
                    cartGoods.setGoodsCount(i+1);
                    cartGoods.setGoodsDesc("aaaa"+i);
                    cartGoods.setGoodsIcon(Constant.HOME_BANNER_FOUR);
                    cartGoods.setGoodsId(i);
                    cartGoods.setGoodsPrice(10l);
                    cartGoods.setGoodsSku("10 元/斤");
                    cartGoods.setSelected(true);
                    goods.add(cartGoods);
                }
                mCartGoodsRv.setLayoutManager(new LinearLayoutManager(getContext()));
                mCartGoodsRv.setAdapter(new CartRecycleViewAdapter(R.layout.item_cart_goods,goods));
            }
        }
    }

    private void initTopTitle() {
        layout_title.setMainMsgVisible(false);
        layout_title.setSearchVisible(false);
        layout_title.setTitleName("购物车");
        layout_title.setMenuVisible(false);
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
