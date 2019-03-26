package com.pgg.yixiannonapp.module.my;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.CartGoods;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.module.login_register.login.LoginActivity;
import com.pgg.yixiannonapp.widget.GridViewChannelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

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



    @OnClick({R.id.civ_userhead_me,R.id.fab_edit_view,R.id.mWaitPayOrderTv,R.id.mWaitConfirmOrderTv,R.id.mCompleteOrderTv,
            R.id.mEvaluationOrderTv,R.id.tv_my_money,R.id.tv_my_bill,R.id.tv_my_coupon,R.id.tv_my_vip,R.id.tv_my_evaluation,
            R.id.tv_my_goodsfrom,R.id.tv_my_address,R.id.tv_my_question,R.id.tv_my_phone,R.id.tv_my_opinion})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.civ_userhead_me:
                //头像点击事件
                Toast.makeText(getContext(),"登录",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this.getContext(),LoginActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.fab_edit_view:
                //编辑个人信息

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
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_my;
    }

    @Override
    public void initView() {
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }
}
