package com.pgg.yixiannonapp.module.classify;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.kennyc.view.MultiStateView;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.classify.ClassifyRecycleLeftAdapter;
import com.pgg.yixiannonapp.adapter.classify.ClassifyRecycleRightAdapter;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.Classify.ClassifyItemEntity;
import com.pgg.yixiannonapp.domain.Classify.ClassifyTypeEntity;
import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.net.httpData.HttpData;
import com.pgg.yixiannonapp.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observer;

public class ClassifyFragment extends BaseFragment {

    @BindView(R.id.rv_classify_left)
    RecyclerView rv_classify_left;
    @BindView(R.id.rv_classify_right)
    RecyclerView rv_classify_right;
    @BindView(R.id.mMultiStateView1)
    MultiStateView mMultiStateView1;
    @BindView(R.id.mMultiStateView2)
    MultiStateView mMultiStateView2;

    @BindView(R.id.layout_title)
    TitleBar layout_title;

    private int currentCheckItem = 0;

    List<ClassifyTypeEntity> classifyTypeEntities;

    @Override
    public void lazyLoad() {
    }

    //初始化左半部recycle
    private void initLeftRecycleView() {
        rv_classify_left.setLayoutManager(new LinearLayoutManager(getContext()));
        final ClassifyRecycleLeftAdapter classifyRecycleLeftAdapter = new ClassifyRecycleLeftAdapter(R.layout.item_recycle_left, classifyTypeEntities);
        rv_classify_left.setAdapter(classifyRecycleLeftAdapter);
        rv_classify_left.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (currentCheckItem != position) {
                    ((ClassifyTypeEntity) adapter.getItem(currentCheckItem)).setIsSelected(false);
                    ((ClassifyTypeEntity) adapter.getItem(position)).setIsSelected(true);
                    adapter.notifyDataSetChanged();
                    currentCheckItem = position;
                    getClassifyItemEntities(position+1);
                }
            }
        });
    }


    private void initRightRecycleView(List<ClassifyItemEntity> data) {
        rv_classify_right.setLayoutManager(new LinearLayoutManager(getContext()));
        ClassifyRecycleRightAdapter classifyRecycleLeftAdapter = new ClassifyRecycleRightAdapter(R.layout.item_classify_right, data);
        rv_classify_right.setAdapter(classifyRecycleLeftAdapter);
        rv_classify_right.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (currentCheckItem!=position){
                    ((ClassifyTypeEntity)adapter.getItem(currentCheckItem)).setIsSelected(false);
                    ((ClassifyTypeEntity)adapter.getItem(position)).setIsSelected(true);
                    adapter.notifyDataSetChanged();
                    currentCheckItem = position;
                }
            }
        });
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_classify;
    }

    @Override
    public void initView() {
        initTopTitle();
        mMultiStateView1.setViewState(MultiStateView.VIEW_STATE_LOADING);
        classifyTypeEntities = new ArrayList<>();
        HttpData.getInstance().getAllClassifyData(new Observer<Results<List<ClassifyTypeEntity>>>() {
            @Override
            public void onCompleted() {
                mMultiStateView1.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            }

            @Override
            public void onError(Throwable e) {
                mMultiStateView1.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }

            @Override
            public void onNext(Results<List<ClassifyTypeEntity>> listResults) {
                if (listResults.getCode() == 0) {
                    //获取所有分类数据
                    classifyTypeEntities = listResults.getData();
                    classifyTypeEntities.get(0).setIsSelected(true);
                    mMultiStateView1.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    initLeftRecycleView();
                }
            }
        });
        getClassifyItemEntities(1);
    }

    private void initTopTitle() {
        layout_title.setTitleName("分类");
        layout_title.setMainMsgVisible(false);
        layout_title.setSearchVisible(false);
        layout_title.setMenuVisible(false);
        layout_title.setRightText("");
    }

    private void getClassifyItemEntities(int classifyTypeId) {
        mMultiStateView2.setViewState(MultiStateView.VIEW_STATE_LOADING);
        HttpData.getInstance().getClassifyItemEntities(new Observer<Results<List<ClassifyItemEntity>>>() {
            @Override
            public void onCompleted() {
                mMultiStateView2.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            }

            @Override
            public void onError(Throwable e) {
                mMultiStateView2.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }

            @Override
            public void onNext(Results<List<ClassifyItemEntity>> listResults) {
                if (listResults.getCode()==0){
                    List<ClassifyItemEntity> data = listResults.getData();
                    mMultiStateView2.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    initRightRecycleView(data);
                }
            }
        }, classifyTypeId);
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }
}
