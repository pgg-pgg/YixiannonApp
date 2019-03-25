package com.pgg.yixiannonapp.adapter.goods_detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pgg.yixiannonapp.base.BaseFragment;

import java.util.List;

/**
 * item页ViewPager的内容适配器
 */
public class ItemTitlePagerAdapter extends FragmentPagerAdapter {
    private String[] titleArray;
    private List<BaseFragment> fragmentList;

    public ItemTitlePagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList, String[] titleArray) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleArray = titleArray;
    }

    public void setFramentData(List<BaseFragment> fragmentList) {
        this.fragmentList = fragmentList;
        notifyDataSetChanged();
    }

    public void setTitleData(String[] titleArray) {
        this.titleArray = titleArray;
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArray[position];
    }

    @Override
    public int getCount() {
        return titleArray.length;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
}
