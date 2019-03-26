package com.pgg.yixiannonapp.module.goods_detail.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GoodsDetailFragment extends BaseFragment {

    @BindView(R.id.ll_goods_detail)
    LinearLayout ll_goods_detail;
    @BindView(R.id.ll_goods_config)
    LinearLayout ll_goods_config;
    @BindView(R.id.tv_goods_detail)
    TextView tv_goods_detail;
    @BindView(R.id.tv_goods_config)
    TextView tv_goods_config;
    @BindView(R.id.fl_content)
    FrameLayout fl_content;
    @BindView(R.id.v_tab_cursor)
    View v_tab_cursor;

    private int nowIndex;
    private float fromX;
    private List<TextView> tabTextList;
    private GoodsConfigFragment goodsConfigFragment;
    private GoodsDetailWebFragment goodsDetailWebFragment;
    private Fragment nowFragment;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_goods_detail;
    }

    @Override
    public void initView() {
        tabTextList = new ArrayList<>();
        tabTextList.add(tv_goods_detail);
        tabTextList.add(tv_goods_config);
        setData();
    }

    /**
     * 商品信息Fragment页获取完数据执行
     */
    public void setData() {
        goodsConfigFragment = new GoodsConfigFragment();
        goodsDetailWebFragment = new GoodsDetailWebFragment();

        nowFragment = goodsDetailWebFragment;
        fragmentManager = getChildFragmentManager();
        //默认显示商品详情tab
        fragmentManager.beginTransaction().replace(R.id.fl_content, nowFragment).commitAllowingStateLoss();
    }

    @OnClick({R.id.ll_goods_detail,R.id.ll_goods_config})
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.ll_goods_detail:
                //商品详情tab
                switchFragment(nowFragment, goodsDetailWebFragment);
                nowIndex = 0;
                nowFragment = goodsDetailWebFragment;
                scrollCursor();
                break;

            case R.id.ll_goods_config:
                //规格参数tab
                switchFragment(nowFragment, goodsConfigFragment);
                nowIndex = 1;
                nowFragment = goodsConfigFragment;
                scrollCursor();
                break;

            default:
                break;
        }
    }

    /**
     * 滑动游标
     */
    private void scrollCursor() {
        TranslateAnimation anim = new TranslateAnimation(fromX, nowIndex * v_tab_cursor.getWidth(), 0, 0);
        anim.setFillAfter(true);//设置动画结束时停在动画结束的位置
        anim.setDuration(50);
        //保存动画结束时游标的位置,作为下次滑动的起点
        fromX = nowIndex * v_tab_cursor.getWidth();
        v_tab_cursor.startAnimation(anim);

        //设置Tab切换颜色
        for (int i = 0; i < tabTextList.size(); i++) {
            tabTextList.get(i).setTextColor(i == nowIndex ? getResources().getColor(R.color.text_red) : getResources().getColor(R.color.text_black));
        }
    }

    /**
     * 切换Fragment
     * <p>(hide、show、add)
     * @param fromFragment
     * @param toFragment
     */
    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        if (nowFragment != toFragment) {
            fragmentTransaction = fragmentManager.beginTransaction();
            if (!toFragment.isAdded()) {    // 先判断是否被add过
                fragmentTransaction.hide(fromFragment).add(R.id.fl_content, toFragment).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到activity中
            } else {
                fragmentTransaction.hide(fromFragment).show(toFragment).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }
}
