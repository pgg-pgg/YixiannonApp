package com.pgg.yixiannonapp.adapter.cart;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.CartGoods.CartAllCheckedEvent;
import com.pgg.yixiannonapp.domain.CartGoods.CartGoods;
import com.pgg.yixiannonapp.domain.CartGoods.UpdateTotalPriceEvent;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.utils.GlideUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import ren.qinc.numberbutton.NumberButton;

public class CartRecycleViewAdapter extends BaseQuickAdapter<CartGoods, BaseViewHolder> {

    private List<CartGoods> data;
    public CartRecycleViewAdapter(int layoutResId, List<CartGoods> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CartGoods item) {
        //是否选中
        helper.setChecked(R.id.mCheckedCb, item.isSelected());
        //加载商品图片
        GlideUtils.loadImage(mContext, Constant.BASE_URL + item.getGoods_icon(), (ImageView) helper.convertView.findViewById(R.id.mGoodsIconIv));
        //商品描述
        helper.setText(R.id.mGoodsDescTv, item.getGoods_desc());
        //商品SKU
        helper.setText(R.id.mGoodsSkuTv, item.getGoods_sku());
        //商品价格
        helper.setText(R.id.mGoodsPriceTv, item.getGoods_price() + "元");
        //商品数量
        NumberButton mGoodsCountBtn = helper.convertView.findViewById(R.id.mGoodsCountBtn);
        mGoodsCountBtn.setCurrentNumber(item.getGoods_count());
        //选中按钮事件
        final CheckBox checkBox = helper.convertView.findViewById(R.id.mCheckedCb);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setSelected(checkBox.isChecked());
                boolean isAllChecked = true;
                for (CartGoods cartGoods : data){
                    if (!cartGoods.isSelected()){
                        isAllChecked = false;
                    }
                }
                EventBus.getDefault().post(new CartAllCheckedEvent(isAllChecked));
                notifyDataSetChanged();
            }
        });
        EditText editView = mGoodsCountBtn.findViewById(R.id.text_count);
        editView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                item.setGoods_count(Integer.parseInt(s.toString()));
                EventBus.getDefault().post(new UpdateTotalPriceEvent());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
