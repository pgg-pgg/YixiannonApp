package com.pgg.yixiannonapp.domain;

public class UserStateBean {

    public final String user_state;

    public static UserStateBean getInstance(String user_state) {
        return new UserStateBean(user_state);
    }

    private UserStateBean(String user_state) {
        this.user_state = user_state;
    }
}
