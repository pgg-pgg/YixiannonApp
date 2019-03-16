package com.pgg.yixiannonapp.module;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.base.BaseCustomActivity;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.global.GlobalApplication;
import com.pgg.yixiannonapp.global.StatusBarCompat;
import com.pgg.yixiannonapp.module.cart.CartFragment;
import com.pgg.yixiannonapp.module.classify.ClassifyFragment;
import com.pgg.yixiannonapp.module.main.MainFragment;
import com.pgg.yixiannonapp.module.message.MessageFragment;
import com.pgg.yixiannonapp.module.my.MyFragment;
import com.pgg.yixiannonapp.utils.SPUtils;
import com.pgg.yixiannonapp.utils.StateBarTranslucentUtils;
import com.pgg.yixiannonapp.widget.TabBar_Main;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseCustomActivity {

    public static List<String> logList=new CopyOnWriteArrayList<>();

    private static final String MAIN_FRAGMENT="MAIN_FRAGMENT";
    private static final String CLASSIFY_FRAGMENT = "CLASSIFY_FRAGMENT";
    private static final String MESSAGE_FRAGMENT = "MESSAGE_FRAGMENT";
    public static final String CART_FRAGMENT = "CART_FRAGMENT";
    public static final String MY_FRAGMENT = "MY_FRAGMENT";

    @BindView(R.id.frameLayout_main)
    FrameLayout sFrameLayoutMain;
    @BindView(R.id.main_tabbar)
    TabBar_Main sMainTab;
    @BindView(R.id.classify_tabbar)
    TabBar_Main sClassifyTab;
    @BindView(R.id.message_tabbar)
    TabBar_Main sMsgTab;
    @BindView(R.id.cart_tabbar)
    TabBar_Main sCartTab;
    @BindView(R.id.my_tabbar)
    TabBar_Main sMyTab;

    private MainFragment mainFragment;
    private ClassifyFragment classifyFragment;
    private MessageFragment messageFragment;
    private CartFragment cartFragment;
    private MyFragment myFragment;


    private FragmentManager sBaseFragmentManager;
    boolean isRestart=false;

    /**
     * 存储当前Fragment的标记
     */
    private String mCurrentIndex;
    private boolean mIsExit;

    @Override
    public void initContentView() {
        StateBarTranslucentUtils.setStateBarTranslucent(this);
        setContentView(R.layout.activity_main);
        GlobalApplication.setMainActivity(this);
        StatusBarCompat.compat(this);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        sBaseFragmentManager=getBaseFragmentManager();

        String startPage=MAIN_FRAGMENT;
        String s=(String) SPUtils.get(this, Constant.OPENNEWS,"nomagic");

        if (s.equals("magicopen")){
            sMainTab.setVisibility(View.VISIBLE);
            startPage=MAIN_FRAGMENT;
        }
        if (savedInstanceState!=null){
            initByRestart(savedInstanceState);
        }else {
            switchToFragment(startPage);
            mCurrentIndex=startPage;
        }

//        int qbox_version=(int)SPUtils.get(this,Constant.QBOX_NEW_VERSION,0);
//        if (qbox_version!=0&&qbox_version> AppUtils.getVersionCode(this)){
//            //todo 通知提示升级
//
//        }
    }

    private void switchToFragment(String index) {
        hideAllFragment();
        switch (index){
            case MAIN_FRAGMENT:
                if (sMainTab.getVisibility()==View.VISIBLE){
                    showMainFragment();
                    Logger.e("newsopen:101");
                }
                break;
            case CLASSIFY_FRAGMENT:
                showClassifyFragment();
                break;
            case MESSAGE_FRAGMENT:
                showMessageFragment();
                break;
            case CART_FRAGMENT:
                showCartFragment();
                break;
            case MY_FRAGMENT:
                showMeFragment();
                break;
            default:
                break;
        }
        mCurrentIndex=index;
    }

    private void showMeFragment() {
        if (!sMyTab.isSelected()){
            sMyTab.setSelected(true);
        }
        if (myFragment==null){
            myFragment=new MyFragment();
            addFragment(R.id.frameLayout_main,myFragment,MY_FRAGMENT);
        }else if (isRestart){
            getFragmentTransaction().show(myFragment).commit();
            isRestart=false;
        }else {
            showFragment(myFragment);
        }
    }

    private void showCartFragment() {
        if (!sCartTab.isSelected()){
            sCartTab.setSelected(true);
        }
        if (cartFragment==null){
            cartFragment=new CartFragment();
            addFragment(R.id.frameLayout_main,cartFragment,CART_FRAGMENT);
        }else if (isRestart){
            getFragmentTransaction().show(cartFragment).commit();
            isRestart=false;
        }else {
            showFragment(cartFragment);
        }
    }

    private void showMessageFragment() {
        if (!sMsgTab.isSelected()){
            sMsgTab.setSelected(true);
        }
        if (messageFragment==null){
            messageFragment=new MessageFragment();
            addFragment(R.id.frameLayout_main,messageFragment,MESSAGE_FRAGMENT);
        }else if (isRestart){
            isRestart=false;
            getFragmentTransaction().show(messageFragment).commit();
        }else {
            showFragment(messageFragment);
        }
    }

    private void showClassifyFragment() {
        if (!sClassifyTab.isSelected()){
            sClassifyTab.setSelected(true);
        }
        if (classifyFragment==null){
            classifyFragment=new ClassifyFragment();
            addFragment(R.id.frameLayout_main,classifyFragment,CLASSIFY_FRAGMENT);
        }else if (isRestart){
            isRestart=false;
            getFragmentTransaction().show(classifyFragment).commit();
        }else {
            showFragment(classifyFragment);
        }
    }

    private void showMainFragment() {
        if (sMainTab.getVisibility()!=View.VISIBLE){
            return;
        }
        if (!sMainTab.isSelected()){
            sMainTab.setSelected(true);
        }
        if (mainFragment==null){
            Logger.e("恢复状态："+"null");
            mainFragment=new MainFragment();
            addFragment(R.id.frameLayout_main,mainFragment,MAIN_FRAGMENT);
        }else if (isRestart){
            isRestart=false;
            getFragmentTransaction().show(mainFragment).commit();
        }else {
            showFragment(mainFragment);
        }
    }

    private void hideAllFragment() {
        if (mainFragment!=null){
            hideFragment(mainFragment);
        }
        if (classifyFragment!=null){
            hideFragment(classifyFragment);
        }
        if (messageFragment!=null){
            hideFragment(messageFragment);
        }
        if (cartFragment!=null){
            hideFragment(cartFragment);
        }
        if (myFragment!=null){
            hideFragment(myFragment);
        }
        if (sMainTab.getVisibility()==View.VISIBLE){
            sMainTab.setSelected(false);
        }
        sClassifyTab.setSelected(false);
        sMsgTab.setSelected(false);
        sCartTab.setSelected(false);
        sMyTab.setSelected(false);

    }

    private void initByRestart(Bundle savedInstanceState) {
        mCurrentIndex=savedInstanceState.getString("mCurrentIndex");

        isRestart=true;
        Logger.e("恢复状态"+mCurrentIndex);
        myFragment= (MyFragment) sBaseFragmentManager.findFragmentByTag(MY_FRAGMENT);
        if (sMainTab.getVisibility()==View.VISIBLE){
            mainFragment= (MainFragment) sBaseFragmentManager.findFragmentByTag(MAIN_FRAGMENT);
        }
        classifyFragment= (ClassifyFragment) sBaseFragmentManager.findFragmentByTag(CLASSIFY_FRAGMENT);
        messageFragment= (MessageFragment) sBaseFragmentManager.findFragmentByTag(MESSAGE_FRAGMENT);
        cartFragment= (CartFragment) sBaseFragmentManager.findFragmentByTag(CART_FRAGMENT);

        switchToFragment(mCurrentIndex);
    }

    @OnClick({R.id.main_tabbar,R.id.classify_tabbar,R.id.message_tabbar,R.id.cart_tabbar,R.id.my_tabbar})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.main_tabbar:
                if (!mCurrentIndex.equals(MAIN_FRAGMENT)) {
                    switchToFragment(MAIN_FRAGMENT);
                }
                break;
            case R.id.classify_tabbar:
                if (!mCurrentIndex.equals(CLASSIFY_FRAGMENT)){
                    switchToFragment(CLASSIFY_FRAGMENT);
                }
                break;
            case R.id.message_tabbar:
                if (!mCurrentIndex.equals(MESSAGE_FRAGMENT)){
                    switchToFragment(MESSAGE_FRAGMENT);
                }
                break;
            case R.id.cart_tabbar:
                if (!mCurrentIndex.equals(CART_FRAGMENT)){
                    switchToFragment(CART_FRAGMENT);
                }
                break;
            case R.id.my_tabbar:
                if (!mCurrentIndex.equals(MY_FRAGMENT)){
                    switchToFragment(MY_FRAGMENT);
                }
                break;
        }
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Constant.NEWSFRAGMENT_CATEGORYACTIVITY_REQUESTCODE&&resultCode==Constant.NEWSFRAGMENT_CATEGORYACTIVITY_RESULTCODE){
            mainFragment.initView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //b EventBus.getDefault().unregister(this);
        for (Fragment fragment:getBaseFragmentManager().getFragments()){
            getFragmentTransaction().remove(fragment);
        }
        GlobalApplication.setMainActivity(null);
        //todo 解决Android输入法造成的内存泄漏问题
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLogInfo();
    }

    private void refreshLogInfo() {
        String AllLog="";
        for (String log:logList){
            AllLog=AllLog+log+"\n\n";
        }
        Logger.e(AllLog);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mCurrentIndex",mCurrentIndex);
        Logger.e("保存状态");
    }

    /**
     * 监听手机的返回键
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (mIsExit){
                this.finish();
            }else {
                Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
                mIsExit=true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit=false;
                    }
                },2000);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 优雅的退出程序，当有其他地方退出应用时，会先返回到此页面在执行退出
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent!=null){
            boolean isExit=intent.getBooleanExtra(Constant.TAG_EXIT,false);
            if (isExit){
                finish();
            }
        }
    }
}
