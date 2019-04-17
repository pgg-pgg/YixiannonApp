package com.pgg.yixiannonapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pgg.yixiannonapp.R;

public class LabelTextView extends FrameLayout {

    String mLabelText = "";
    String mContentText = "";
    private Context context;
    TextView mLabelTv,mContentTv;

    public LabelTextView(@NonNull Context context) {
        this(context, null);
    }

    public LabelTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.LabelText);
        this.context = context;
        mLabelText = typeArray.getText(R.styleable.LabelText_labelText).toString();
        mContentText = typeArray.getText(R.styleable.LabelText_contentText).toString();
        initView();
        typeArray.recycle();
    }

    private void initView() {
        View inflate = View.inflate(context, R.layout.layout_label_textview, this);
        mLabelTv = inflate.findViewById(R.id.mLabelTv);
        mContentTv = inflate.findViewById(R.id.mContentTv);
        mLabelTv.setText(mLabelText);
        mContentTv.setText(mContentText);
    }

    /**
     * 设置内容文本
     */
    public void setContentText(String text) {
        mContentTv.setText(text);
    }
}
