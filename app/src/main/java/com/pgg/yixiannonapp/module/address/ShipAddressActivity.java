package com.pgg.yixiannonapp.module.address;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.kennyc.view.MultiStateView;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.address.ShipAddressAdapter;
import com.pgg.yixiannonapp.base.BaseCustomActivity;
import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.domain.SelectAddressEvent;
import com.pgg.yixiannonapp.domain.order.ShipAddress;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.module.order.common.OrderConstant;
import com.pgg.yixiannonapp.net.httpData.HttpData;
import com.pgg.yixiannonapp.utils.SPUtils;
import com.pgg.yixiannonapp.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observer;

public class ShipAddressActivity extends BaseCustomActivity {

    @BindView(R.id.layout_title)
    TitleBar layout_title;
    @BindView(R.id.mMultiStateView)
    MultiStateView mMultiStateView;
    @BindView(R.id.mAddressRv)
    RecyclerView mAddressRv;
    @BindView(R.id.mAddAddressBtn)
    Button mAddAddressBtn;

    private ShipAddressAdapter mAdapter;
    private List<ShipAddress> list = new ArrayList<>();

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_address);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initTopTitle();
        mAddressRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ShipAddressAdapter(R.layout.layout_address_item, list);
        mAddressRv.setAdapter(mAdapter);

        mAdapter.setOnOptClickListener(new ShipAddressAdapter.OnOptClickListener() {
            @Override
            public void onSetDefault(ShipAddress address) {
                //设为默认地址
                final SVProgressHUD svProgressHUD = new SVProgressHUD(getContext());
                svProgressHUD.show();
                HttpData.getInstance().modifyShipAddress(new Observer<Results<ShipAddress>>() {
                    @Override
                    public void onCompleted() {
                        svProgressHUD.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        svProgressHUD.showErrorWithStatus("设置失败");
                    }

                    @Override
                    public void onNext(Results<ShipAddress> shipAddressResults) {
                        if (shipAddressResults.getCode() == 0) {
                            svProgressHUD.dismiss();
                        }
                    }
                }, address);
            }

            @Override
            public void onEdit(ShipAddress address) {
                //编辑地址
                Intent intent = new Intent(ShipAddressActivity.this, ShipAddressEditActivity.class);
                intent.putExtra(OrderConstant.KEY_SHIP_ADDRESS, address);
                startActivity(intent);
            }

            @Override
            public void onDelete(final ShipAddress address) {
                new AlertDialog.Builder(getContext()).setTitle("警告").setMessage("确认删除此地址么？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                //删除地址
                                final SVProgressHUD svProgressHUD = new SVProgressHUD(getContext());
                                svProgressHUD.show();
                                HttpData.getInstance().deleteShipAddress(new Observer<Results<ShipAddress>>() {
                                    @Override
                                    public void onCompleted() {
                                        svProgressHUD.dismiss();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        svProgressHUD.showErrorWithStatus("删除失败");
                                    }

                                    @Override
                                    public void onNext(Results<ShipAddress> shipAddressResults) {
                                        if (shipAddressResults.getCode() == 0 ){
                                            svProgressHUD.dismiss();
                                            list.remove(address);
                                            mAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }, address.getId());
                            }
                        }).show();
            }
        });
        mAdapter.setOnItemClickListener(new ShipAddressAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, ShipAddress address) {
                EventBus.getDefault().post(new SelectAddressEvent(address));
                finish();
            }
        });
        mAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShipAddressActivity.this, ShipAddressEditActivity.class));
            }
        });
    }

    private void initTopTitle() {
        layout_title.setTitleName("收货地址");
        layout_title.setMainMsgVisible(false);
        layout_title.setSearchVisible(false);
        layout_title.setMenuVisible(false);
        layout_title.setRightText("");
        layout_title.setLeftImage(R.drawable.icon_back);
        layout_title.setLeftClickListener(new TitleBar.LeftClickListener() {
            @Override
            public void setLeftOnClickListener() {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        String userName = SPUtils.get(getContext(), Constant.USER_NAGE, "").toString();
        HttpData.getInstance().getShipAddress(new Observer<Results<List<ShipAddress>>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);

            }

            @Override
            public void onNext(Results<List<ShipAddress>> listResults) {
                if (listResults.getCode() == 0) {
                    if (listResults.getData().size() <= 0) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                    } else {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        list.clear();
                        list.addAll(listResults.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                }
            }
        }, userName);
    }

    @Override
    public void initPresenter() {

    }
}
