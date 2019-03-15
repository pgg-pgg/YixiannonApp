package com.pgg.yixiannonapp.module.start.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.utils.SPUtils;
import com.pgg.yixiannonapp.utils.StateBarTranslucentUtils;


/**
 * Created by pgg on 2019/3/15.
 */

public class WelcomeGuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //设置状态栏透明
        StateBarTranslucentUtils.setStateBarTranslucent(this);
        if(savedInstanceState==null){
            //更换Fragment
           replaceTutorialFragment();
        }
    }


    private void replaceTutorialFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_welcome,new CustomPresentationPagerFragment())
                .commit();
    }

    public static void start(Context context) {
        SPUtils.put(context, Constant.FIRST_OPEN,true);
        context.startActivity(new Intent(context,WelcomeGuideActivity.class));
    }
}
