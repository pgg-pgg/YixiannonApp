package com.pgg.yixiannonapp.net.api;

import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.domain.order.Order;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface OrderService {

    /**
     * 添加订单
     * @param order
     * @return
     */
    @POST("Order/addOrder")
    Observable<Results<Order>> addOrder(@Body Order order);

    /**
     * 获取订单列表数据
     * @return
     */
    @GET("Order/getOrderList")
    Observable<Results<List<Order>>> getOrderList(@Query("userName") String user_name);


    @GET("Order/getOrderListByStatus")
    Observable<Results<List<Order>>> getOrderListByStatus(@Query("userName") String userName,@Query("orderStatus") int orderStatus);

    @GET("Order/updateOrderStatus")
    Observable<Results<Order>> updateOrderStatus(@Query("orderStatus") int orderStatus,@Query("id") int id);

}
