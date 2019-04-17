package com.pgg.yixiannonapp.global;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.alipay.sdk.app.EnvUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.pgg.yixiannonapp.module.MainActivity;
import com.pgg.yixiannonapp.utils.SharedPrefHelper;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;

import static cn.jpush.im.android.api.JMessageClient.FLAG_NOTIFY_SILENCE;

/**
 * Created by pgg on 2018/5/2.
 * 基类的Application
 */

public class GlobalApplication extends Application {
    private static MainActivity sMainActivity = null;
    private static GlobalApplication mInstance;
    public static final boolean USE_SAMPLE_DATA = false;
    private SharedPrefHelper sharedPrefHelper;
    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initScreenSize();
        sharedPrefHelper = SharedPrefHelper.getInstance();
        Fresco.initialize(this);
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);

        //开启极光调试
        JPushInterface.setDebugMode(true);
        //实例化极光推送
        JPushInterface.init(mInstance);
        //实例化极光IM,并自动同步聊天记录
        JMessageClient.init(getApplicationContext(), true);
        JMessageClient.setDebugMode(true);
        //通知管理,通知栏开启，其他关闭
        JMessageClient.setNotificationFlag(FLAG_NOTIFY_SILENCE);
        initJPush2();
    }

    private void initJPush2() {
        sharedPrefHelper.setMusic(false);
        sharedPrefHelper.setVib(false);
        sharedPrefHelper.setAppKey("b47a37f342eba5f9fbcd1961");
    }

    public static void setMainActivity(MainActivity activity) {
        sMainActivity = activity;
    }

    public static Context getInstance() {
        return mInstance;
    }

    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }
}
