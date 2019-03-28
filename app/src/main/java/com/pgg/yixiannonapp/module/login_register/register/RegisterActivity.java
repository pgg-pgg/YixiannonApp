package com.pgg.yixiannonapp.module.login_register.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.base.BaseCommonActivity;
import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.domain.User;
import com.pgg.yixiannonapp.module.login_register.login.LoginActivity;
import com.pgg.yixiannonapp.net.httpData.HttpData;
import com.pgg.yixiannonapp.utils.GlideImageLoader;
import com.pgg.yixiannonapp.widget.TitleBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observer;

public class RegisterActivity extends BaseCommonActivity {

    @BindView(R.id.main_title)
    TitleBar main_title;

    @BindView(R.id.edit_id)
    EditText edit_id;
    @BindView(R.id.edit_nick_name)
    EditText edit_nick_name;
    @BindView(R.id.edit_password)
    EditText edit_password;
    @BindView(R.id.view_confirm)
    View view_confirm;
    @BindView(R.id.edit_confirm_password)
    EditText edit_confirm_password;
    @BindView(R.id.input_layout_confirm_psw)
    LinearLayout input_layout_confirm_psw;
    @BindView(R.id.btn_signup)
    TextView btn_signup;
    @BindView(R.id.link_login)
    TextView link_login;
    @BindView(R.id.layout_progress)
    View progress;
    @BindView(R.id.iv_camera)
    ImageView iv_circle;

    @BindView(R.id.input_layout)
    View mInputLayout;

    private float mWidth, mHeight;

    private ImagePicker imagePicker;
    ArrayList<ImageItem> images = null;
    private static String base64;
    private static boolean isHasFace = false;
    private Bitmap mBitmap;
    private int degree;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initView() {
        main_title.setLeftImage(R.drawable.icon_back);

        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());

        main_title.setRightText("登录 >");
        main_title.setLeftClickListener(new TitleBar.LeftClickListener() {
            @Override
            public void setLeftOnClickListener() {
                finish();
            }
        });

        main_title.setRightClickListener(new TitleBar.RightClickListener() {
            @Override
            public void setRightOnClickListener() {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @OnClick({R.id.iv_camera,R.id.link_login,R.id.btn_signup})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_camera:
                ChooseHeaderMethod();
                break;

            case R.id.link_login:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;

            case R.id.btn_signup:

                // 计算出控件的高与宽
                mWidth = btn_signup.getMeasuredWidth();
                mHeight = btn_signup.getMeasuredHeight();
                // 隐藏输入框
                edit_password.setVisibility(View.INVISIBLE);
                edit_nick_name.setVisibility(View.INVISIBLE);
                edit_id.setVisibility(View.INVISIBLE);
                edit_confirm_password.setVisibility(View.INVISIBLE);

                String user_name = edit_id.getText().toString();
                String user_nick_name = edit_nick_name.getText().toString();
                String user_pwd = edit_password.getText().toString();
                String confirm_pwd = edit_confirm_password.getText().toString();

                inputAnimator(mInputLayout, mWidth, mHeight);

                if (base64==null){
                    registerError("请设置你的头像");
                    return;
                }
                if (TextUtils.isEmpty(user_name)||user_name.length()<4||user_name.length()>128){
                    registerError("用户名长度限制为4-128个字节");
                    return;
                }
                if (TextUtils.isEmpty(user_nick_name)){
                    registerError("请填写昵称");
                    return;
                }

                if (TextUtils.isEmpty(user_pwd)||user_pwd.length()<4||user_pwd.length()>128){
                    registerError("密码长度限制为4-128个字节");
                    return;
                }

                if(!user_pwd.equals(confirm_pwd)){
                    registerError("您两次输入密码不一致");
                    return;
                }
                User user = new User();
                user.setUser_icon(base64);
                user.setUser_name(user_name);
                user.setUser_nick_name(user_nick_name);
                user.setUser_pwd(user_pwd);

                HttpData.getInstance().register(new Observer<Results<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        registerError("注册失败，请检查网络");
                    }

                    @Override
                    public void onNext(Results<User> results) {
                        if (results.getCode()==0){
                            //注册成功
                            finish();
                        }else {
                            registerError("注册失败，请检查网络");
                        }
                    }
                },user);
                break;
        }
    }

    private void registerError(String message) {
//        showToast(message);
        new SVProgressHUD(this).showInfoWithStatus(message);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recovery();
            }
        },1000);

    }

    /**
     * 选择图片
     */
    private void ChooseHeaderMethod() {
        imagePicker.setMultiMode(false);
        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        Integer width = Integer.valueOf(300);
        Integer height = Integer.valueOf(300);
        width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(width);
        imagePicker.setFocusHeight(height);
        imagePicker.setOutPutX(Integer.valueOf(128));
        imagePicker.setOutPutY(Integer.valueOf(128));
        Intent intent = new Intent(RegisterActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
        startActivityForResult(intent, 100);
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
        edit_confirm_password.setVisibility(View.VISIBLE);
        edit_nick_name.setVisibility(View.VISIBLE);
        edit_password.setVisibility(View.VISIBLE);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                File file = new File(images.get(0).path);
                // 从指定路径下读取图片，并获取其EXIF信息
                ExifInterface exifInterface = null;
                try {
                    exifInterface = new ExifInterface(images.get(0).path);
                    // 获取图片的旋转信息
                    int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);
                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            degree = 90;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            degree = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            degree = 270;
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (file.exists()) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    mBitmap = BitmapFactory.decodeFile(images.get(0).path, options);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(degree);
                    mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    iv_circle.setImageBitmap(mBitmap);
                    byte[] bytes = baos.toByteArray();
                    base64 = new String(Base64.encode(bytes, Base64.DEFAULT));
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void initPresenter() {

    }
}
