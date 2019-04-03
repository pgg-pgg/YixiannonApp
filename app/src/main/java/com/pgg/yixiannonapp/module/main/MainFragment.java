package com.pgg.yixiannonapp.module.main;

import com.ajguan.library.EasyRefreshLayout;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.main.MainMultiItemAdapter;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.MainEntity;
import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.net.httpData.HttpData;
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
import rx.Observer;

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

    ArrayList<MainEntity> mainEntities = new ArrayList<>();
    MainEntity mainEntity1 = new MainEntity(MainEntity.BANNER_TYPE);
    MainEntity mainEntity2 = new MainEntity(MainEntity.CHANNEL_TYPE);
    MainEntity mainEntity3 = new MainEntity(MainEntity.COMMENT_TYPE);
    MainEntity mainEntity4 = new MainEntity(MainEntity.TOP_TYPE);
    MainEntity mainEntity5 = new MainEntity(MainEntity.RECOMMEND_TYPE);

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
        initData();
    }

    private void initData() {
        HttpData.getInstance().getHomeData(new Observer<Results<MainEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("pggg======",e.getMessage());
            }

            @Override
            public void onNext(Results<MainEntity> mainEntityResults) {
                if (mainEntityResults.getCode()==0){
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
                    rv_main_view.setAdapter(new MainMultiItemAdapter(mainEntities));
                }
            }
        });
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
