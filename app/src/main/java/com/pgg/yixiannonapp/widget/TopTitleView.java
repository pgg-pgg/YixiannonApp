package com.pgg.yixiannonapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pgg.yixiannonapp.R;

public class TopTitleView extends LinearLayout {

    private TextView tv_top_title,tv_go_look;
    private TopLookListener listener;
    public static final int TOP_TYPE = 0;
    public static final int RECOMMEND_TYPE = 1;
    public TopTitleView(Context context) {
        this(context,null);
    }

    public TopTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TopTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_top_view,this);
        tv_go_look = view.findViewById(R.id.tv_go_look);
        tv_top_title = view.findViewById(R.id.tv_top_title);

    }

    public void setTopTitleName(final int type){
        switch (type){
            case TOP_TYPE:
                //农头条
                tv_top_title.setText(getResources().getString(R.string.top_news_title));
                break;
            case RECOMMEND_TYPE:
                //猜你喜欢
                tv_top_title.setText(getResources().getString(R.string.favr_title));
                break;
        }
        tv_go_look.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.setTopLookOnClickLister(type);
                }
            }
        });
    }

    public interface TopLookListener{
        void setTopLookOnClickLister(int type);
    }

    public void setTopLookListener(TopLookListener listener) {
        this.listener = listener;
    }
}
