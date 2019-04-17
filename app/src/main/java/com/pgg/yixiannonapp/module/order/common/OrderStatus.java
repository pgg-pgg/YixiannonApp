package com.pgg.yixiannonapp.module.order.common;

public interface OrderStatus {
    int ORDER_ALL = 0;//全部
    int ORDER_WAIT_PAY = 1;//待支付
    int ORDER_WAIT_CONFIRM = 2;//待收货
    int ORDER_COMPLETED = 3;//已完成
    int ORDER_CANCELED = 4;//已取消
}
