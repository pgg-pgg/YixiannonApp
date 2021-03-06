package com.pgg.yixiannonapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by pgg on 2018/5/2.
 */

public abstract class BaseFragment extends Fragment implements IBaseView {

    private BaseActivity mActivity;
    private View mLayoutView;
    private boolean isInit = false;
    private boolean isLoad = false;

    /**
     * 初始化布局文件
     * @return
     */
    public abstract int getLayoutRes();

    /**
     * 初始化视图
     */
    public abstract void initView();

    /**
     * 如果Fragment创建需要数据，在这里接受传进来的数据
     */
    protected abstract void managerArguments();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            managerArguments();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mLayoutView!=null){
            ViewGroup parent= (ViewGroup) mLayoutView.getParent();
            isInit = true;
            if (parent!=null){
                parent.removeView(mLayoutView);
            }
        }else {
            mLayoutView=getCreateView(inflater,container);
            ButterKnife.bind(this,mLayoutView);
            isInit = true;
            initView();
        }
        isCanLoadData();
        return mLayoutView;

    }

    /**
     * 获取Fragment的布局文件的View
     * @param inflater
     * @param container
     * @return
     */

    private View getCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getLayoutRes(),container,false);
    }

    /**
     * 获取当前Fragment的状态
     * @return true为正常，false为未加载或正在删除
     */
    private boolean getStatus(){
        return (isAdded()&&!isRemoving());
    }

    public BaseActivity getBaseActivity(){
        if (mActivity==null){
            mActivity=(BaseActivity)getActivity();
        }
        return mActivity;
    }
    @Override
    public void showProgress(boolean flag, String message) {
        if (getStatus()){
            BaseActivity activity=getBaseActivity();
            if (activity!=null){
                activity.showProgress(flag,message);
            }
        }
    }

    /**
     * 视图是否已经对用户可见，系统的方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 懒加载
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }
        if (getUserVisibleHint() && !isLoad) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
    }


    /**
     * 第一次加载的处理   此处可以留给加载网络去处理
     */
    public void lazyLoad() {

    }

    /**
     * 页面停止加载的处理
     */
    public void stopLoad() {

    }

    @Override
    public void showProgress(String message) {
        showProgress(true,message);
    }

    @Override
    public void showProgress() {
        showProgress(true);
    }

    @Override
    public void showProgress(boolean flag) {
        showProgress(flag,"");
    }

    @Override
    public void hideProgress() {
        if (getStatus()){
            BaseActivity activity=getBaseActivity();
            if (activity!=null){
                activity.hideProgress();
            }
        }
    }

    @Override
    public void showToast(int resId) {
        if (getStatus()) {
            BaseActivity activity = getBaseActivity();
            if (activity != null) {
                activity.showToast(resId);
            }
        }
    }

    @Override
    public void showToast(String msg) {
        if (getStatus()) {
            BaseActivity activity = getBaseActivity();
            if (activity != null) {
                activity.showToast(msg);
            }
        }
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void close() {

    }

    public abstract String getUmengFragmentName();
}
