package com.pgg.yixiannonapp.widget;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.module.login_register.login.LoginActivity;

import butterknife.BindView;

public class NoLoginFragment extends BaseFragment {

    @BindView(R.id.tv_to_login)
    TextView tv_to_login;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_no_login;
    }

    @Override
    public void initView() {
        tv_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getContext(),LoginActivity.class));
            }
        });
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }
}
