package com.pgg.yixiannonapp.module.main;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.utils.GlideUtils;
import com.pgg.yixiannonapp.widget.SearchTextFlipperView;
import com.pgg.yixiannonapp.widget.TitleBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

import butterknife.BindView;

import static butterknife.internal.Utils.listOf;
import static com.pgg.yixiannonapp.global.Constant.HOME_BANNER_FOUR;
import static com.pgg.yixiannonapp.global.Constant.HOME_BANNER_ONE;
import static com.pgg.yixiannonapp.global.Constant.HOME_BANNER_THREE;
import static com.pgg.yixiannonapp.global.Constant.HOME_BANNER_TWO;

public class MainFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.ll_main_search)
    SearchTextFlipperView ll_main_search;
    @BindView(R.id.mMainBanner)
    Banner mMainBanner;
    @BindView(R.id.rv_main_view)
    RecyclerView rv_main_view;

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView() {
        //初始化顶部搜索栏
        initTopTitle();
        //初始化bannner
        initBanner();
    }

    private void initBanner() {
        mMainBanner.setImageLoader(new BannerImageLoader());
        mMainBanner.setImages(listOf(HOME_BANNER_ONE, HOME_BANNER_TWO, HOME_BANNER_THREE, HOME_BANNER_FOUR));
        mMainBanner.setBannerAnimation(Transformer.Accordion);
        mMainBanner.setDelayTime(2000);
        //设置指示器位置
        mMainBanner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner全部设置完毕后调用
        mMainBanner.start();
    }

    private void initTopTitle() {
        ArrayList<String> data = new ArrayList<>();
        data.add(getString(R.string.top_search1));
        data.add(getString(R.string.top_search2));
        ll_main_search.setData(data);
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }

    private class BannerImageLoader extends ImageLoader{
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            GlideUtils.loadUrlImage(context,path.toString(),imageView);
        }
    }
}
