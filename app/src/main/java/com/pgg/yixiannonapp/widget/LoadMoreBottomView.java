package com.pgg.yixiannonapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ajguan.library.ILoadMoreView;
import com.pgg.yixiannonapp.R;

public class LoadMoreBottomView extends LinearLayout implements ILoadMoreView {

    private DrawHookView dhv_view;
    private TextView tv_loading;
    public LoadMoreBottomView(Context context) {
        this(context,null);
    }

    public LoadMoreBottomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadMoreBottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_load_bottom, this, true);
        dhv_view = view.findViewById(R.id.pb_bottom_load);
        tv_loading = view.findViewById(R.id.tv_loading);
    }

    @Override
    public void reset() {

    }

    @Override
    public void loading() {
        tv_loading.setText(getResources().getString(R.string.load_ing));
    }

    @Override
    public void loadComplete() {
        tv_loading.setText(getResources().getString(R.string.load_complete));
    }

    @Override
    public void loadFail() {
        tv_loading.setText(getResources().getString(R.string.empty_network_error));
    }

    @Override
    public void loadNothing() {
        tv_loading.setText(getResources().getString(R.string.empty_no_data));
    }

    @Override
    public View getCanClickFailView() {
        return this;
    }
}
