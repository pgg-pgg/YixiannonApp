package com.pgg.yixiannonapp.module.main;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.main.MainMultiItemAdapter;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.MainEntity;
import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.net.httpData.HttpData;
import com.pgg.yixiannonapp.widget.SearchTextFlipperView;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observer;

public class MainFragment extends BaseFragment{

    @BindView(R.id.ll_main_search)
    SearchTextFlipperView ll_main_search;
    @BindView(R.id.rv_main_view)
    RecyclerView rv_main_view;
    @BindView(R.id.mMultiStateView)
    MultiStateView mMultiStateView;
    @BindView(R.id.iv_main_menu)
    ImageView iv_main_menu;
    private int curPage = 0;
    private int pageNum = 5;

    ArrayList<MainEntity> mainEntities = new ArrayList<>();
    MainEntity mainEntity1 = new MainEntity(MainEntity.BANNER_TYPE);
    MainEntity mainEntity2 = new MainEntity(MainEntity.CHANNEL_TYPE);
    MainEntity mainEntity3 = new MainEntity(MainEntity.COMMENT_TYPE);
    MainEntity mainEntity4 = new MainEntity(MainEntity.TOP_TYPE);
    MainEntity mainEntity5 = new MainEntity(MainEntity.RECOMMEND_TYPE);

    MainMultiItemAdapter mainMultiItemAdapter;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView() {
        //初始化顶部搜索栏
        initTopTitle();
        initData();
    }

    private void initData() {
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        HttpData.getInstance().getHomeData(new Observer<Results<MainEntity>>() {
            @Override
            public void onCompleted() {
                initRefreshLayout();
            }

            @Override
            public void onError(Throwable e) {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }

            @Override
            public void onNext(Results<MainEntity> mainEntityResults) {
                if (mainEntityResults.getCode()==0){
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    mainEntity1.setBannerEntities(mainEntityResults.getData().getBannerEntities());
                    mainEntity2.setChannelEntities(mainEntityResults.getData().getChannelEntities());
                    mainEntity3.setCommentEntities(mainEntityResults.getData().getCommentEntities());
                    mainEntity4.setTopNewsEntities(mainEntityResults.getData().getTopNewsEntities());
                    mainEntity5.setRecommendEntities(mainEntityResults.getData().getRecommendEntities());
                    mainEntities.add(mainEntity1);
                    mainEntities.add(mainEntity2);
                    mainEntities.add(mainEntity3);
                    mainEntities.add(mainEntity4);
                    mainEntities.add(mainEntity5);
                    rv_main_view.setLayoutManager(new LinearLayoutManager(getContext()));
                    mainMultiItemAdapter = new MainMultiItemAdapter(mainEntities);
                    mainMultiItemAdapter.openLoadAnimation();
                    rv_main_view.setAdapter(mainMultiItemAdapter);
                    curPage+=pageNum;
                }else {
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                }
            }
        },curPage,pageNum);
    }

    private void initRefreshLayout() {
        mainMultiItemAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                HttpData.getInstance().getRecommendData(new Observer<Results<List<MainEntity.RecommendEntity>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mainMultiItemAdapter.loadMoreFail();
                    }

                    @Override
                    public void onNext(Results<List<MainEntity.RecommendEntity>> recommendEntityResults) {
                        List<MainEntity.RecommendEntity> data = recommendEntityResults.getData();
                        if (recommendEntityResults.getCode()==0){
                            if (data.size()<0){
                                //数据获取失败
                                mainMultiItemAdapter.loadMoreFail();
                                return;
                            }
                            if (data.size()<pageNum){
                                //数据全部加载完毕
                                mainMultiItemAdapter.loadMoreEnd();
                            }else {
                                List<MainEntity.RecommendEntity> recommendEntities = mainEntity5.getRecommendEntities();
                                recommendEntities.addAll(data);
                                mainEntity5.setRecommendEntities(recommendEntities);
                                mainMultiItemAdapter.notifyItemChanged(4);
                                mainMultiItemAdapter.loadMoreComplete();
                                curPage+=pageNum;
                            }
                        }

                    }
                },curPage,pageNum);
            }
        });
    }

    /**
     * 懒加载
     */
    @Override
    public void lazyLoad() {
    }

    private void initTopTitle() {
        ArrayList<String> data = new ArrayList<>();
        data.add(getString(R.string.top_search1));
        data.add(getString(R.string.top_search2));
        ll_main_search.setLeftImage(R.drawable.tv_search_drawable_selector);
        ll_main_search.setContainerBackground(R.drawable.corner_news_bg);
        ll_main_search.setLineVertical(false);
        ll_main_search.setData(data);
        ll_main_search.findViewById(R.id.mFlipperView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到搜索界面
                startActivity(new Intent(getContext(),SearchActivity.class));
            }
        });
        iv_main_menu.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }


}
