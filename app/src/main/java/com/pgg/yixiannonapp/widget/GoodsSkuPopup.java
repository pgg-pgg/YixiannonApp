package com.pgg.yixiannonapp.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.GoodsDetail.AddCartEvent;
import com.pgg.yixiannonapp.domain.GoodsDetail.GoodsSku;
import com.pgg.yixiannonapp.domain.GoodsDetail.SkuChangeEvent;
import com.pgg.yixiannonapp.utils.GlideUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import ren.qinc.numberbutton.NumberButton;

public class GoodsSkuPopup extends PopupWindow implements View.OnClickListener {

    private View mRootView;
    private Context mContext;
    private ArrayList<SkuView> mSkuViewList= new ArrayList<>();
    private ImageView mCloseIv,mGoodsIconIv;
    private TextView mGoodsCodeTv,mGoodsPriceTv;
    private NumberButton mSkuCountBtn;
    private Button mAddCartBtn;
    private LinearLayout mSkuView;

    public GoodsSkuPopup(Context context) {
        this(context,null);
    }

    public GoodsSkuPopup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GoodsSkuPopup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView=inflater.inflate(R.layout.layout_sku_pop,null);

        initView();
        //设置SelectPicPopupWindow的view
        setContentView(mRootView);
        //设置SelectPicPopupWindow弹出框体的宽
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出框体的高
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        setAnimationStyle(R.style.AnimBottom);
//        getBackground().setAlpha(150);
        mRootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mRootView.findViewById(R.id.mPopView).getTop();
                int y = (int)event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }


    private void initView(){
        mCloseIv = mRootView.findViewById(R.id.mCloseIv);
        mCloseIv.setOnClickListener(this);
        mAddCartBtn = mRootView.findViewById(R.id.mAddCartBtn);
        mAddCartBtn.setOnClickListener(this);
        mSkuCountBtn = mRootView.findViewById(R.id.mSkuCountBtn);
        mGoodsIconIv = mRootView.findViewById(R.id.mGoodsIconIv);
        mGoodsCodeTv = mRootView.findViewById(R.id.mGoodsCodeTv);
        mGoodsPriceTv = mRootView.findViewById(R.id.mGoodsPriceTv);
        mSkuView = mRootView.findViewById(R.id.mSkuView);
        mSkuCountBtn.setCurrentNumber(1);
        ((EditText)mSkuCountBtn.findViewById(R.id.text_count)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EventBus.getDefault().post(new SkuChangeEvent());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mRootView.findViewById(R.id.mAddCartBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new AddCartEvent());
                dismiss();
            }
        });
    }

    /*
       设置商品图标
    */
    public void setGoodsIcon(String text){
        GlideUtils.loadImage(mContext,text,mGoodsIconIv);
    }
    /*
        设置商品价格
     */
    public void setmGoodsPrice(String text){
        mGoodsPriceTv.setText(text);
    }

    /*
        设置商品编号
     */
    public void setGoodsCode(String code) {
        mGoodsCodeTv.setText("商品编号:" + code);
    }

    /*
        设置商品SKU
     */
    public void setSkuData(List<GoodsSku> list){
        for(GoodsSku goodsSku:list){
            SkuView skuView = new SkuView(mContext);
            skuView.setGoodsSku(goodsSku);
            mSkuViewList.add(skuView);
            mSkuView.addView(skuView);
        }
    }

    /*
        获取选中的SKU
     */
    public String getSelectSku(){
        String skuInfo = "";
        for(SkuView skuView : mSkuViewList){
            skuInfo += skuView.getSkuInfo().split(",")[1]+",";
        }
        return skuInfo.substring(0,skuInfo.length()-1);
    }

    /*
        获取商品数量
     */
    public int getSelectCount(){
        return mSkuCountBtn.getNumber();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mCloseIv:
                dismiss();
                break;
            case R.id.mAddCartBtn:
                dismiss();
                break;

        }
    }
}
