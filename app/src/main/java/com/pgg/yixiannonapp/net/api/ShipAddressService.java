package com.pgg.yixiannonapp.net.api;

import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.domain.order.ShipAddress;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ShipAddressService {

    /**
     * 添加地址
     * @param shipAddress
     * @return
     */
    @POST("ShipAddress/addShipAddress")
    Observable<Results<ShipAddress>> addShipAddress(@Body ShipAddress shipAddress);

    /**
     * 通过用户名获取地址
     * @return
     */
    @GET("ShipAddress/getShipAddress")
    Observable<Results<List<ShipAddress>>> getShipAddress(@Query("userName") String userName);

    /**
     * 修改地址
     * @param shipAddress
     * @return
     */
    @POST("ShipAddress/modifyShipAddress")
    Observable<Results<ShipAddress>> modifyShipAddress(@Body ShipAddress shipAddress);

    /**
     * 删除地址
     * @param id
     * @return
     */
    @GET("ShipAddress/deleteShipAddress")
    Observable<Results<ShipAddress>> deleteShipAddress(@Query("id") int id);

    /**
     * 通过id获取地址
     * @return
     */
    @GET("ShipAddress/getShipAddressById")
    Observable<Results<ShipAddress>> getShipAddressById(@Query("id") int id);
}
