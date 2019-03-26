package com.pgg.yixiannonapp.module.login_register.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.base.BaseCommonActivity;
import com.pgg.yixiannonapp.module.login_register.register.RegisterActivity;
import com.pgg.yixiannonapp.widget.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseCommonActivity {

    @BindView(R.id.main_title)
    TitleBar main_title;
    @BindView(R.id.main_btn_login)
    TextView main_btn_login;
    @BindView(R.id.layout_progress)
    View progress;
    @BindView(R.id.input_layout)
    View mInputLayout;
    @BindView(R.id.edit_id)
    EditText edit_id;
    @BindView(R.id.edit_password)
    EditText edit_password;
    @BindView(R.id.input_layout_name)
    LinearLayout mName;
    @BindView(R.id.input_layout_psw)
    LinearLayout mPsw;

    @BindView(R.id.ll_head)
    LinearLayout ll_head;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
        main_title.setLeftImage(R.drawable.icon_back);
        main_title.setRightText("注册 >");
        main_title.setLeftClickListener(new TitleBar.LeftClickListener() {
            @Override
            public void setLeftOnClickListener() {
                Toast.makeText(getContext(),"返回",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        main_title.setRightClickListener(new TitleBar.RightClickListener() {
            @Override
            public void setRightOnClickListener() {
                Toast.makeText(getContext(),"注册",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void initPresenter() {

    }

    @OnClick({R.id.main_btn_login})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.main_btn_login:
                //登录按钮
                break;
        }
    }
}
