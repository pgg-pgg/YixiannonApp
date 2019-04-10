package com.pgg.yixiannonapp.net.api;

import com.pgg.yixiannonapp.domain.MainEntity;
import com.pgg.yixiannonapp.domain.Results;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GoodsDetailService {

    /**
     * 获取分类左边数据接口
     * @return
     */
    @GET("GoodsDetail/getGoodsDetail")
    Observable<Results<MainEntity.RecommendEntity>> getGoodsDetail(@Query("goodsName")String goodsName);

}
