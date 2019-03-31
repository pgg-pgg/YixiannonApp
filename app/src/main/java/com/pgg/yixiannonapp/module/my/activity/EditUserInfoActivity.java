package com.pgg.yixiannonapp.module.my.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.base.BaseCommonActivity;
import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.domain.User;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.net.httpData.HttpData;
import com.pgg.yixiannonapp.utils.GlideImageLoader;
import com.pgg.yixiannonapp.utils.SPUtils;
import com.pgg.yixiannonapp.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observer;

public class EditUserInfoActivity extends BaseCommonActivity {

    @BindView(R.id.layout_title)
    TitleBar layout_title;
    @BindView(R.id.user_head_img_userinfo)
    CircleImageView user_head_img_userinfo;
    @BindView(R.id.user_name_userinfo)
    EditText user_name_userinfo;

    @BindView(R.id.user_geyan_userinfo)
    EditText user_geyan_userinfo;

    @BindView(R.id.user_real_name_userinfo)
    EditText user_real_name_userinfo;

    @BindView(R.id.user_sfz_userinfo)
    EditText user_sfz_userinfo;

    @BindView(R.id.user_phone_userinfo)
    EditText user_phone_userinfo;
    private ImagePicker imagePicker;
    ArrayList<ImageItem> images = null;
    private static String base64;
    private Bitmap mBitmap;
    private int degree;
    private File file = null;




    @Override
    public void initContentView() {
        setContentView(R.layout.activity_edit_user);
    }

    @Override
    public void initView() {
        //初始化标题栏
        initTopTitle();

        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
    }

    private void initTopTitle() {
        layout_title.setRightText("保存");
        layout_title.setMenuVisible(false);
        layout_title.setSearchVisible(false);
        layout_title.setMainMsgVisible(false);
        layout_title.setTitleName("个人信息");

        layout_title.setRightClickListener(new TitleBar.RightClickListener() {

            @Override
            public void setRightOnClickListener() {
                String user_nick_name = user_name_userinfo.getText().toString();
                String user_sign = user_geyan_userinfo.getText().toString();
                String user_real_name = user_real_name_userinfo.getText().toString();
                String user_identity_card = user_sfz_userinfo.getText().toString();
                String user_mobile = user_phone_userinfo.getText().toString();
                final SVProgressHUD svProgressHUD = new SVProgressHUD(getContext());
                if (TextUtils.isEmpty(user_nick_name)&&TextUtils.isEmpty(user_sign)
                        &&TextUtils.isEmpty(user_real_name)&&TextUtils.isEmpty(user_identity_card)
                        &&TextUtils.isEmpty(user_mobile)&&TextUtils.isEmpty(base64)){
                    //如果没有修改任何信息，那么直接退出界面
                    finish();
                    return;
                }
                //保存
                svProgressHUD.show();

                final User user = new User();
                user.setUser_nick_name(TextUtils.isEmpty(user_nick_name)?""+SPUtils.get(getContext(),Constant.USER_NICK,""):user_nick_name);
                user.setUser_sign(TextUtils.isEmpty(user_sign)?""+SPUtils.get(getContext(),Constant.USER_SIGN,""):user_sign);
                user.setUser_real_name(TextUtils.isEmpty(user_real_name)?""+SPUtils.get(getContext(),Constant.USER_REAL_NAME,""):user_real_name);
                user.setUser_identity_card(TextUtils.isEmpty(user_identity_card)?""+SPUtils.get(getContext(),Constant.USER_IDENTITY_CARD,""):user_identity_card);
                user.setUser_mobile(TextUtils.isEmpty(user_mobile)?""+SPUtils.get(getContext(),Constant.USER_MOBILE,""):user_mobile);
                user.setUser_icon(TextUtils.isEmpty(base64)?"":base64);
                user.setUser_name(SPUtils.get(getContext(),Constant.USER_NAGE,"")+"");

                HttpData.getInstance().updateUserInfo(new Observer<Results<User>>() {
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
                        if (userResults.getCode()==0){
                            svProgressHUD.showSuccessWithStatus("更新成功！");
                            if (file!=null){
                                JMessageClient.updateUserAvatar(file, new BasicCallback() {
                                    @Override
                                    public void gotResult(int i, String s) {
                                        if (i == 0) {
                                            Log.e("updateUserAvatar", "更新头像成功");
                                        } else {
                                            Log.e("updateUserAvatar", "更新头像失败");
                                        }
                                    }
                                });
                            }
                            user.setUser_state("1");
                            EventBus.getDefault().post(user);
                            SPUtils.put(getContext(),Constant.USER_SIGN,user.getUser_sign());
                            SPUtils.put(getContext(),Constant.USER_NICK,user.getUser_nick_name());
                            SPUtils.put(getContext(),Constant.USER_MOBILE,user.getUser_mobile());
                            SPUtils.put(getContext(),Constant.USER_IDENTITY_CARD,user.getUser_identity_card());
                            SPUtils.put(getContext(),Constant.USER_REAL_NAME,user.getUser_real_name());
                            finish();
                        }else {
                            svProgressHUD.showErrorWithStatus("更新失败");
                        }
                    }
                },user);
            }
        });
    }

    @OnClick({R.id.user_head_img_userinfo})
    public void onClick(){
        ChooseHeaderMethod();
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
        Intent intent = new Intent(EditUserInfoActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                file = new File(images.get(0).path);
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
                    user_head_img_userinfo.setImageBitmap(mBitmap);
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
