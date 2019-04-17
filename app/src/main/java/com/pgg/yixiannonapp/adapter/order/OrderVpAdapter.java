package com.pgg.yixiannonapp.adapter.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pgg.yixiannonapp.module.order.common.OrderConstant;
import com.pgg.yixiannonapp.module.order.fragment.OrderFragment;

public class OrderVpAdapter extends FragmentPagerAdapter {

    private String[] titles = new String[]{"全部","待付款","待收货","已完成","待评价"};
    public OrderVpAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        OrderFragment orderFragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(OrderConstant.KEY_ORDER_STATUS,position);
        orderFragment.setArguments(bundle);
        return orderFragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
