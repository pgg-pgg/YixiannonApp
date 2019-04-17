package com.pgg.yixiannonapp.module.order;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.order.OrderVpAdapter;
import com.pgg.yixiannonapp.base.BaseCommonActivity;
import com.pgg.yixiannonapp.module.order.common.OrderConstant;
import com.pgg.yixiannonapp.module.order.common.OrderStatus;

import butterknife.BindView;

public class OrderActivity extends BaseCommonActivity {

    @BindView(R.id.mOrderTab)
    TabLayout mOrderTab;
    @BindView(R.id.mOrderVp)
    ViewPager mOrderVp;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_order);
    }

    @Override
    public void initView() {
        mOrderTab.setTabMode(TabLayout.MODE_FIXED);
        mOrderVp.setAdapter(new OrderVpAdapter(getSupportFragmentManager()));
        mOrderTab.setupWithViewPager(mOrderVp);
        mOrderVp.setCurrentItem(getIntent().getIntExtra(OrderConstant.KEY_ORDER_STATUS,OrderStatus.ORDER_ALL));
    }

    @Override
    public void initPresenter() {

    }
}
