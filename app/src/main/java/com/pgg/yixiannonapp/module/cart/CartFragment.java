package com.pgg.yixiannonapp.module.cart;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.kennyc.view.MultiStateView;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.cart.CartRecycleViewAdapter;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.CartGoods.CartAllCheckedEvent;
import com.pgg.yixiannonapp.domain.CartGoods.CartGoods;
import com.pgg.yixiannonapp.domain.CartGoods.UpdateTotalPriceEvent;
import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.domain.SelectAddressEvent;
import com.pgg.yixiannonapp.domain.UserStateBean;
import com.pgg.yixiannonapp.domain.order.Order;
import com.pgg.yixiannonapp.domain.order.OrderGoods;
import com.pgg.yixiannonapp.domain.order.ShipAddress;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.module.address.ShipAddressActivity;
import com.pgg.yixiannonapp.module.login_register.login.LoginActivity;
import com.pgg.yixiannonapp.module.pay.PayResult;
import com.pgg.yixiannonapp.net.httpData.HttpData;
import com.pgg.yixiannonapp.utils.OrderInfoUtil2_0;
import com.pgg.yixiannonapp.utils.SPUtils;
import com.pgg.yixiannonapp.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observer;

import static com.pgg.yixiannonapp.global.Constant.APPID;
import static com.pgg.yixiannonapp.global.Constant.RSA2_PRIVATE;
import static com.pgg.yixiannonapp.global.Constant.RSA_PRIVATE;
import static com.pgg.yixiannonapp.global.Constant.SDK_PAY_FLAG;

public class CartFragment extends BaseFragment {

    @BindView(R.id.mMultiStateView)
    MultiStateView mMultiStateView;
    @BindView(R.id.mCartGoodsRv)
    RecyclerView mCartGoodsRv;
    @BindView(R.id.layout_title)
    TitleBar layout_title;

    @BindView(R.id.rl_no_login)
    RelativeLayout rl_no_login;
    @BindView(R.id.tv_to_login)
    TextView tv_to_login;

    @BindView(R.id.mAllCheckedCb)
    CheckBox mAllCheckedCb;
    @BindView(R.id.mTotalPriceTv)
    TextView mTotalPriceTv;
    @BindView(R.id.mSettleAccountsBtn)
    Button mSettleAccountsBtn;
    @BindView(R.id.mDeleteBtn)
    Button mDeleteBtn;
    private boolean isLogin = false;
    CartRecycleViewAdapter adapter = null;
    List<CartGoods> cartGoods = null;

    private Double mTotalPrice = 0d;
    private ShipAddress shipAddress;
    private int orderStatus = 0;

    private List<OrderGoods> orderGoodsList;
    private List<Integer> list;
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
                        Toast.makeText(getContext(), "支付成功", Toast.LENGTH_SHORT).show();
                        orderStatus = 2;
                        addOrder(list);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        orderStatus = 1;
                        Toast.makeText(getContext(), "支付失败", Toast.LENGTH_SHORT).show();
                        addOrder(list);
                    }

                    break;
                }
                default:
                    break;
            }
        }
    };


    @OnClick({R.id.tv_to_login, R.id.mSettleAccountsBtn, R.id.mDeleteBtn, R.id.mAllCheckedCb})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_to_login:
                //去登录
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.mSettleAccountsBtn:
                //去结算
                list = new ArrayList<>();
                List<CartGoods> data = adapter.getData();
                for (CartGoods cartGoods : data) {
                    if (cartGoods.isSelected()) {
                        list.add(cartGoods.getId());
                    }
                }
                if (list.size() == 0) {
                    Toast.makeText(getContext(), "请选择需要结算的数据", Toast.LENGTH_SHORT).show();
                } else {
                    //提交结算
                    payV2();
                    Toast.makeText(getContext(), "结算" + mTotalPrice, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mDeleteBtn:
                //删除
                deleteCartGoods();
                break;
            case R.id.mAllCheckedCb:
                //全选
                for (CartGoods item : adapter.getData()) {
                    item.setSelected(mAllCheckedCb.isChecked());
                }
                adapter.notifyDataSetChanged();
                updateTotalPrice();
                break;
        }
    }

    private void addOrder(List<Integer> list) {
        int ship_id = (int) SPUtils.get(getContext(), Constant.SHIP_ID, -1);
        if (ship_id == -1) {
            new AlertDialog.Builder(getContext()).setTitle("警告").setMessage("未设置默认地址，请选择地址")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            Intent intent = new Intent(getContext(), ShipAddressActivity.class);
                            intent.putExtra(Constant.ISCHOOSE_ADDRESS, true);
                            startActivity(intent);
                        }
                    }).show();
        } else {
            final SVProgressHUD svProgressHUD = new SVProgressHUD(getContext());
            svProgressHUD.show();
            orderGoodsList = new ArrayList<>();
            for (CartGoods cartGoods : adapter.getData()) {
                if (cartGoods.isSelected()) {
                    OrderGoods orderGoods = new OrderGoods();
                    orderGoods.setGoodsCount(cartGoods.getGoods_count());
                    orderGoods.setGoodsDesc(cartGoods.getGoods_desc());
                    orderGoods.setGoodsIcon(cartGoods.getGoods_icon());
                    orderGoods.setGoodsPrice(cartGoods.getGoods_price() + "");
                    orderGoods.setGoodsSku(cartGoods.getGoods_sku());
                    orderGoods.setGoodsId(cartGoods.getGoods_id());
                    orderGoodsList.add(orderGoods);
                }
            }
            final String userName = SPUtils.get(getContext(), Constant.USER_NAGE, "") + "";
            final Order order = new Order();
            order.setTotalPrice(mTotalPrice);
            order.setUserName(userName);
            order.setOrderGoodsList(orderGoodsList);
            order.setOrderStatus(orderStatus);
            order.setPayType(0);
            order.setCartGoodsIds(list);
            shipAddress = new ShipAddress();
            shipAddress.setId(ship_id);
            order.setShipAddress(shipAddress);
            HttpData.getInstance().addOrder(new Observer<Results<Order>>() {
                @Override
                public void onCompleted() {
                    svProgressHUD.dismiss();
                }

                @Override
                public void onError(Throwable e) {
                    svProgressHUD.showErrorWithStatus("添加订单失败");
                }

                @Override
                public void onNext(Results<Order> orderResults) {
                    if (orderResults.getCode() == 0) {
                        getCartGoodsData(userName);
                    }
                }
            }, order);
        }
    }

    /**
     * 支付宝支付业务
     */
    private void payV2() {
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
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,mTotalPrice);
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

    private void deleteCartGoods() {
        list = new ArrayList<>();
        List<CartGoods> data = adapter.getData();
        for (CartGoods cartGoods : data) {
            if (cartGoods.isSelected()) {
                list.add(cartGoods.getId());
            }
        }
        if (list.size() == 0) {
            Toast.makeText(getContext(), "请选择需要删除的数据", Toast.LENGTH_SHORT).show();
        } else {
            final SVProgressHUD svProgressHUD = new SVProgressHUD(getContext());
            svProgressHUD.show();
            HttpData.getInstance().deleteCartGoodsList(new Observer<Results<CartGoods>>() {
                @Override
                public void onCompleted() {
                    Toast.makeText(getContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                    svProgressHUD.dismiss();
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(getContext(), "删除失败", Toast.LENGTH_SHORT).show();
                    svProgressHUD.dismiss();
                }

                @Override
                public void onNext(Results<CartGoods> cartGoodsResults) {
                    if (cartGoodsResults.getCode() == 0) {
                        //删除成功
                        refreshRightState();
                        String user_name = SPUtils.get(getContext(), Constant.USER_NAGE, "") + "";
                        getCartGoodsData(user_name);
                        svProgressHUD.dismiss();
                    }
                }
            }, list);
        }
    }

    private void updateTotalPrice() {
        Double totalPrice = 0d;
        for (CartGoods cartGoods : adapter.getData()) {
            if (cartGoods.isSelected()) {
                totalPrice += cartGoods.getGoods_count() * cartGoods.getGoods_price();
            }
        }
        mTotalPrice = totalPrice;
        mTotalPriceTv.setText("合计:" + totalPrice + "元");
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_cart;
    }

    @Override
    public void initView() {
        initRecycleListData();
        initTopTitle();
        EventBus.getDefault().register(this);
        isLogin = (SPUtils.get(getContext(), Constant.USER_STATE, "0") + "").equals("1");
        if (isLogin) {
            String user_name = SPUtils.get(getContext(), Constant.USER_NAGE, "") + "";
            mMultiStateView.setVisibility(View.VISIBLE);
            rl_no_login.setVisibility(View.GONE);
            getCartGoodsData(user_name);
        } else {
            mMultiStateView.setVisibility(View.GONE);
            rl_no_login.setVisibility(View.VISIBLE);
        }
    }

    private void initRecycleListData() {
        cartGoods = new ArrayList<>();
        adapter = new CartRecycleViewAdapter(R.layout.item_cart_goods, cartGoods);
        mCartGoodsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mCartGoodsRv.setAdapter(adapter);
    }

    private void getCartGoodsData(String user_name) {
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        HttpData.getInstance().getCartGoodsList(new Observer<Results<List<CartGoods>>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }

            @Override
            public void onNext(Results<List<CartGoods>> listResults) {
                if (listResults.getCode() == 0) {
                    if (listResults.getData().size() <= 0) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                    } else {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        cartGoods.clear();
                        cartGoods.addAll(listResults.getData());
                        adapter.notifyDataSetChanged();
                        updateTotalPrice();
                    }
                }
            }
        }, user_name);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(UserStateBean userStateBean) {
        if (userStateBean != null) {
            if (userStateBean.user_state.equals("0")) {
                mMultiStateView.setVisibility(View.GONE);
                rl_no_login.setVisibility(View.VISIBLE);
            } else {
                String user_name = SPUtils.get(getContext(), Constant.USER_NAGE, "") + "";
                mMultiStateView.setVisibility(View.VISIBLE);
                rl_no_login.setVisibility(View.GONE);
                getCartGoodsData(user_name);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(CartGoods cartGoods) {
        String user_name = SPUtils.get(getContext(), Constant.USER_NAGE, "") + "";
        getCartGoodsData(user_name);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(SelectAddressEvent event) {
        final SVProgressHUD svProgressHUD = new SVProgressHUD(getContext());
        svProgressHUD.show();
        orderGoodsList = new ArrayList<>();
        for (CartGoods cartGoods : adapter.getData()) {
            if (cartGoods.isSelected()) {
                OrderGoods orderGoods = new OrderGoods();
                orderGoods.setGoodsCount(cartGoods.getGoods_count());
                orderGoods.setGoodsDesc(cartGoods.getGoods_desc());
                orderGoods.setGoodsIcon(cartGoods.getGoods_icon());
                orderGoods.setGoodsPrice(cartGoods.getGoods_price() + "");
                orderGoods.setGoodsSku(cartGoods.getGoods_sku());
                orderGoods.setGoodsId(cartGoods.getGoods_id());
                orderGoodsList.add(orderGoods);
            }
        }
        final String userName = SPUtils.get(getContext(), Constant.USER_NAGE, "") + "";
        final Order order = new Order();
        order.setTotalPrice(mTotalPrice);
        order.setUserName(userName);
        order.setOrderGoodsList(orderGoodsList);
        order.setOrderStatus(orderStatus);
        order.setPayType(0);
        order.setCartGoodsIds(list);
        shipAddress = event.getAddress();
        order.setShipAddress(shipAddress);
        HttpData.getInstance().addOrder(new Observer<Results<Order>>() {
            @Override
            public void onCompleted() {
                svProgressHUD.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                svProgressHUD.showErrorWithStatus("添加订单失败");
            }

            @Override
            public void onNext(Results<Order> orderResults) {
                if (orderResults.getCode() == 0) {
                    getCartGoodsData(userName);
                }
            }
        }, order);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(CartAllCheckedEvent t) {
        mAllCheckedCb.setChecked(t.isAllChecked());
        updateTotalPrice();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(UpdateTotalPriceEvent t) {
        updateTotalPrice();
    }


    private void initTopTitle() {
        layout_title.setMainMsgVisible(false);
        layout_title.setSearchVisible(false);
        layout_title.setTitleName("购物车");
        layout_title.setMenuVisible(false);
        layout_title.setRightText("编辑");
        layout_title.setRightClickListener(new TitleBar.RightClickListener() {
            @Override
            public void setRightOnClickListener() {
                refreshRightState();
            }
        });
    }

    private void refreshRightState() {
        boolean isEditValue = layout_title.getRightText().equals("编辑");
        mTotalPriceTv.setVisibility(isEditValue ? View.INVISIBLE : View.VISIBLE);
        mSettleAccountsBtn.setVisibility(isEditValue ? View.INVISIBLE : View.VISIBLE);
        mDeleteBtn.setVisibility(isEditValue ? View.VISIBLE : View.INVISIBLE);
        layout_title.setRightText(isEditValue ? "完成" : "编辑");
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
