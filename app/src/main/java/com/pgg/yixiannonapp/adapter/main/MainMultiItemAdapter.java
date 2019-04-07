package com.pgg.yixiannonapp.adapter.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.MainEntity;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.module.WebSafeActivity;
import com.pgg.yixiannonapp.utils.GlideUtils;
import com.pgg.yixiannonapp.widget.GridViewChannelView;
import com.pgg.yixiannonapp.widget.SearchTextFlipperView;
import com.pgg.yixiannonapp.widget.TopTitleView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.OVER_SCROLL_NEVER;
import static butterknife.internal.Utils.listOf;
import static com.pgg.yixiannonapp.global.Constant.HOME_BANNER_FOUR;
import static com.pgg.yixiannonapp.global.Constant.HOME_BANNER_ONE;
import static com.pgg.yixiannonapp.global.Constant.HOME_BANNER_THREE;
import static com.pgg.yixiannonapp.global.Constant.HOME_BANNER_TWO;

public class MainMultiItemAdapter extends BaseMultiItemQuickAdapter<MainEntity, BaseViewHolder> {

    public RecommendListAdapter recommendListAdapter;

    public MainMultiItemAdapter(List<MainEntity> data) {
        super(data);
        addItemType(MainEntity.BANNER_TYPE, R.layout.layout_main_banner);
        addItemType(MainEntity.CHANNEL_TYPE, R.layout.layout_main_channel);
        addItemType(MainEntity.COMMENT_TYPE, R.layout.layout_news_flipper);
        addItemType(MainEntity.TOP_TYPE, R.layout.layout_top_news);
        addItemType(MainEntity.RECOMMEND_TYPE, R.layout.layout_recommend_view);
    }

    @Override
    public void setData(int index, MainEntity data) {
        super.setData(index, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainEntity item) {
        MainEntity homeEntity = item;
        final View convertView = helper.getConvertView();
        switch (homeEntity.getItemType()) {
            //顶部轮播图
            case MainEntity.BANNER_TYPE:
                final List<MainEntity.BannerEntity> bannerEntities = homeEntity.getBannerEntities();
                if (bannerEntities != null) {
                    List<String> images = new ArrayList<>();
                    for(int i = 0;i<bannerEntities.size();i++){
                        images.add(Constant.BASE_URL+bannerEntities.get(i).getBannerImageUrl());
                    }
                    Banner mMainBanner = convertView.findViewById(R.id.mMainBanner);
                    mMainBanner.setImageLoader(new BannerImageLoader());
                    mMainBanner.setImages(images);
                    mMainBanner.setBannerAnimation(Transformer.RotateDown);
                    mMainBanner.setDelayTime(2000);
                    //设置指示器位置
                    mMainBanner.setIndicatorGravity(BannerConfig.RIGHT);
                    //banner全部设置完毕后调用
                    mMainBanner.start();
                    mMainBanner.setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            Intent intent = new Intent(mContext,WebSafeActivity.class);
                            intent.putExtra(Constant.CLICK_URL,bannerEntities.get(position).getClickUrl());
                            mContext.startActivity(intent);
                        }
                    });
                }
            case MainEntity.CHANNEL_TYPE:
                //频道
                List<MainEntity.ChannelEntity> channelEntities = homeEntity.getChannelEntities();
                if (channelEntities != null) {
                    final int size = channelEntities.size();
                    ViewPager mViewPagerChannel = convertView.findViewById(R.id.vp_main_channel);
                    final View indicator_left = convertView.findViewById(R.id.v_indicator_left);
                    final View indicator_right = convertView.findViewById(R.id.v_indicator_right);
                    final View ll_indicator = convertView.findViewById(R.id.ll_indicator);

                    mViewPagerChannel.setAdapter(new ChannelViewPagerAdapter(convertView.getContext(), channelEntities));
                    mViewPagerChannel.setOverScrollMode(OVER_SCROLL_NEVER);
                    mViewPagerChannel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            if (size <= 10) {
                                ll_indicator.setVisibility(View.GONE);
                            } else {
                                indicator_left.setBackgroundResource(position == 0 ? R.drawable.corner_indicator_bg : R.drawable.corner_news_bg);
                                indicator_right.setBackgroundResource(position == 1 ? R.drawable.corner_indicator_bg : R.drawable.corner_news_bg);
                            }
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                }
            case MainEntity.COMMENT_TYPE:
                //公告
                List<MainEntity.CommentEntity> commentEntities = homeEntity.getCommentEntities();
                if (commentEntities != null) {
                    SearchTextFlipperView mFlipperView = convertView.findViewById(R.id.stf_comment_view);
                    mFlipperView.setLeftImage(R.drawable.icon_news);
                    mFlipperView.setLineVertical(true);
                    mFlipperView.setContainerBackground(R.drawable.corner_news_bg);
                    List<String> names = new ArrayList<>();
                    for (int i = 0; i < commentEntities.size(); i++) {
                        names.add(commentEntities.get(i).getShowText());
                    }

                    mFlipperView.setData(names);
                }
            case MainEntity.TOP_TYPE:
                //农头条
                List<MainEntity.TopNewsEntity> topNewsEntities = homeEntity.getTopNewsEntities();
                if (topNewsEntities != null) {
                    TopTitleView ttv_news_view = convertView.findViewById(R.id.ttv_news_view);
                    GridViewChannelView gr_top_news = convertView.findViewById(R.id.gr_top_news);
                    ttv_news_view.setTopTitleName(TopTitleView.TOP_TYPE);
                    ttv_news_view.setTopLookListener(new TopTitleView.TopLookListener() {
                        @Override
                        public void setTopLookOnClickLister(int type) {
                            //Todo:跳转到对应的类别
                            Toast.makeText(convertView.getContext(), "农头条"+type, Toast.LENGTH_SHORT).show();
                        }
                    });
                    gr_top_news.setAdapter(new TopNewsGridViewAdapter(mContext, topNewsEntities));
                    gr_top_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(convertView.getContext(), position + "", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            case MainEntity.RECOMMEND_TYPE:
                //为您推荐
                List<MainEntity.RecommendEntity> recommendEntities = homeEntity.getRecommendEntities();
                if (recommendEntities!=null){
                    TopTitleView ttv_news_view = convertView.findViewById(R.id.ttv_recommend_view);
                    ttv_news_view.setTopTitleName(TopTitleView.RECOMMEND_TYPE);
                    ttv_news_view.setTopLookListener(new TopTitleView.TopLookListener() {
                        @Override
                        public void setTopLookOnClickLister(int type) {
                            Toast.makeText(convertView.getContext(), "为您推荐"+type, Toast.LENGTH_SHORT).show();
                        }
                    });
                    GridViewChannelView lv_recommend_view = convertView.findViewById(R.id.lv_recommend_view);
                    lv_recommend_view.setAdapter(new RecommendListAdapter(convertView.getContext(),recommendEntities));
                    lv_recommend_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //todo:推荐商品
                            Toast.makeText(convertView.getContext(), position + "", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

        }
    }

    private class BannerImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            GlideUtils.loadUrlImage(context, path.toString(), imageView);
        }
    }
}
