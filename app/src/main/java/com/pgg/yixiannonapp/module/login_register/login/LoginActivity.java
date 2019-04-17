package com.pgg.yixiannonapp.module.login_register.login;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.base.BaseCommonActivity;
import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.domain.User;
import com.pgg.yixiannonapp.domain.UserStateBean;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.module.login_register.register.RegisterActivity;
import com.pgg.yixiannonapp.net.httpData.HttpData;
import com.pgg.yixiannonapp.utils.SPUtils;
import com.pgg.yixiannonapp.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import rx.Observer;

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
        main_btn_login.setEnabled(false);
        // 隐藏输入框
        edit_password.setVisibility(View.INVISIBLE);
        edit_id.setVisibility(View.INVISIBLE);

        final String user_name = edit_id.getText().toString();
        final String user_pwd = edit_password.getText().toString();


        initLogin(user_name,user_pwd);

    }

    private void initLogin(final String user_name, final String user_pwd){
        JMessageClient.login(user_name, user_pwd, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                switch (i) {
                    case 801003:
                        loginError("用户名错误");
                        break;
                    case 871301:
                        loginError("密码格式错误");
                        break;
                    case 801004:
                        loginError("密码错误");
                        break;
                    case 871201:
                        loginError("网络错误，请检查网络");
                        break;
                    case 0:
                        HttpData.getInstance().login(new Observer<Results<User>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                loginError("登录失败，请检查网络");
                                Log.e("pgggg=====",e.getMessage());
                            }

                            @Override
                            public void onNext(Results<User> results) {
                                if (results.getCode()==0){
                                    //登录成功,进行登录操作
                                    updateUserInLocal(results);
                                    results.getData().setUser_state("1");
                                    EventBus.getDefault().post(results.getData());
                                    EventBus.getDefault().post(UserStateBean.getInstance("1"));
                                    finish();
                                }else {
                                    loginError("登录成功，请检查网络");
                                }
                            }
                        },user_name,user_pwd);
                        break;
                    default:

                        break;
                }

            }
        });
    }

    private void updateUserInLocal(Results<User> results) {
        SPUtils.put(getContext(),Constant.USER_ID,results.getData().getId());
        SPUtils.put(getContext(),Constant.USER_NAGE,results.getData().getUser_name());
        SPUtils.put(getContext(),Constant.USER_NICK,results.getData().getUser_nick_name());
        SPUtils.put(getContext(),Constant.USER_STATE,"1");
        SPUtils.put(getContext(),Constant.USER_MOBILE,TextUtils.isEmpty(results.getData().getUser_mobile())?"":results.getData().getUser_mobile());
        SPUtils.put(getContext(),Constant.USER_IDENTITY_CARD,TextUtils.isEmpty(results.getData().getUser_identity_card())?"":results.getData().getUser_identity_card());
        SPUtils.put(getContext(),Constant.USER_REAL_NAME,TextUtils.isEmpty(results.getData().getUser_real_name())?"":results.getData().getUser_real_name());
        SPUtils.put(getContext(),Constant.USER_SIGN,TextUtils.isEmpty(results.getData().getUser_sign())?"":results.getData().getUser_sign());
    }


    private void loginError(String message) {
        new SVProgressHUD(this).showInfoWithStatus(message);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recovery();
            }
        },1000);
        main_btn_login.setEnabled(true);
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
        edit_id.setVisibility(View.VISIBLE);
        edit_password.setVisibility(View.VISIBLE);
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
}
