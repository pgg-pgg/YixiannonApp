package com.pgg.yixiannonapp.module.my;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.bumptech.glide.Glide;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.CartGoods;
import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.domain.User;
import com.pgg.yixiannonapp.domain.UserStateBean;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.module.login_register.login.LoginActivity;
import com.pgg.yixiannonapp.module.my.activity.EditUserInfoActivity;
import com.pgg.yixiannonapp.net.httpData.HttpData;
import com.pgg.yixiannonapp.utils.GlideUtils;
import com.pgg.yixiannonapp.utils.SPUtils;
import com.pgg.yixiannonapp.widget.GridViewChannelView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observer;

public class MyFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.tv_motto_me)
    TextView tv_motto_me;
    @BindView(R.id.civ_userhead_me)
    CircleImageView civ_userhead_me;
    @BindView(R.id.tv_username_me)
    TextView tv_username_me;
    @BindView(R.id.fab_edit_view)
    FloatingActionButton fab_edit_view;
    @BindView(R.id.mWaitPayOrderTv)
    TextView mWaitPayOrderTv;
    @BindView(R.id.mWaitConfirmOrderTv)
    TextView mWaitConfirmOrderTv;
    @BindView(R.id.mCompleteOrderTv)
    TextView mCompleteOrderTv;
    @BindView(R.id.mEvaluationOrderTv)
    TextView mEvaluationOrderTv;
    @BindView(R.id.mAllOrderTv)
    TextView mAllOrderTv;
    @BindView(R.id.tv_my_money)
    TextView tv_my_money;
    @BindView(R.id.tv_my_bill)
    TextView tv_my_bill;
    @BindView(R.id.tv_my_coupon)
    TextView tv_my_coupon;
    @BindView(R.id.tv_my_vip)
    TextView tv_my_vip;
    @BindView(R.id.tv_my_evaluation)
    TextView tv_my_evaluation;
    @BindView(R.id.tv_my_goodsfrom)
    TextView tv_my_goodsfrom;
    @BindView(R.id.tv_my_address)
    TextView tv_my_address;
    @BindView(R.id.tv_my_question)
    TextView tv_my_question;
    @BindView(R.id.tv_my_phone)
    TextView tv_my_phone;
    @BindView(R.id.tv_my_opinion)
    TextView tv_my_opinion;
    @BindView(R.id.tv_my_exit)
    TextView tv_my_exit;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_my;
    }

    @Override
    public void initView() {
        String user_name = SPUtils.get(getContext(), Constant.USER_NAGE, "") + "";
        String user_nick_name = SPUtils.get(getContext(), Constant.USER_NICK, "") + "";
        String user_sign = SPUtils.get(getContext(), Constant.USER_SIGN, "") + "";
        fab_edit_view.setEnabled(false);
        if ((SPUtils.get(getContext(), Constant.USER_STATE, "0") + "").equals("1")) {
            //用户已登录
            Glide.with(getContext()).load(Constant.BASE_URL + "image/" + user_name + ".jpg").
                    placeholder(R.drawable.ocnyang).error(R.drawable.ocnyang)
                    .centerCrop().into(civ_userhead_me);
            civ_userhead_me.setEnabled(false);
            tv_username_me.setText(TextUtils.isEmpty(user_nick_name) ? "未设置" : user_nick_name);
            tv_motto_me.setText(TextUtils.isEmpty(user_sign) ? "登录后设置" : user_sign);
            tv_my_exit.setVisibility(View.VISIBLE);
            fab_edit_view.setEnabled(true);
        }
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.civ_userhead_me, R.id.fab_edit_view, R.id.mWaitPayOrderTv, R.id.mWaitConfirmOrderTv, R.id.mCompleteOrderTv,
            R.id.mEvaluationOrderTv, R.id.tv_my_money, R.id.tv_my_bill, R.id.tv_my_coupon, R.id.tv_my_vip, R.id.tv_my_evaluation,
            R.id.tv_my_goodsfrom, R.id.tv_my_address, R.id.tv_my_question, R.id.tv_my_phone, R.id.tv_my_opinion, R.id.tv_my_exit})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.civ_userhead_me:
                //头像点击事件
                Toast.makeText(getContext(), "登录", Toast.LENGTH_SHORT).show();
                intent = new Intent(this.getContext(), LoginActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.fab_edit_view:
                //编辑个人信息
                intent = new Intent(getContext(), EditUserInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.mWaitPayOrderTv:
                //待付款

                break;

            case R.id.mWaitConfirmOrderTv:
                //待收货

                break;

            case R.id.mCompleteOrderTv:
                //已完成

                break;

            case R.id.mEvaluationOrderTv:
                //待评价

                break;
            case R.id.tv_my_money:
                //我的余额

                break;

            case R.id.tv_my_bill:
                //我的账单

                break;
            case R.id.tv_my_coupon:
                //优惠券

                break;
            case R.id.tv_my_vip:
                //我的特权

                break;
            case R.id.tv_my_evaluation:
                //我的评价

                break;
            case R.id.tv_my_goodsfrom:
                //关心货源

                break;
            case R.id.tv_my_address:
                //收货地址

                break;
            case R.id.tv_my_question:
                //常见问题

                break;
            case R.id.tv_my_phone:
                //客服电话

                break;

            case R.id.tv_my_opinion:
                //意见反馈

                break;

            case R.id.tv_my_exit:
                //退出登录
                final SVProgressHUD svProgressHUD = new SVProgressHUD(getContext());
                svProgressHUD.show();
                final User user = new User();
                user.setUser_name(SPUtils.get(getContext(), Constant.USER_NAGE, "") + "");
                user.setUser_state("0");
                HttpData.getInstance().logout(new Observer<Results<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        svProgressHUD.dismiss();
                    }

                    @Override
                    public void onNext(Results<User> userResults) {
                        svProgressHUD.dismiss();
                        if (userResults.getCode() == 0) {
                            JMessageClient.logout();
                            clearUserInfoInLocal();
                            //退出登录成功之后，通知界面刷新
                            setLoginState(user, false);
                            EventBus.getDefault().post(UserStateBean.getInstance("0"));
                        }
                    }
                }, user);

                break;
        }
    }

    private void clearUserInfoInLocal() {
        SPUtils.put(getContext(), Constant.USER_STATE, "0");
        SPUtils.put(getContext(), Constant.USER_NAGE, "");
        SPUtils.put(getContext(), Constant.USER_SIGN, "");
        SPUtils.put(getContext(), Constant.USER_NICK, "");
        SPUtils.put(getContext(), Constant.USER_MOBILE, "");
        SPUtils.put(getContext(), Constant.USER_IDENTITY_CARD, "");
        SPUtils.put(getContext(), Constant.USER_REAL_NAME, "");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(User user) {
        setLoginState(user, true);
    }

    private void setLoginState(User user, boolean isLogin) {
        if (isLogin) {
            Glide.with(getContext()).load(Constant.BASE_URL + "image/" + user.getUser_name() + ".jpg").
                    placeholder(R.drawable.ocnyang).error(R.drawable.ocnyang)
                    .centerCrop().into(civ_userhead_me);
            civ_userhead_me.setEnabled(false);
            fab_edit_view.setEnabled(true);
            tv_my_exit.setVisibility(View.VISIBLE);
            tv_username_me.setText(user.getUser_nick_name());
            tv_motto_me.setText(TextUtils.isEmpty(user.getUser_sign()) ? "登录后设置" : user.getUser_sign());
        } else {
            civ_userhead_me.setImageResource(R.drawable.ocnyang);
            civ_userhead_me.setEnabled(true);
            fab_edit_view.setEnabled(false);
            tv_my_exit.setVisibility(View.INVISIBLE);
            tv_motto_me.setText("登录后设置");
            tv_username_me.setText("未设置");
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }
}
