package com.pgg.yixiannonapp.widget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pgg.yixiannonapp.R;

/**
 * Created by pgg on 18-6-11.
 * 自定义标题栏
 */

public class TitleBar extends LinearLayout implements View.OnClickListener {

    private View ll_main_search;
    private View rl_main_msg;
    private View iv_main_menu;
    private Context context;

    public TitleBar(Context context) {
        this(context,null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    /**
     * 当布局文件加载完成回调此方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //得到孩子实例
        iv_main_menu=getChildAt(0);
        ll_main_search=getChildAt(1);
        rl_main_msg=getChildAt(2);
        iv_main_menu.setOnClickListener(this);
        ll_main_search.setOnClickListener(this);
        rl_main_msg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_main_menu:
                Toast.makeText(context,"菜单",Toast.LENGTH_LONG).show();
                break;
            case R.id.ll_main_search:
                //搜索
                Toast.makeText(context,"搜索",Toast.LENGTH_LONG).show();
//                Intent intent=new Intent(context,SearchActivity.class);
//                context.startActivity(intent);
                break;
            case R.id.rl_main_msg:
                Toast.makeText(context,"消息",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
