package com.pgg.yixiannonapp.module.classify;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.classify.ClassifyRecycleLeftAdapter;
import com.pgg.yixiannonapp.adapter.classify.ClassifyRecycleRightAdapter;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.ClassifyTypeEntity;
import com.pgg.yixiannonapp.global.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ClassifyFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.rv_classify_left)
    RecyclerView rv_classify_left;
    @BindView(R.id.rv_classify_right)
    RecyclerView rv_classify_right;

    private int currentCheckItem=0;

    List<ClassifyTypeEntity> classifyTypeEntities;

    @Override
    public void onClick(View view) {

    }

    @Override
    public void lazyLoad() {
        initLeftRecycleView();
        initRightRecycleView();

    }

    //初始化左半部recycle
    private void initLeftRecycleView() {

        rv_classify_left.setLayoutManager(new LinearLayoutManager(getContext()));
        final ClassifyRecycleLeftAdapter classifyRecycleLeftAdapter = new ClassifyRecycleLeftAdapter(R.layout.item_recycle_left, classifyTypeEntities);
        rv_classify_left.setAdapter(classifyRecycleLeftAdapter);
        rv_classify_left.addOnItemTouchListener(new OnItemChildClickListener() {
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


    private void initRightRecycleView() {
        rv_classify_right.setLayoutManager(new LinearLayoutManager(getContext()));
        ClassifyRecycleRightAdapter classifyRecycleLeftAdapter = new ClassifyRecycleRightAdapter(R.layout.item_classify_right, classifyTypeEntities.get(0).getClassifyDescEntities());
        rv_classify_right.setAdapter(classifyRecycleLeftAdapter);
//        rv_classify_right.addOnItemTouchListener(new OnItemChildClickListener() {
//            @Override
//            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                if (currentCheckItem!=position){
//                    ((ClassifyTypeEntity)adapter.getItem(currentCheckItem)).setIsSelected(false);
//                    ((ClassifyTypeEntity)adapter.getItem(position)).setIsSelected(true);
//                    adapter.notifyDataSetChanged();
//                    currentCheckItem = position;
//                }
//            }
//        });
    }



    @Override
    public int getLayoutRes() {
        return R.layout.fragment_classify;
    }

    @Override
    public void initView() {
        classifyTypeEntities = new ArrayList<>();
        List<ClassifyTypeEntity.ClassifyDescEntity> classifyDescEntities=new ArrayList<>();
        List<ClassifyTypeEntity.GoodsTypeEntity> goodsTypeEntities=new ArrayList<>();

        for (int i = 0;i<9;i++){
            ClassifyTypeEntity.GoodsTypeEntity goodsTypeEntity = new ClassifyTypeEntity.GoodsTypeEntity();
            goodsTypeEntity.setId(i);
            goodsTypeEntity.setGoodsImageUrl(Constant.HOME_BANNER_ONE);
            goodsTypeEntity.setGoodsName("商品"+i);
            goodsTypeEntities.add(goodsTypeEntity);
        }

        for (int i=0;i<4;i++){
            ClassifyTypeEntity.ClassifyDescEntity descEntity = new ClassifyTypeEntity.ClassifyDescEntity();
            descEntity.setId(i);
            descEntity.setTypeName("推荐分类"+i);
            descEntity.setGoodsTypeEntities(goodsTypeEntities);
            classifyDescEntities.add(descEntity);
        }
        for(int i=0;i<20;i++){
            ClassifyTypeEntity entity = new ClassifyTypeEntity();
            if (i==0){
                entity.setIsSelected(true);
            }
            entity.setId(i);
            entity.setTypeName("新鲜水果"+i);
            entity.setClassifyDescEntities(classifyDescEntities);
            classifyTypeEntities.add(entity);
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
