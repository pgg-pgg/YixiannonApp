package com.pgg.yixiannonapp.module.order.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.kennyc.view.MultiStateView;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.order.OrderAdapter;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.order.Order;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.module.order.OrderDetailActivity;
import com.pgg.yixiannonapp.module.order.common.OrderConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderFragment extends BaseFragment {


    @BindView(R.id.mOrderRv)
    RecyclerView mOrderRv;
    @BindView(R.id.mMultiStateView)
    MultiStateView mMultiStateView;
    private OrderAdapter mAdapter;
    private List<Order> data = new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_order;
    }

    @Override
    public void initView() {
        mOrderRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new OrderAdapter(R.layout.layout_order_item, data);
        mOrderRv.setAdapter(mAdapter);

        mAdapter.setOnOptClickListener(new OrderAdapter.OnOptClickListener() {
            @Override
            public void onOptClick(int optType, Order order) {
                switch (optType) {
                    case OrderConstant.OPT_ORDER_PAY:
                        //todo:跳转到支付页面
                        Toast.makeText(getContext(),"支付订单",Toast.LENGTH_SHORT).show();
//                        ARouter.getInstance().build(RouterPath.PaySdk.PATH_PAY)
//                                .withInt(ProviderConstant.KEY_ORDER_ID, order.id)
//                                .withLong(ProviderConstant.KEY_ORDER_PRICE, order.totalPrice)
//                                .navigation();
                        break;
                    case OrderConstant.OPT_ORDER_CONFIRM:
                        //todo：确认订单
                        Toast.makeText(getContext(),"确认订单",Toast.LENGTH_SHORT).show();
//                        mPresenter.confirmOrder(orderId = order.id)
                        break;

                    case OrderConstant.OPT_ORDER_CANCEL:
                        showCancelDialog(order);
                        break;

                }
            }
        });

        mAdapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, Order order) {
                Intent intent = new Intent(getActivity(),OrderDetailActivity.class);
                intent.putExtra(Constant.KEY_ORDER_ID,order.getId());
                startActivity(intent);
            }
        });

        initData();
    }

    private void initData() {
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            }
        },2000);
    }


    /**
     * 取消订单对话框
     */
    private void showCancelDialog(Order order) {
        new AlertDialog.Builder(getContext()).setTitle("取消订单").setMessage("确定取消该订单？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        if (i==0){
                            //todo：取消订单
                            Toast.makeText(getContext(),"取消订单",Toast.LENGTH_SHORT).show();
//                            mPresenter.cancelOrder(order.id)
                        }
                    }
                }).show();
    }
    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }
}
