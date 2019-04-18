package com.pgg.yixiannonapp.module.order;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.order.OrderVpAdapter;
import com.pgg.yixiannonapp.base.BaseCommonActivity;
import com.pgg.yixiannonapp.module.order.common.OrderConstant;
import com.pgg.yixiannonapp.module.order.common.OrderStatus;
import com.pgg.yixiannonapp.widget.TitleBar;

import butterknife.BindView;

public class OrderActivity extends BaseCommonActivity {

    @BindView(R.id.mOrderTab)
    TabLayout mOrderTab;
    @BindView(R.id.mOrderVp)
    ViewPager mOrderVp;
    @BindView(R.id.layout_title)
    TitleBar layout_title;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_order);
    }

    @Override
    public void initView() {
        initTopTitle();
        mOrderTab.setTabMode(TabLayout.MODE_FIXED);
        mOrderVp.setAdapter(new OrderVpAdapter(getSupportFragmentManager()));
        mOrderTab.setupWithViewPager(mOrderVp);
        mOrderVp.setCurrentItem(getIntent().getIntExtra(OrderConstant.KEY_ORDER_STATUS,OrderStatus.ORDER_ALL));
    }

    private void initTopTitle() {
        layout_title.setTitleName("订单管理");
        layout_title.setMainMsgVisible(false);
        layout_title.setSearchVisible(false);
        layout_title.setMenuVisible(false);
        layout_title.setRightText("");
        layout_title.setLeftImage(R.drawable.icon_back);
        layout_title.setLeftClickListener(new TitleBar.LeftClickListener() {
            @Override
            public void setLeftOnClickListener() {
                finish();
            }
        });
    }

    @Override
    public void initPresenter() {

    }
}
