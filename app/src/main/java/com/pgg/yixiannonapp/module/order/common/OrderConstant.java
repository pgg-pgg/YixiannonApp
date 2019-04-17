package com.pgg.yixiannonapp.module.order.common;

public interface OrderConstant {
    //订单状态
    String KEY_ORDER_STATUS = "order_status";
    //收货地址
    String KEY_SHIP_ADDRESS = "ship_address";
    //选择收货地址请求码
    int REQ_SELECT_ADDRESS = 1001;
    //是否选择收货地址
    String KEY_IS_SELECT_ADDRESS = "is_select_address";
    //是否编辑地址
    String KEY_ADDRESS_IS_EDIT = "address_is_edit";
    //支付订单操作
    int OPT_ORDER_PAY = 1;
    //确认订单操作
    int OPT_ORDER_CONFIRM = 2;
    //取消订单操作
    int OPT_ORDER_CANCEL = 3;
}
