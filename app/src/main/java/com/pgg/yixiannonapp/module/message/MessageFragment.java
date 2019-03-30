package com.pgg.yixiannonapp.module.message;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.User;
import com.pgg.yixiannonapp.global.Constant;
import com.pgg.yixiannonapp.utils.SPUtils;
import com.pgg.yixiannonapp.widget.NoLoginFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class MessageFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.rv_connect)
    RelativeLayout rv_connect;
    @BindView(R.id.rv_connect_person)
    RelativeLayout rv_connect_person;

    @BindView(R.id.view_h_connect)
    View view_h_connect;
    @BindView(R.id.view_h_im_person)
    View view_h_im_person;

    @BindView(R.id.frameLayout_message)
    FrameLayout frameLayout_message;


    private MessageListFragment messageListFragment;
    private MessagePersonListFragment messagePersonListFragment;
    private NoLoginFragment noLoginFragment1;
    private NoLoginFragment noLoginFragment2;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment nowFragment;
    private boolean isLogin;

    @OnClick({R.id.rv_connect,R.id.rv_connect_person})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rv_connect:
                //最近联系
                if (isLogin){
                    switchFragment(nowFragment, messageListFragment);
                    nowFragment = messageListFragment;
                    view_h_connect.setVisibility(View.VISIBLE);
                    view_h_im_person.setVisibility(View.GONE);
                }else {
                    switchFragment(nowFragment, noLoginFragment1);
                    nowFragment = noLoginFragment1;
                    view_h_connect.setVisibility(View.VISIBLE);
                    view_h_im_person.setVisibility(View.GONE);
                }
                break;

            case R.id.rv_connect_person:
                //联系人列表
                if (isLogin){
                    switchFragment(nowFragment, messagePersonListFragment);
                    nowFragment = messagePersonListFragment;
                    view_h_im_person.setVisibility(View.VISIBLE);
                    view_h_connect.setVisibility(View.GONE);
                }else {
                    switchFragment(nowFragment, noLoginFragment2);
                    nowFragment = noLoginFragment2;
                    view_h_im_person.setVisibility(View.VISIBLE);
                    view_h_connect.setVisibility(View.GONE);
                }
                break;

        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_message;
    }

    @Override
    public void initView() {
        isLogin = (SPUtils.get(getContext(),Constant.USER_STATE,"0")+"").equals("1");
        messageListFragment = new MessageListFragment();
        messagePersonListFragment = new MessagePersonListFragment();
        noLoginFragment1 = new NoLoginFragment();
        noLoginFragment2 = new NoLoginFragment();
        fragmentManager = getChildFragmentManager();
        if (isLogin){
            //如果已经登录
            nowFragment = messageListFragment;
        }else {
            nowFragment = noLoginFragment1;
        }
        //默认显示商品详情tab
        fragmentManager.beginTransaction().replace(R.id.frameLayout_message, nowFragment).commitAllowingStateLoss();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(User user) {
        isLogin = user.getUser_state().equals("1");
        nowFragment = messageListFragment;
    }


    /**
     * 切换Fragment
     * <p>(hide、show、add)
     * @param fromFragment
     * @param toFragment
     */
    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        if (nowFragment != toFragment) {
            fragmentTransaction = fragmentManager.beginTransaction();
            if (!toFragment.isAdded()) {    // 先判断是否被add过
                fragmentTransaction.hide(fromFragment).add(R.id.frameLayout_message, toFragment).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到activity中
            } else {
                fragmentTransaction.hide(fromFragment).show(toFragment).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
