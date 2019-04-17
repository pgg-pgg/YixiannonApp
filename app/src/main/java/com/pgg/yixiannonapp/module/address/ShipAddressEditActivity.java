package com.pgg.yixiannonapp.module.address;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.base.BaseCustomActivity;
import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.domain.order.ShipAddress;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.module.order.common.OrderConstant;
import com.pgg.yixiannonapp.net.httpData.HttpData;
import com.pgg.yixiannonapp.utils.SPUtils;
import com.pgg.yixiannonapp.widget.TitleBar;

import butterknife.BindView;
import rx.Observer;

public class ShipAddressEditActivity extends BaseCustomActivity {


    @BindView(R.id.layout_title)
    TitleBar layout_title;
    @BindView(R.id.mShipNameEt)
    EditText mShipNameEt;
    @BindView(R.id.mShipMobileEt)
    EditText mShipMobileEt;
    @BindView(R.id.mShipAddressEt)
    EditText mShipAddressEt;
    @BindView(R.id.mSaveBtn)
    Button mSaveBtn;

    ShipAddress mAddress;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_edit_address);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initTopTitle();
        mAddress = (ShipAddress) getIntent().getSerializableExtra(OrderConstant.KEY_SHIP_ADDRESS);
        if (mAddress!=null){
            mShipAddressEt.setText(mAddress.getShipAddress());
            mShipMobileEt.setText(mAddress.getShipUserMobile());
            mShipNameEt.setText(mAddress.getShipUserName());
        }
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shipUserName = mShipNameEt.getText().toString();
                String shipMobile = mShipMobileEt.getText().toString();
                final String shipAddress = mShipAddressEt.getText().toString();
                if (TextUtils.isEmpty(shipUserName)) {
                    showToast("名称不能为空");
                    return;
                }
                if (TextUtils.isEmpty(shipMobile)) {
                    showToast("联系方式不能为空");
                    return;
                }
                if (TextUtils.isEmpty(shipAddress)) {
                    showToast("地址不能为空");
                    return;
                }

                if (mAddress == null) {
                    String userName = SPUtils.get(getContext(), Constant.USER_NAGE, "").toString();
                    ShipAddress address = new ShipAddress();
                    address.setShipAddress(shipAddress);
                    address.setShipUserMobile(shipMobile);
                    address.setShipUserName(shipUserName);
                    address.setUserName(userName);
                    address.setShipIsDefault(1);
                    final SVProgressHUD svProgressHUD = new SVProgressHUD(getContext());
                    svProgressHUD.show();
                    HttpData.getInstance().addShipAddress(new Observer<Results<ShipAddress>>() {
                        @Override
                        public void onCompleted() {
                            svProgressHUD.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            svProgressHUD.showErrorWithStatus("添加地址失败");
                        }

                        @Override
                        public void onNext(Results<ShipAddress> shipAddressResults) {
                            if (shipAddressResults.getCode() == 0) {
                                svProgressHUD.dismiss();
                                finish();
                            }
                        }
                    }, address);
                } else {
                    boolean userNameIsChange = mAddress.getShipUserName().equals(shipUserName);
                    boolean mobileIsChange = mAddress.getShipUserMobile().equals(shipMobile);
                    boolean addressIsChange = mAddress.getShipAddress().equals(shipAddress);
                    mAddress.setShipUserName(userNameIsChange ? mAddress.getShipUserName() : shipUserName);
                    mAddress.setShipUserMobile(mobileIsChange ? mAddress.getShipUserMobile() : shipMobile);
                    mAddress.setShipAddress(addressIsChange ? mAddress.getShipAddress() : shipAddress);

                    if (userNameIsChange && mobileIsChange && addressIsChange) {
                        finish();
                    } else {
                        final SVProgressHUD svProgressHUD = new SVProgressHUD(getContext());
                        svProgressHUD.show();
                        HttpData.getInstance().modifyShipAddress(new Observer<Results<ShipAddress>>() {
                            @Override
                            public void onCompleted() {
                                svProgressHUD.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                svProgressHUD.showErrorWithStatus("更新失败");
                            }

                            @Override
                            public void onNext(Results<ShipAddress> shipAddressResults) {
                                if (shipAddressResults.getCode()==0){
                                    svProgressHUD.dismiss();
                                    finish();
                                }
                            }
                        }, mAddress);
                    }
                }
            }
        });
    }

    private void initTopTitle() {
        layout_title.setTitleName("地址管理");
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
    public void initPresenter() {

    }
}
