package com.pgg.yixiannonapp.domain.Classify;

public class GoodsTypeEntity {

    private int id;
    private int classifyDescId;
    private String goodsImageUrl;
    private String goodsName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassifyDescId() {
        return classifyDescId;
    }

    public void setClassifyDescId(int classifyDescId) {
        this.classifyDescId = classifyDescId;
    }

    public String getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        this.goodsImageUrl = goodsImageUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
