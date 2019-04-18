package com.pgg.yixiannonapp.module.order.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.kennyc.view.MultiStateView;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.order.OrderAdapter;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.domain.order.Order;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.module.order.OrderDetailActivity;
import com.pgg.yixiannonapp.module.order.common.OrderConstant;
import com.pgg.yixiannonapp.module.pay.PayResult;
import com.pgg.yixiannonapp.net.httpData.HttpData;
import com.pgg.yixiannonapp.utils.OrderInfoUtil2_0;
import com.pgg.yixiannonapp.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import rx.Observer;

import static com.pgg.yixiannonapp.global.Constant.APPID;
import static com.pgg.yixiannonapp.global.Constant.RSA2_PRIVATE;
import static com.pgg.yixiannonapp.global.Constant.RSA_PRIVATE;
import static com.pgg.yixiannonapp.global.Constant.SDK_PAY_FLAG;

public class OrderFragment extends BaseFragment {


    @BindView(R.id.mOrderRv)
    RecyclerView mOrderRv;
    @BindView(R.id.mMultiStateView)
    MultiStateView mMultiStateView;
    private OrderAdapter mAdapter;
    private List<Order> data = new ArrayList<>();
    private int id = -1;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        updateOrderStatus(id, 2, "支付成功");

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(getContext(), "支付失败", Toast.LENGTH_SHORT).show();
                    }

                    break;
                }
                default:
                    break;
            }
        }
    };

    private void updateOrderStatus(int id, int orderStatus, final String message) {
        if (id != -1) {
            HttpData.getInstance().updateOrderStatus(new Observer<Results<Order>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Results<Order> orderResults) {
                    if (orderResults.getCode() == 0) {
                        //更新成功
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                }
            }, orderStatus, id);
        }
    }

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
                        id = order.getId();
                        payV2(order.getTotalPrice());
                        Toast.makeText(getContext(), "支付订单", Toast.LENGTH_SHORT).show();
                        break;
                    case OrderConstant.OPT_ORDER_CONFIRM:
                        //todo：确认订单
                        updateOrderStatus(order.getId(), 3, "确认收货");
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
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtra(Constant.KEY_ORDER_ID, order.getId());
                startActivity(intent);
            }
        });

        initData();
    }

    /**
     * 支付宝支付业务
     */
    private void payV2(Double mTotalPrice) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(getContext()).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {

                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, mTotalPrice);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void initData() {
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        String userName = SPUtils.get(getContext(), Constant.USER_NAGE, "") + "";
        int orderStatus = getArguments().getInt(OrderConstant.KEY_ORDER_STATUS, -1);
        if (orderStatus == 0){
            HttpData.getInstance().getOrderList(new Observer<Results<List<Order>>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                }

                @Override
                public void onNext(Results<List<Order>> listResults) {
                    if (listResults.getCode() == 0) {
                        if (listResults.getData() != null && listResults.getData().size() > 0) {
                            data.clear();
                            data.addAll(listResults.getData());
                            mAdapter.notifyDataSetChanged();
                            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        } else {
                            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                        }
                    }
                }
            },userName);
        }else {
            HttpData.getInstance().getOrderListByStatus(new Observer<Results<List<Order>>>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                }

                @Override
                public void onNext(Results<List<Order>> listResults) {
                    if (listResults.getCode() == 0) {
                        if (listResults.getData() != null && listResults.getData().size() > 0) {
                            data.clear();
                            data.addAll(listResults.getData());
                            mAdapter.notifyDataSetChanged();
                            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        } else {
                            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                        }
                    }
                }
            }, userName, orderStatus);
        }

    }


    /**
     * 取消订单对话框
     */
    private void showCancelDialog(final Order order) {
        new AlertDialog.Builder(getContext()).setTitle("取消订单").setMessage("确定取消该订单？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        updateOrderStatus(order.getId(), 4, "取消成功");
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
