package com.pgg.yixiannonapp.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.utils.PixelUtil;

import java.util.ArrayList;

/**
 * 搜索框的滚动条
 */
public class SearchTextFlipperView extends FrameLayout {

    private ViewFlipper mFlipperView;

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
        addView(rootView);
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
    public void setData(ArrayList<String> data){
        for (String text: data) {
            mFlipperView.addView(buildTextView(text));
        }
        mFlipperView.startFlipping();
    }

}
