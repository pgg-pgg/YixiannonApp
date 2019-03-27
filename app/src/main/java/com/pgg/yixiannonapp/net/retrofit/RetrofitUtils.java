package com.pgg.yixiannonapp.net.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 所有的请求数据的方法集中地
 * 根据MovieService的定义编写合适的方法
 * 其中observable是获取API数据
 * observableCahce获取缓存数据
 * new EvictDynamicKey(false) false使用缓存  true 加载数据不使用缓存
 * Created by pgg on 2019/3/26.
 */

public class RetrofitUtils {


    private static Retrofit retrofit;
    private static OkHttpClient client;


    public static Retrofit getRetrofit(String baseUrl) {

        if (client==null){
            client=OkHttp3Utils.getOkHttpClient();
        }
        retrofit=new Retrofit.Builder().baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
