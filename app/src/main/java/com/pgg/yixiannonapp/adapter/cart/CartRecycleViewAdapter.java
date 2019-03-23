package com.pgg.yixiannonapp.adapter.cart;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.CartGoods;
import com.pgg.yixiannonapp.utils.GlideUtils;

import java.util.List;

import ren.qinc.numberbutton.NumberButton;

public class CartRecycleViewAdapter extends BaseQuickAdapter<CartGoods,BaseViewHolder> {


    public CartRecycleViewAdapter(int layoutResId, List<CartGoods> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CartGoods item) {
        //是否选中
        helper.setChecked(R.id.mCheckedCb,item.isSelected());
        //加载商品图片
        GlideUtils.loadImage(mContext,item.getGoodsIcon(),(ImageView) helper.convertView.findViewById(R.id.mGoodsIconIv));
        //商品描述
        helper.setText(R.id.mGoodsDescTv,item.getGoodsDesc());
        //商品SKU
        helper.setText(R.id.mGoodsSkuTv,item.getGoodsSku());
        //商品价格
        helper.setText(R.id.mGoodsPriceTv,item.getGoodsPrice()+"元");
        //商品数量
        NumberButton mGoodsCountBtn = helper.convertView.findViewById(R.id.mGoodsCountBtn);
        mGoodsCountBtn.setCurrentNumber(item.getGoodsCount());
        //选中按钮事件
        final CheckBox checkBox = helper.convertView.findViewById(R.id.mCheckedCb);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setSelected(checkBox.isChecked());
            }
        });
//        holder.itemView.mCheckedCb.onClick{
//            model.isSelected=holder.itemView.mCheckedCb.isChecked
//            val isAllChecked=dataList.all { it.isSelected }
//            Bus.send(CartAllCheckedEvent(isAllChecked))
//            notifyDataSetChanged()
//        }
//
//        //商品数量变化监听
//        holder.itemView.mGoodsCountBtn.getEditText().addTextChangedListener(object :DefaultTextWatcher(){
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                model.goodsCount=s.toString().toInt()
//                Bus.send(UpdateTotalPriceEvent())
//            }
//        })
    }
}
