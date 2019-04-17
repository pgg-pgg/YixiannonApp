package com.pgg.yixiannonapp.domain.CartGoods;

public class CartAllCheckedEvent {
    private boolean isAllChecked;
    public CartAllCheckedEvent(boolean isAllChecked){
        this.isAllChecked = isAllChecked;
    }

    public boolean isAllChecked() {
        return isAllChecked;
    }

    public void setAllChecked(boolean allChecked) {
        isAllChecked = allChecked;
    }
}
