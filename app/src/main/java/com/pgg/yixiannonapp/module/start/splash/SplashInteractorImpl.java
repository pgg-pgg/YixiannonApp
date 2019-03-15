package com.pgg.yixiannonapp.module.start.splash;

import android.os.Handler;

/**
 * Created by pgg on 2018/5/2.
 */

public class SplashInteractorImpl implements SplashInteractor {

    @Override
    public void enterInto(boolean isFirstOpen, final OnEnterIntoFinishListener listener) {
        if (!isFirstOpen){
            listener.isFirstOpen();
        }else {
            listener.showContentView();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listener.isNotFirstOpen();
                }
            },2000);
        }
    }
}
