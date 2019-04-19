package com.pgg.yixiannonapp.net.api;

import com.pgg.yixiannonapp.domain.MainEntity;
import com.pgg.yixiannonapp.domain.Results;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface MainService {

    /**
     * 获取首页数据接口
     * @return
     */
    @GET("Home/getHomeData")
    Observable<Results<MainEntity>> getHomeData(@Query("curPage")int curPage, @Query("pageNum")int pageNum);


    /**
     * 获取分页推荐数据接口
     * @return
     */
    @GET("Home/getRecommendData")
    Observable<Results<List<MainEntity.RecommendEntity>>> getRecommendData(@Query("curPage")int curPage, @Query("pageNum")int pageNum);


    @GET("Home/searchGoodsByName")
    Observable<Results<List<MainEntity.RecommendEntity>>> searchGoodsByName(@Query("goodsName")String goodsName);
}
