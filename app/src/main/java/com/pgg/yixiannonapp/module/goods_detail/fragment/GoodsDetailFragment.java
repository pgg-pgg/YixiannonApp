package com.pgg.yixiannonapp.module.goods_detail.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.MainEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GoodsDetailFragment extends BaseFragment {

    @BindView(R.id.ll_goods_detail)
    RelativeLayout ll_goods_detail;
    @BindView(R.id.ll_goods_config)
    RelativeLayout ll_goods_config;
    @BindView(R.id.tv_goods_detail)
    TextView tv_goods_detail;
    @BindView(R.id.tv_goods_config)
    TextView tv_goods_config;
    @BindView(R.id.fl_content)
    FrameLayout fl_content;
    @BindView(R.id.view_line_config)
    View view_line_config;
    @BindView(R.id.view_line_detail)
    View view_line_detail;

    private int nowIndex;
    private float fromX;
    private List<TextView> tabTextList;
    private GoodsConfigFragment goodsConfigFragment;
    private GoodsInfoWebFragment goodsDetailWebFragment;
    private Fragment nowFragment;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private MainEntity.RecommendEntity recommendEntity;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_goods_detail;
    }

    public void setGoodsDetail(MainEntity.RecommendEntity recommendEntity){
        this.recommendEntity = recommendEntity;
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
    private void setData() {
        goodsConfigFragment = new GoodsConfigFragment();
        goodsDetailWebFragment = new GoodsInfoWebFragment();
        goodsConfigFragment.setData(recommendEntity);
        goodsDetailWebFragment.setUrlData(recommendEntity.getGoodsDetails());

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
                view_line_config.setVisibility(View.INVISIBLE);
                view_line_detail.setVisibility(View.VISIBLE);
                scrollCursor();
                break;

            case R.id.ll_goods_config:
                //规格参数tab
                switchFragment(nowFragment, goodsConfigFragment);
                nowIndex = 1;
                nowFragment = goodsConfigFragment;
                view_line_config.setVisibility(View.VISIBLE);
                view_line_detail.setVisibility(View.INVISIBLE);
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
        //设置Tab切换颜色
        for (int i = 0; i < tabTextList.size(); i++) {
            tabTextList.get(i).setTextColor(i == nowIndex ? getResources().getColor(R.color.colorAccent) : getResources().getColor(R.color.text_black));
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
