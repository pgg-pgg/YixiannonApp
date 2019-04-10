package com.pgg.yixiannonapp.domain;

public class Comments {

    private int id;
    private int goodsId;
    private int ratingStar;
    private String commentsName;
    private String commentContent;
    private int praiseNum;
    private int treadNum;
    private int relayNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(int ratingStar) {
        this.ratingStar = ratingStar;
    }

    public String getCommentsName() {
        return commentsName;
    }

    public void setCommentsName(String commentsName) {
        this.commentsName = commentsName;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public int getTreadNum() {
        return treadNum;
    }

    public void setTreadNum(int treadNum) {
        this.treadNum = treadNum;
    }

    public int getRelayNum() {
        return relayNum;
    }

    public void setRelayNum(int relayNum) {
        this.relayNum = relayNum;
    }
}
