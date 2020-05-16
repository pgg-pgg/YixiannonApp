package com.pgg.yixiannonapp.adapter.order;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.order.Order;
import com.pgg.yixiannonapp.domain.order.OrderGoods;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.module.order.common.OrderConstant;
import com.pgg.yixiannonapp.module.order.common.OrderStatus;
import com.pgg.yixiannonapp.utils.GlideUtils;
import com.pgg.yixiannonapp.utils.PixelUtil;

import java.util.List;

public class OrderAdapter extends BaseQuickAdapter<Order,BaseViewHolder> {

    public OrderAdapter(int layoutResId, List<Order> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, final Order item) {
        int mTotalCount=0;
        RelativeLayout mSingleGoodsView = helper.convertView.findViewById(R.id.mSingleGoodsView);
        LinearLayout mMultiGoodsView = helper.convertView.findViewById(R.id.mMultiGoodsView);
        ImageView mGoodsIconIv = helper.convertView.findViewById(R.id.mGoodsIconIv);
        TextView mGoodsDescTv = helper.convertView.findViewById(R.id.mGoodsDescTv);
        TextView mGoodsPriceTv = helper.convertView.findViewById(R.id.mGoodsPriceTv);
        TextView mGoodsCountTv = helper.convertView.findViewById(R.id.mGoodsCountTv);
        TextView mOrderInfoTv = helper.convertView.findViewById(R.id.mOrderInfoTv);
        Button mConfirmBtn = helper.convertView.findViewById(R.id.mConfirmBtn);
        Button mPayBtn = helper.convertView.findViewById(R.id.mPayBtn);
        Button mCancelBtn = helper.convertView.findViewById(R.id.mCancelBtn);
        TextView mOrderStatusNameTv = helper.convertView.findViewById(R.id.mOrderStatusNameTv);


        if (item.getOrderGoodsList().size()==1){
            //单个商品
            mSingleGoodsView.setVisibility(View.VISIBLE);
            mMultiGoodsView.setVisibility(View.INVISIBLE);//单个商品隐藏多个商品视图
            OrderGoods orderGoods = item.getOrderGoodsList().get(0);
            GlideUtils.loadImage(mContext,Constant.BASE_URL+orderGoods.getGoodsIcon(),mGoodsIconIv);
            mGoodsDescTv.setText(orderGoods.getGoodsDesc());
            mGoodsPriceTv.setText(orderGoods.getGoodsPrice()+"元/斤");
            mGoodsCountTv.setText(orderGoods.getGoodsCount()+"");
            mTotalCount = orderGoods.getGoodsCount();
        }else{
            //多个商品
            mSingleGoodsView.setVisibility(View.INVISIBLE);//多个商品隐藏单个商品视图
            mMultiGoodsView.setVisibility(View.VISIBLE);
            mMultiGoodsView.removeAllViews();
            for (OrderGoods orderGoods : item.getOrderGoodsList()){
                //动态添加商品视图
                ImageView imageView=new ImageView(mContext);
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(PixelUtil.px2dp(60.0f), PixelUtil.px2dp(60.0f));
                lp.rightMargin=PixelUtil.px2dp(15f);
                imageView.setLayoutParams(lp);
                GlideUtils.loadImage(mContext,Constant.BASE_URL+orderGoods.getGoodsIcon(),imageView);
                mMultiGoodsView.addView(imageView);
                mTotalCount+=orderGoods.getGoodsCount();
            }
        }
        mOrderInfoTv.setText("合计:"+mTotalCount+"件商品，总价:"+item.getTotalPrice()+"元");
        switch (item.getOrderStatus()){
            //根据订单状态设置颜色，文案及对应操作按钮
            case OrderStatus.ORDER_WAIT_PAY:
                mOrderStatusNameTv.setText("待支付");
                mOrderStatusNameTv.setTextColor(mContext.getResources().getColor(R.color.common_red));
                setOptVisiable(false,true,true,helper);
                break;
            case OrderStatus.ORDER_WAIT_CONFIRM:
                mOrderStatusNameTv.setText("待收货");
                mOrderStatusNameTv.setTextColor(mContext.getResources().getColor(R.color.common_red));
                setOptVisiable(true,false,true,helper);
                break;
            case OrderStatus.ORDER_COMPLETED:
                mOrderStatusNameTv.setText("已完成");
                mOrderStatusNameTv.setTextColor(mContext.getResources().getColor(R.color.common_red));
                setOptVisiable(false,false,false,helper);
                break;
            case OrderStatus.ORDER_CANCELED:
                mOrderStatusNameTv.setText("已取消");
                mOrderStatusNameTv.setTextColor(mContext.getResources().getColor(R.color.common_red));
                setOptVisiable(false,false,false,helper);
                break;

        }
        //设置确认收货点击事件
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onOptClick(OrderConstant.OPT_ORDER_CONFIRM,item);
                }
            }
        });

        //设置支付订单点击事件
        mPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onOptClick(OrderConstant.OPT_ORDER_PAY,item);
                }
            }
        });

        //设置取消订单点击事件
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onOptClick(OrderConstant.OPT_ORDER_CANCEL,item);
                }
            }
        });
        helper.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener!=null){
                    itemClickListener.OnItemClick(v,item);
                }
            }
        });
    }

    /**
     * 设置操作按钮显示隐藏
     */
    private void setOptVisiable(boolean confirmVisible,boolean waitPayVisible,boolean cancelVisible, BaseViewHolder helper){
        helper.convertView.findViewById(R.id.mConfirmBtn).setVisibility(confirmVisible?View.VISIBLE:View.INVISIBLE);
        helper.convertView.findViewById(R.id.mPayBtn).setVisibility(waitPayVisible?View.VISIBLE:View.INVISIBLE);
        helper.convertView.findViewById(R.id.mCancelBtn).setVisibility(cancelVisible?View.VISIBLE:View.INVISIBLE);
        if (confirmVisible || waitPayVisible || cancelVisible){
            helper.convertView.findViewById(R.id.mBottomView).setVisibility(View.VISIBLE);
        }else{
            helper.convertView.findViewById(R.id.mBottomView).setVisibility(View.INVISIBLE);
        }
    }

    private OnOptClickListener listener;
    public void setOnOptClickListener(OnOptClickListener listener){
        this.listener = listener;
    }
    public interface OnOptClickListener{
        void onOptClick(int optType,Order order);
    }

    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }
    public interface OnItemClickListener{
        void OnItemClick(View view,Order order);
    }
}
