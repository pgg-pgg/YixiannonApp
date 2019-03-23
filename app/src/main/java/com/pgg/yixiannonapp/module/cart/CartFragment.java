package com.pgg.yixiannonapp.module.cart;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kennyc.view.MultiStateView;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.cart.CartRecycleViewAdapter;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.CartGoods;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CartFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.mMultiStateView)
    MultiStateView mMultiStateView;
    @BindView(R.id.mCartGoodsRv)
    RecyclerView mCartGoodsRv;
    @BindView(R.id.layout_title)
    TitleBar layout_title;

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_cart;
    }

    @Override
    public void initView() {
        initTopTitle();
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
}
