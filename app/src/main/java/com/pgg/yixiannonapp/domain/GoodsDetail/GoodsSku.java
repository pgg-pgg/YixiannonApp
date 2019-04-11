package com.pgg.yixiannonapp.domain.GoodsDetail;

import java.util.List;

public class GoodsSku {

    //sku id
    private int id;
    //sku标题
    private String goods_sku_title;
    //sku内容
    private List<String> skuContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoods_sku_title() {
        return goods_sku_title;
    }

    public void setGoods_sku_title(String goods_sku_title) {
        this.goods_sku_title = goods_sku_title;
    }

    public List<String> getSkuContent() {
        return skuContent;
    }

    public void setSkuContent(List<String> skuContent) {
        this.skuContent = skuContent;
    }
}
