package com.pgg.yixiannonapp.net.api;

import com.pgg.yixiannonapp.domain.Classify.ClassifyItemEntity;
import com.pgg.yixiannonapp.domain.Classify.ClassifyTypeEntity;
import com.pgg.yixiannonapp.domain.Results;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ClassifyService {

    /**
     * 获取分类左边数据接口
     * @return
     */
    @GET("Classify/getAllClassifyData")
    Observable<Results<List<ClassifyTypeEntity>>> getAllClassifyData();


    /**
     * 获取分类右边数据接口
     * @return
     */
    @GET("Classify/getClassifyItemEntities")
    Observable<Results<List<ClassifyItemEntity>>> getClassifyItemEntities(@Query("classifyDescId")int classifyDescId);


}
