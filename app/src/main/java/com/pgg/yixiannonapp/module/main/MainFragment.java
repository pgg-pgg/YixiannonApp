package com.pgg.yixiannonapp.module.main;

import com.ajguan.library.EasyRefreshLayout;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.main.MainMultiItemAdapter;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.MainEntity;
import com.pgg.yixiannonapp.widget.LoadMoreBottomView;
import com.pgg.yixiannonapp.widget.RefreshHeaderView;
import com.pgg.yixiannonapp.widget.SearchTextFlipperView;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

import static butterknife.internal.Utils.listOf;
import static com.pgg.yixiannonapp.global.Constant.HOME_BANNER_FOUR;
import static com.pgg.yixiannonapp.global.Constant.HOME_BANNER_ONE;
import static com.pgg.yixiannonapp.global.Constant.HOME_BANNER_TWO;

public class MainFragment extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.ll_main_search)
    SearchTextFlipperView ll_main_search;
    @BindView(R.id.rv_main_view)
    RecyclerView rv_main_view;
    @BindView(R.id.erl_main_view)
    EasyRefreshLayout erl_main_view;

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
        //初始化EasyRefresh
        initEasyRefreshLayout();
    }

    private void initEasyRefreshLayout() {
        erl_main_view.setRefreshHeadView(new RefreshHeaderView(getContext()));
        erl_main_view.setLoadMoreView(new LoadMoreBottomView(getContext()));
        erl_main_view.setEnableLoadMore(false);
        erl_main_view.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            //上拉加载更多
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        erl_main_view.loadMoreComplete(new EasyRefreshLayout.Event() {
                            @Override
                            public void complete() {
                            }
                        }, 500);

                    }
                }, 2000);

            }

            //下拉刷新
            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        erl_main_view.refreshComplete();
                        Toast.makeText(getContext(), "refresh success", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });
    }

    /**
     * 懒加载
     */
    @Override
    public void lazyLoad() {
        //初始化RecycleView
        initRecycleView();
    }

    private void initRecycleView() {
        rv_main_view.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<MainEntity> mainEntities = new ArrayList<>();
        MainEntity mainEntity1 = new MainEntity(MainEntity.BANNER_TYPE);
        MainEntity mainEntity2 = new MainEntity(MainEntity.CHANNEL_TYPE);
        MainEntity mainEntity3 = new MainEntity(MainEntity.COMMENT_TYPE);
        MainEntity mainEntity4 = new MainEntity(MainEntity.TOP_TYPE);
        MainEntity mainEntity5 = new MainEntity(MainEntity.RECOMMEND_TYPE);
        List<MainEntity.BannerEntity> bannerEntities = new ArrayList<>();
        List<MainEntity.ChannelEntity> channelEntities = new ArrayList<>();
        List<MainEntity.CommentEntity> commentEntities = new ArrayList<>();
        List<MainEntity.TopNewsEntity> topNewsEntities = new ArrayList<>();
        List<MainEntity.RecommendEntity> recommendEntities = new ArrayList<>();
        for (int i=0;i<5;i++){
            MainEntity.BannerEntity bannerEntity = new MainEntity.BannerEntity();
            bannerEntity.setId(i);
            bannerEntities.add(bannerEntity);
        }
        mainEntity1.setBannerEntities(bannerEntities);
        for (int i = 0;i<20;i++){
            MainEntity.ChannelEntity channelEntity =new MainEntity.ChannelEntity();
            channelEntity.setId(i);
            channelEntity.setChannelName("哈哈哈"+i);
            channelEntity.setChannelUrl(HOME_BANNER_ONE);
            channelEntities.add(channelEntity);
        }
        mainEntity2.setChannelEntities(channelEntities);
        String text[] = new String[]{
                "夏日炎炎，来个西瓜清凉一下",
                "爆款新品狂降价",
                "新用户立领1000元优惠券"
        };
        for (int i = 0;i<3;i++){
            MainEntity.CommentEntity commentEntity = new MainEntity.CommentEntity();
            commentEntity.setId(i);
            commentEntity.setShowText(text[i]);
            commentEntities.add(commentEntity);
        }
        mainEntity3.setCommentEntities(commentEntities);

        for (int i=0;i<8;i++){
            MainEntity.TopNewsEntity topNewsEntity = new MainEntity.TopNewsEntity();
            topNewsEntity.setId(i);
            topNewsEntity.setLeftTopImageUrl(HOME_BANNER_ONE);
            topNewsEntity.setTopDesc("限时限量抢半价"+i);
            topNewsEntity.setTopName("农头条"+i);
            topNewsEntity.setGoodsUrls(listOf(HOME_BANNER_TWO,HOME_BANNER_FOUR));
            topNewsEntities.add(topNewsEntity);
        }
        mainEntity4.setTopNewsEntities(topNewsEntities);


        for (int i=0;i<20;i++){
            MainEntity.RecommendEntity recommendEntity = new MainEntity.RecommendEntity();
            recommendEntity.setId(i);
            recommendEntity.setAddress("地址地址地址"+i);
            recommendEntity.setGoodsDesc("单果重：15～20克以上 20～25克以上 30~35克以上"+i);
            recommendEntity.setGoodsImageUrl(HOME_BANNER_FOUR);
            recommendEntity.setGoodsLabel(listOf(1,1,1));
            recommendEntity.setGoodsName("某某水果"+i);
            recommendEntity.setGoodsPrice((1+i));
            recommendEntity.setManName("某某人"+i);
            recommendEntity.setReleaseTime(i+"分钟");
            recommendEntities.add(recommendEntity);
        }
        mainEntity5.setRecommendEntities(recommendEntities);
        mainEntities.add(mainEntity1);
        mainEntities.add(mainEntity2);
        mainEntities.add(mainEntity3);
        mainEntities.add(mainEntity4);
        mainEntities.add(mainEntity5);
        rv_main_view.setAdapter(new MainMultiItemAdapter(mainEntities));
    }

    private void initTopTitle() {
        ArrayList<String> data = new ArrayList<>();
        data.add(getString(R.string.top_search1));
        data.add(getString(R.string.top_search2));
        ll_main_search.setLeftImage(R.drawable.tv_search_drawable_selector);
        ll_main_search.setContainerBackground(R.drawable.corner_news_bg);
        ll_main_search.setLineVertical(false);
        ll_main_search.setData(data);
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }


}
