package com.pgg.yixiannonapp.net.api;

import com.pgg.yixiannonapp.domain.MainEntity;
import com.pgg.yixiannonapp.domain.Results;

import retrofit2.http.GET;
import rx.Observable;

public interface MainService {

    /**
     * 获取首页数据接口
     * @return
     */
    @GET("Home/getHomeData")
    Observable<Results<MainEntity>> getHomeData();

}
