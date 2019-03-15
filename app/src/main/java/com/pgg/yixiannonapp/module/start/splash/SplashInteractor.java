package com.pgg.yixiannonapp.module.start.splash;

/**
 * Created by pgg on 2018/5/2.
 */

public interface SplashInteractor {

    interface OnEnterIntoFinishListener{
        void isFirstOpen();

        void isNotFirstOpen();

        void showContentView();
    }

    void enterInto(boolean isFirstOpen, OnEnterIntoFinishListener listener);
}
