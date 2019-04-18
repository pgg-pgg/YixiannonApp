package com.pgg.yixiannonapp.net.api;

import com.pgg.yixiannonapp.domain.CartGoods.CartGoods;
import com.pgg.yixiannonapp.domain.Results;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface CartGoodsService {

    /**
     * 添加购物车
     * @param cartGoods
     * @return
     */
    @POST("CartGoods/insertCartGoods")
    Observable<Results<CartGoods>> addCartGoods(@Body CartGoods cartGoods);

    /**
     * 获取购车车列表数据
     * @return
     */
    @GET("CartGoods/getCartGoodsList")
    Observable<Results<List<CartGoods>>> getCartGoodsList(@Query("user_name") String user_name);

    /**
     * 删除购物车列表
     * @param cartGoodsIds
     * @return
     */
    @POST("CartGoods/deleteCartGoodsList")
    Observable<Results<CartGoods>> deleteCartGoodsList(@Body List<Integer> cartGoodsIds);

}
