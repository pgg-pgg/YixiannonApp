package com.pgg.yixiannonapp.module.message;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.base.BaseFragment;

import butterknife.BindView;

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
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment nowFragment;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rv_connect:
                //最近联系
                //商品详情tab
                switchFragment(nowFragment, messageListFragment);
                nowFragment = messageListFragment;
                view_h_connect.setVisibility(View.VISIBLE);
                view_h_im_person.setVisibility(View.GONE);
                break;

            case R.id.rv_connect_person:
                //联系人列表
                switchFragment(nowFragment, messagePersonListFragment);
                nowFragment = messagePersonListFragment;
                view_h_im_person.setVisibility(View.VISIBLE);
                view_h_connect.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_message;
    }

    @Override
    public void initView() {
        rv_connect.setOnClickListener(this);
        rv_connect_person.setOnClickListener(this);
        messageListFragment = new MessageListFragment();
        messagePersonListFragment = new MessagePersonListFragment();

        nowFragment = messageListFragment;
        fragmentManager = getChildFragmentManager();
        //默认显示商品详情tab
        fragmentManager.beginTransaction().replace(R.id.frameLayout_message, nowFragment).commitAllowingStateLoss();
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
}
