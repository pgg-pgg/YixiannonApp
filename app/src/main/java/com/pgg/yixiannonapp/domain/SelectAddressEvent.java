package com.pgg.yixiannonapp.domain;

import com.pgg.yixiannonapp.domain.order.ShipAddress;

public class SelectAddressEvent {
    private ShipAddress address;

    public SelectAddressEvent(ShipAddress shipAddress){
        this.address = shipAddress;
    }

    public ShipAddress getAddress() {
        return address;
    }

    public void setAddress(ShipAddress address) {
        this.address = address;
    }
}
