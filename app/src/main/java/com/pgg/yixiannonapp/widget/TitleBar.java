package com.pgg.yixiannonapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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
    private TextView tv_title;
    private Context context;
    private TextView tv_title_right;
    private LeftClickListener leftListener;
    private RightClickListener rightClickListener;

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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        iv_main_menu = findViewById(R.id.iv_main_menu);
        tv_title = findViewById(R.id.tv_title);
        tv_title_right = findViewById(R.id.tv_title_right);
        ll_main_search = findViewById(R.id.ll_main_search);
        rl_main_msg = findViewById(R.id.rl_main_msg);
        iv_main_menu.setOnClickListener(this);
        ll_main_search.setOnClickListener(this);
        rl_main_msg.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);
    }

    public void setTitleName(String title){
        tv_title.setVisibility(VISIBLE);
        tv_title.setText(title);
    }

    public void setLeftImage(int drawable){
        iv_main_menu.setVisibility(VISIBLE);
        iv_main_menu.setBackgroundResource(drawable);
    }

    public void setRightText(String text){
        setMainMsgVisible(false);
        tv_title_right.setText(text);
    }
    public void setSearchVisible(boolean visible){
        ll_main_search.setVisibility(visible?VISIBLE:GONE);
    }

    public void setMainMsgVisible(boolean visible){
        rl_main_msg.setVisibility(visible?VISIBLE:GONE);
        tv_title_right.setVisibility(!visible?VISIBLE:GONE);
    }
    public void setMenuVisible(boolean visible){
        iv_main_menu.setVisibility(visible?VISIBLE:INVISIBLE);
    }

    public String getRightText(){
        return tv_title_right.getText().toString();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_main_menu:
//                Toast.makeText(context,"菜单",Toast.LENGTH_LONG).show();
                if (leftListener!=null){
                    leftListener.setLeftOnClickListener();
                }
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

            case R.id.tv_title_right:
                //右侧管理
                if (rightClickListener!=null){
                    rightClickListener.setRightOnClickListener();
                }
                break;
        }
    }

    public interface LeftClickListener{
        void setLeftOnClickListener();
    }

    public interface RightClickListener{
        void setRightOnClickListener();
    }

    public void setLeftClickListener(LeftClickListener listener) {
        this.leftListener = listener;
    }

    public void setRightClickListener(RightClickListener listener){
        this.rightClickListener = listener;
    }
}
