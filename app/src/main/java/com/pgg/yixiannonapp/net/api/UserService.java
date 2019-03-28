package com.pgg.yixiannonapp.net.api;

import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.domain.User;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface UserService {

    /**
     * 注册接口
     * @param user
     * @return
     */
    @POST("User/register")
    Observable<Results<User>> register(@Body User user);

    /**
     * 登录接口
     * @param id
     * @param password
     * @return
     */
    @GET("User/login")
    Observable<Results<User>> login(@Query("user_name")String id, @Query("user_pwd") String password);

}
