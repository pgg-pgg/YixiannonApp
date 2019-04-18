package com.pgg.yixiannonapp.domain.order;

import java.util.List;

public class Order {
    private int id;
    private int payType;
    private String userName;
    private Double totalPrice;
    private int orderStatus;
    private ShipAddress shipAddress;
    private List<OrderGoods> orderGoodsList;
    private List<Integer> cartGoodsIds;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderGoods> getOrderGoodsList() {
        return orderGoodsList;
    }

    public void setOrderGoodsList(List<OrderGoods> orderGoodsList) {
        this.orderGoodsList = orderGoodsList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ShipAddress getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(ShipAddress shipAddress) {
        this.shipAddress = shipAddress;
    }

    public List<Integer> getCartGoodsIds() {
        return cartGoodsIds;
    }

    public void setCartGoodsIds(List<Integer> cartGoodsIds) {
        this.cartGoodsIds = cartGoodsIds;
    }
}
