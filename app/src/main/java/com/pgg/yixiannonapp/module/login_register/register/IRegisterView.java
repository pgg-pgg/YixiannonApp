package com.pgg.yixiannonapp.module.login_register.register;

import com.pgg.yixiannonapp.domain.Results;
import com.pgg.yixiannonapp.domain.User;

public interface IRegisterView {

    void showProgress();

    void hideProgress();

    void onShowFailMsg();

    void ToLoginActivity();

    void onShowSuccessMsg(Results<User> results);

    void showOnResponseError(Results<User> data);//请求网络成功，但是没有获取正确的数据

    void clearEditText();
}
