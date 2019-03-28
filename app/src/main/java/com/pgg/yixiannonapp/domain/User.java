package com.pgg.yixiannonapp.domain;

import cn.jiguang.imui.commons.models.IUser;

public class User {

    //用户id
    private String id;
    //用户登录名
    private String user_name;
    //用户密码
    private String user_pwd;
    //用户手机号
    private String user_mobile;
    //用户头像
    private String user_icon;
    //用户真实姓名
    private String user_real_name;
    //用户身份证号
    private String user_identity_card;
    //用户昵称
    private String user_nick_name;
    //用户签名
    private String user_sign;
    //用户登录状态
    private int user_state;
    //推送id
    private String push_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public String getUser_real_name() {
        return user_real_name;
    }

    public void setUser_real_name(String user_real_name) {
        this.user_real_name = user_real_name;
    }

    public String getUser_identity_card() {
        return user_identity_card;
    }

    public void setUser_identity_card(String user_identity_card) {
        this.user_identity_card = user_identity_card;
    }

    public String getUser_nick_name() {
        return user_nick_name;
    }

    public void setUser_nick_name(String user_nick_name) {
        this.user_nick_name = user_nick_name;
    }

    public String getUser_sign() {
        return user_sign;
    }

    public void setUser_sign(String user_sign) {
        this.user_sign = user_sign;
    }

    public int getUser_state() {
        return user_state;
    }

    public void setUser_state(int user_state) {
        this.user_state = user_state;
    }

    public String getPush_id() {
        return push_id;
    }

    public void setPush_id(String push_id) {
        this.push_id = push_id;
    }

}
