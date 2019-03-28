package com.pgg.yixiannonapp.module.login_register.login;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;
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

    private float mWidth, mHeight;

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

    @OnClick(R.id.main_btn_login)
    public void onClick(){
        // 计算出控件的高与宽
        mWidth = main_btn_login.getMeasuredWidth();
        mHeight = main_btn_login.getMeasuredHeight();
        // 隐藏输入框
        mName.setVisibility(View.INVISIBLE);
        mPsw.setVisibility(View.INVISIBLE);

        inputAnimator(mInputLayout, mWidth, mHeight);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recovery();
            }
        },2000);
    }

    @Override
    public void initPresenter() {

    }

    /**
     * 输入框的动画效果
     *
     * @param view
     *            控件
     * @param w
     *            宽
     * @param h
     *            高
     */
    private void inputAnimator(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /**
                 * 动画结束后，先显示加载的动画，然后再隐藏输入框
                 */
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });

    }

    /**
     * 恢复初始状态
     */
    private void recovery() {
        progress.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);
        mName.setVisibility(View.VISIBLE);
        mPsw.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);


        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f,1f );
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }

    /**
     * 出现进度动画
     *
     * @param view
     */
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();

    }

    public class JellyInterpolator extends LinearInterpolator {
        private float factor;

        public JellyInterpolator() {
            this.factor = 0.15f;
        }

        @Override
        public float getInterpolation(float input) {
            return (float) (Math.pow(2, -10 * input)
                    * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1);
        }
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
