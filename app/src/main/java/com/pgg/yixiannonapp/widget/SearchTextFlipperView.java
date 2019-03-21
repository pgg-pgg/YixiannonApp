package com.pgg.yixiannonapp.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.pgg.yixiannonapp.R;
import java.util.List;

/**
 * 搜索框的滚动条
 */
public class SearchTextFlipperView extends FrameLayout {

    private ViewFlipper mFlipperView;
    private ImageView iv_left_view;
    private View v_line_vertical;
    private LinearLayout ll_flipper_view;

    public SearchTextFlipperView(@NonNull Context context) {
        this(context,null);
    }

    public SearchTextFlipperView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SearchTextFlipperView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View rootView = View.inflate(getContext(),R.layout.layout_search_text_flipper,null);
        mFlipperView = rootView.findViewById(R.id.mFlipperView);
        mFlipperView.setInAnimation(getContext(), R.anim.search_bottom_in);
        mFlipperView.setOutAnimation(getContext(), R.anim.search_bottom_out);
        iv_left_view = rootView.findViewById(R.id.iv_left_view);
        v_line_vertical = rootView.findViewById(R.id.v_line_vertical);
        ll_flipper_view = rootView.findViewById(R.id.ll_flipper_view);
        addView(rootView);
    }

    public void setContainerBackground(int res){
        ll_flipper_view.setBackgroundResource(res);
    }

    public void setLineVertical(boolean isVisible){
        v_line_vertical.setVisibility(isVisible?VISIBLE:GONE);
    }

    public void setLeftImage(int res){
        iv_left_view.setImageResource(res);
    }

    /**
     * 构建TextView
     * @param text
     * @return
     */
    private TextView buildTextView(String text){
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(12);
        textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        return textView;
    }

    /**
     * 设置公告数据
     * @param data
     */
    public void setData(List<String> data){
        for (String text: data) {
            mFlipperView.addView(buildTextView(text));
        }
        mFlipperView.startFlipping();
    }

}
