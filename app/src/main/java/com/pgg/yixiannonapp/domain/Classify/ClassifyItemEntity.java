package com.pgg.yixiannonapp.domain.Classify;

import java.util.List;

public class ClassifyItemEntity {

    private int position;
    private ClassifyDescEntity classifyDescEntity;
    private List<GoodsTypeEntity> goodsTypeEntities;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ClassifyDescEntity getClassifyDescEntity() {
        return classifyDescEntity;
    }

    public void setClassifyDescEntity(ClassifyDescEntity classifyDescEntity) {
        this.classifyDescEntity = classifyDescEntity;
    }

    public List<GoodsTypeEntity> getGoodsTypeEntities() {
        return goodsTypeEntities;
    }

    public void setGoodsTypeEntities(List<GoodsTypeEntity> goodsTypeEntities) {
        this.goodsTypeEntities = goodsTypeEntities;
    }
}
