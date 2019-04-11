package com.pgg.yixiannonapp.module.goods_detail.fragment;

import android.content.Context;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.gxz.PagerSlidingTabStrip;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.goods_detail.ItemCommentsAdapter;
import com.pgg.yixiannonapp.adapter.goods_detail.ItemRecommendAdapter;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.Comments;
import com.pgg.yixiannonapp.domain.GoodsDetail.GoodsSku;
import com.pgg.yixiannonapp.domain.MainEntity;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.module.goods_detail.GoodsDetailActivity;
import com.pgg.yixiannonapp.utils.GlideUtils;
import com.pgg.yixiannonapp.widget.GoodsSkuPopup;
import com.pgg.yixiannonapp.widget.SlideDetailsLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GoodsInfoFragment extends BaseFragment implements SlideDetailsLayout.OnSlideDetailsListener {

    private PagerSlidingTabStrip psts_tabs;
    @BindView(R.id.sv_switch)
    SlideDetailsLayout sv_switch;
    @BindView(R.id.sv_goods_info)
    ScrollView sv_goods_info;
    @BindView(R.id.fab_up_slide)
    FloatingActionButton fab_up_slide;
    @BindView(R.id.vp_item_goods_img)
    Banner vp_item_goods_img;
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
    @BindView(R.id.ll_current_goods)
    LinearLayout ll_current_goods;
    @BindView(R.id.ll_activity)
    LinearLayout ll_activity;
    @BindView(R.id.ll_comment)
    LinearLayout ll_comment;
    @BindView(R.id.ll_recommend)
    LinearLayout ll_recommend;
    @BindView(R.id.ll_pull_up)
    LinearLayout ll_pull_up;
    @BindView(R.id.tv_goods_title)
    TextView tv_goods_title;
    @BindView(R.id.tv_goods_address)
    TextView tv_goods_address;
    @BindView(R.id.tv_goods_man_name)
    TextView tv_goods_man_name;
    @BindView(R.id.tv_goods_desc)
    TextView tv_goods_desc;
    @BindView(R.id.tv_new_price)
    TextView tv_new_price;
    @BindView(R.id.tv_old_price)
    TextView tv_old_price;
    @BindView(R.id.tv_current_goods)
    TextView tv_current_goods;
    @BindView(R.id.tv_comment_count)
    TextView tv_comment_count;
    @BindView(R.id.tv_good_comment)
    TextView tv_good_comment;
    @BindView(R.id.lv_comments)
    ListView lv_comments;
    @BindView(R.id.tv_label_1)
    TextView tv_label_1;
    @BindView(R.id.tv_label_2)
    TextView tv_label_2;
    @BindView(R.id.tv_none_comments)
    TextView tv_none_comments;
    @BindView(R.id.vp_recommend)
    ConvenientBanner vp_recommend;
    @BindView(R.id.view_line_config)
    View view_line_config;
    @BindView(R.id.view_line_detail)
    View view_line_detail;
    private GoodsSkuPopup mSkuPop;
    private Animation mAnimationEnd, mAnimationStart;
    private View contentView;

    /**
     * 当前商品详情数据页的索引分别是图文详情、规格参数
     */
    private int nowIndex;
    public GoodsConfigFragment goodsConfigFragment;
    public GoodsInfoWebFragment goodsInfoWebFragment;
    private Fragment nowFragment;
    private List<TextView> tabTextList;
    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    public GoodsDetailActivity activity;
    private MainEntity.RecommendEntity data;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (GoodsDetailActivity) context;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_goods_info;
    }

    public void setData(MainEntity.RecommendEntity data) {
        this.data = data;
    }

    @Override
    public void initView() {
        fragmentList = new ArrayList<>();
        tabTextList = new ArrayList<>();
        tabTextList.add(tv_goods_detail);
        tabTextList.add(tv_goods_config);
        contentView = getActivity().findViewById(android.R.id.content);
        initAnim();
        initData(data);
        //设置文字中间一条横线
        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        fab_up_slide.hide();

    }

    private void initData(MainEntity.RecommendEntity data) {
        List<MainEntity.RecommendEntity> recommendEntities = data.getRecommends();
        String banner = data.getGoodsBannerUrl();
        String[] split = banner.split(",");
        //初始化banner
        initBanner(split);
        //初始化商品信息
        initTopInfo(data);
        //初始化小农推荐
        initRecommendGoods(recommendEntities);
        initDetailData(data);
        initSkuPop();
        loadPopData(data);
    }

    /**
     * 初始化弹窗的动画
     */
    private void initAnim() {
        //弹窗出现动画
        mAnimationStart = new ScaleAnimation(1f, 0.95f, 1f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimationStart.setDuration(500);
        mAnimationStart.setFillAfter(true);

        //弹窗消失动画
        mAnimationEnd = new ScaleAnimation(0.95f, 1f, 0.95f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimationEnd.setDuration(500);
        mAnimationEnd.setFillAfter(true);
    }

    private void initSkuPop() {
        mSkuPop = new GoodsSkuPopup(getActivity());
        mSkuPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                contentView.startAnimation(mAnimationEnd);
            }
        });
    }

    /**
     * 加载SKU数据
     *
     * @param data
     */
    private void loadPopData(MainEntity.RecommendEntity data) {
        String defaultSku = "";
        for (GoodsSku goodsSku : data.getGoodsSkus()) {
            defaultSku += goodsSku.getSkuContent().get(0) + ",";
        }
        defaultSku = defaultSku.substring(0,defaultSku.length()-1);
        tv_current_goods.setText(defaultSku);
        mSkuPop.setGoodsIcon(Constant.BASE_URL + data.getGoodsImageUrl());
        mSkuPop.setGoodsCode(data.getId() + "");
        mSkuPop.setmGoodsPrice(data.getGoodsPrice() + "元/斤");
        mSkuPop.setSkuData(data.getGoodsSkus());
    }

    private void initTopInfo(MainEntity.RecommendEntity data) {
        tv_goods_title.setText(data.getGoodsName());
        tv_goods_address.setText(data.getAddress());
        tv_goods_man_name.setText(data.getManName());
        tv_goods_desc.setText(data.getGoodsDesc());
        tv_new_price.setText(data.getGoodsPrice());
        tv_old_price.setText("¥" + data.getGoodsOldPrice());
        String goodsLabel = data.getGoodsLabel();
        String[] goodsLabelArray = goodsLabel.split(",");
        if (goodsLabelArray != null && goodsLabelArray.length >= 3) {
            tv_label_1.setVisibility(!goodsLabelArray[1].equals("0") ? View.VISIBLE : View.GONE);
            tv_label_2.setVisibility(!goodsLabelArray[2].equals("0") ? View.VISIBLE : View.GONE);
        }
        List<Comments> comments = data.getComments();
        int startNums = 0;
        for (int i = 0; i < comments.size(); i++) {
            startNums += comments.get(i).getRatingStar();
        }
        float percent = (float) startNums / (5 * comments.size());
        tv_comment_count.setText("(" + comments.size() + ")");
        tv_good_comment.setText((percent * 100) + "%");
        if (comments.size() > 0) {
            tv_none_comments.setVisibility(View.GONE);
            lv_comments.setVisibility(View.VISIBLE);
            lv_comments.setAdapter(new ItemCommentsAdapter(getContext(), data.getComments()));
        }
    }

    private void initBanner(String[] split) {
        List<String> images = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            images.add(Constant.BASE_URL + split[i]);
        }
        vp_item_goods_img.setImages(images);
        vp_item_goods_img.setImageLoader(new BannerImageLoader());
        vp_item_goods_img.setImages(images);
        vp_item_goods_img.setBannerAnimation(Transformer.RotateDown);
        vp_item_goods_img.setDelayTime(2000);
        //设置指示器位置
        vp_item_goods_img.setIndicatorGravity(BannerConfig.CENTER);
        //banner全部设置完毕后调用
        vp_item_goods_img.start();
    }

    /**
     * 加载完商品详情执行
     *
     * @param data
     */
    public void initDetailData(MainEntity.RecommendEntity data) {
        goodsConfigFragment = new GoodsConfigFragment();
        goodsConfigFragment.setData(data);
        goodsInfoWebFragment = new GoodsInfoWebFragment();
        goodsInfoWebFragment.setUrlData(data.getGoodsDetails());
        fragmentList.add(goodsConfigFragment);
        fragmentList.add(goodsInfoWebFragment);
        nowFragment = goodsInfoWebFragment;
        fragmentManager = getChildFragmentManager();
        //默认显示商品详情tab
        fragmentManager.beginTransaction().replace(R.id.fl_content, nowFragment).commitAllowingStateLoss();
    }

    /**
     * 设置推荐商品
     *
     * @param recommendEntities
     */
    public void initRecommendGoods(List<MainEntity.RecommendEntity> recommendEntities) {
        List<List<MainEntity.RecommendEntity>> handledData = handleRecommendGoods(recommendEntities);
        //设置如果只有一组数据时不能滑动
        vp_recommend.setManualPageable(handledData.size() == 1 ? false : true);
        vp_recommend.setCanLoop(handledData.size() == 1 ? false : true);
        vp_recommend.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new ItemRecommendAdapter();
            }
        }, handledData);
        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
        vp_recommend.setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.drawable.shape_item_index_red});
        vp_recommend.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    @OnClick({R.id.fab_up_slide, R.id.ll_current_goods, R.id.ll_activity,
            R.id.ll_comment, R.id.ll_pull_up, R.id.ll_goods_detail, R.id.ll_goods_config})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_pull_up:
                //上拉查看图文详情
                sv_switch.smoothOpen(true);
                break;

            case R.id.fab_up_slide:
                //点击滑动到顶部
                sv_goods_info.smoothScrollTo(0, 0);
                sv_switch.smoothClose(true);
                break;

            case R.id.ll_goods_detail:
                //商品详情tab
                nowIndex = 0;
                scrollCursor();
                view_line_config.setVisibility(View.INVISIBLE);
                view_line_detail.setVisibility(View.VISIBLE);
                switchFragment(nowFragment, goodsInfoWebFragment);
                nowFragment = goodsInfoWebFragment;
                break;

            case R.id.ll_goods_config:
                //规格参数tab
                nowIndex = 1;
                scrollCursor();
                view_line_config.setVisibility(View.VISIBLE);
                view_line_detail.setVisibility(View.INVISIBLE);
                switchFragment(nowFragment, goodsConfigFragment);
                nowFragment = goodsConfigFragment;
                break;
            case R.id.ll_current_goods:
                mSkuPop.showAtLocation(contentView, Gravity.BOTTOM & Gravity.CENTER_HORIZONTAL, 0, 0);
                contentView.startAnimation(mAnimationStart);
                break;
            default:
                break;
        }
    }

    @Override
    public void onStatucChanged(SlideDetailsLayout.Status status) {
        if (status == SlideDetailsLayout.Status.OPEN) {
            //当前为图文详情页
            fab_up_slide.show();
            activity.vp_content.setNoScroll(true);
            activity.tv_title.setVisibility(View.VISIBLE);
            activity.psts_tabs.setVisibility(View.GONE);
        } else {
            //当前为商品详情页
            fab_up_slide.hide();
            activity.vp_content.setNoScroll(false);
            activity.tv_title.setVisibility(View.GONE);
            activity.psts_tabs.setVisibility(View.VISIBLE);
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
     *
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


    /**
     * 处理推荐商品数据(每两个分为一组)
     *
     * @param data
     * @return
     */
    public static List<List<MainEntity.RecommendEntity>> handleRecommendGoods(List<MainEntity.RecommendEntity> data) {
        List<List<MainEntity.RecommendEntity>> handleData = new ArrayList<>();
        int length = data.size() / 2;
        if (data.size() % 2 != 0) {
            length = data.size() / 2 + 1;
        }
        for (int i = 0; i < length; i++) {
            List<MainEntity.RecommendEntity> recommendGoods = new ArrayList<>();
            for (int j = 0; j < (i * 2 + j == data.size() ? 1 : 2); j++) {
                recommendGoods.add(data.get(i * 2 + j));
            }
            handleData.add(recommendGoods);
        }
        return handleData;
    }

    private class BannerImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            GlideUtils.loadUrlImage(context, path.toString(), imageView);
        }
    }
}
