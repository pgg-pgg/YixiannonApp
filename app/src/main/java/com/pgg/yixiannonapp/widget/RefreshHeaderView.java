package com.pgg.yixiannonapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ajguan.library.IRefreshHeader;
import com.ajguan.library.State;
import com.pgg.yixiannonapp.R;

public class RefreshHeaderView extends LinearLayout implements IRefreshHeader {

    private ImageView iv_header_refresh;
    private ProgressBar pb_header_refresh;
    private TextView tv_status;
    private TextView tv_time;


    public RefreshHeaderView(Context context) {
        this(context,null);
    }

    public RefreshHeaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RefreshHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_refresh_header, this, true);
        iv_header_refresh = view.findViewById(R.id.iv_header_refresh);
        pb_header_refresh = view.findViewById(R.id.pb_header_refresh);
        tv_status = view.findViewById(R.id.tv_status);
        tv_time = view.findViewById(R.id.tv_time);
    }

    @Override
    public void reset() {
        Log.e("pgg-----","reset");
    }

    @Override
    public void pull() {
        Log.e("pgg-----","pull");
    }

    @Override
    public void refreshing() {
        Log.e("pgg-----","refreshing");
    }

    @Override
    public void onPositionChange(float v, float v1, float v2, boolean b, State state) {
        Log.e("pgg-----","onPositionChange"+"v"+v+"   "+"v1"+v1+"    "+"v2"+v2);

    }

    @Override
    public void complete() {
        Log.e("pgg-----","complete");

    }
}
