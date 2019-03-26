package com.pgg.yixiannonapp.module.login_register.register;

import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.base.BaseCommonActivity;
import com.pgg.yixiannonapp.module.login_register.login.LoginActivity;
import com.pgg.yixiannonapp.widget.TitleBar;

import butterknife.BindView;

public class RegisterActivity extends BaseCommonActivity {

    @BindView(R.id.main_title)
    TitleBar main_title;

    @BindView(R.id.edit_id)
    EditText edit_id;
    @BindView(R.id.edit_password)
    EditText edit_password;
    @BindView(R.id.view_confirm)
    View view_confirm;
    @BindView(R.id.edit_confirm_password)
    EditText edit_confirm_password;
    @BindView(R.id.input_layout_confirm_psw)
    LinearLayout input_layout_confirm_psw;
    @BindView(R.id.btn_signup)
    AppCompatButton btn_signup;
    @BindView(R.id.link_login)
    TextView link_login;
    @BindView(R.id.layout_progress)
    View progress;
    @BindView(R.id.iv_camera)
    ImageView iv_circle;
    @Override
    public void initContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initView() {
        main_title.setLeftImage(R.drawable.icon_back);
        view_confirm.setVisibility(View.VISIBLE);
        input_layout_confirm_psw.setVisibility(View.VISIBLE);
        main_title.setRightText("登录 >");
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
                Toast.makeText(getContext(),"登录",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void initPresenter() {

    }
}
