package com.pgg.yixiannonapp.domain.CartGoods;


/**
 * 购物车商品类
 */
public class CartGoods {
    private int id;//购物车单项商品ID
    private int goods_id;//具体商品ID
    private String user_name;//用户id
    private String goods_desc;//商品描述
    private String goods_icon;//商品图片
    private Double goods_price;//商品价格
    private int goods_count;//商品数量
    private String goods_sku;//商品SKU
    private boolean isSelected;//是否选中

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    public String getGoods_icon() {
        return goods_icon;
    }

    public void setGoods_icon(String goods_icon) {
        this.goods_icon = goods_icon;
    }

    public Double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(Double goods_price) {
        this.goods_price = goods_price;
    }

    public int getGoods_count() {
        return goods_count;
    }

    public void setGoods_count(int goods_count) {
        this.goods_count = goods_count;
    }

    public String getGoods_sku() {
        return goods_sku;
    }

    public void setGoods_sku(String goods_sku) {
        this.goods_sku = goods_sku;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
