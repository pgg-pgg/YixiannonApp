package com.pgg.yixiannonapp.module.start.splash;

/**
 * Created by pgg on 2018/5/2.
 * SplashActivity的View
 */

public interface SplashView {

    //setContentView（）方法
    void initContentView();

    //跳转到欢迎引导活动
    void startWelcomeGuideActivity();

    //跳转到主活动
    void startHomeActivity();
}
