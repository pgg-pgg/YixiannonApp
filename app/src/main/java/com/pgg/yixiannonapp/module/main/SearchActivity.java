package com.pgg.yixiannonapp.module.main;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.main.RecommendListAdapter;
import com.pgg.yixiannonapp.base.BaseCustomActivity;
import com.pgg.yixiannonapp.domain.MainEntity;
import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.net.httpData.HttpData;
import com.pgg.yixiannonapp.widget.GridViewChannelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observer;

public class SearchActivity extends BaseCustomActivity {

    @BindView(R.id.mSearchEt)
    EditText mSearchEt;
    @BindView(R.id.mMultiStateView)
    MultiStateView mMultiStateView;
    @BindView(R.id.lv_recommend_view)
    GridViewChannelView lv_recommend_view;
    @BindView(R.id.tv_no_search)
    TextView tv_no_search;

    RecommendListAdapter mAdapter;
    List<MainEntity.RecommendEntity> data = new ArrayList<>();

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_search);
    }


    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                String goodsName = mSearchEt.getText().toString();
                if (TextUtils.isEmpty(goodsName)) {
                    showToast("请输入要查询的关键字");
                } else {
                    tv_no_search.setVisibility(View.GONE);
                    mMultiStateView.setVisibility(View.VISIBLE);
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                    HttpData.getInstance().searchGoodsByName(new Observer<Results<List<MainEntity.RecommendEntity>>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                        }

                        @Override
                        public void onNext(Results<List<MainEntity.RecommendEntity>> listResults) {
                            if (listResults.getCode() == 0) {
                                if (listResults.getData() != null && listResults.getData().size() > 0) {
                                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                                    data.clear();
                                    data.addAll(listResults.getData());
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                                }
                            } else {
                                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                            }
                        }
                    }, goodsName);
                }
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mAdapter = new RecommendListAdapter(getContext(), data);
        lv_recommend_view.setAdapter(mAdapter);
        tv_no_search.setVisibility(View.VISIBLE);
        mMultiStateView.setVisibility(View.GONE);
    }

    @Override
    public void initPresenter() {

    }
}
