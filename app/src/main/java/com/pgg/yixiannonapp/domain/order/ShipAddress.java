package com.pgg.yixiannonapp.domain.order;

import java.io.Serializable;

public class ShipAddress implements Serializable{

    private int id;
    private String shipUserName;
    private String shipUserMobile;
    private String shipAddress;
    private int shipIsDefault;
    private String userName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShipUserName() {
        return shipUserName;
    }

    public void setShipUserName(String shipUserName) {
        this.shipUserName = shipUserName;
    }

    public String getShipUserMobile() {
        return shipUserMobile;
    }

    public void setShipUserMobile(String shipUserMobile) {
        this.shipUserMobile = shipUserMobile;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public int getShipIsDefault() {
        return shipIsDefault;
    }

    public void setShipIsDefault(int shipIsDefault) {
        this.shipIsDefault = shipIsDefault;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
