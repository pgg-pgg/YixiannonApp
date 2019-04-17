package com.pgg.yixiannonapp.module.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.order.OrderGoodsAdapter;
import com.pgg.yixiannonapp.base.BaseCustomActivity;
import com.pgg.yixiannonapp.domain.order.OrderGoods;
import com.pgg.yixiannonapp.global.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderDetailActivity extends BaseCustomActivity {

    @BindView(R.id.mOrderGoodsRv)
    RecyclerView mOrderGoodsRv;

    private OrderGoodsAdapter mAdapter;
    private List<OrderGoods> data = new ArrayList<>();
    @Override
    public void initContentView() {
        setContentView(R.layout.activity_order_detail);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mOrderGoodsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new OrderGoodsAdapter(R.layout.layout_order_goods_item,data);
        mOrderGoodsRv.setAdapter(mAdapter);

        int order_id = getIntent().getIntExtra(Constant.KEY_ORDER_ID, -1);
        initData(order_id);
    }

    private void initData(int order_id) {
        //加载数据

    }

    @Override
    public void initPresenter() {

    }
}
