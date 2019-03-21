package com.pgg.yixiannonapp.domain;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class MainEntity implements MultiItemEntity {
    //顶部轮播图
    public static final int BANNER_TYPE = 1;
    //各个频道
    public static final int CHANNEL_TYPE = 2;
    //公告
    public static final int COMMENT_TYPE = 3;
    //头条
    public static final int TOP_TYPE = 4;
    //为您推荐
    public static final int RECOMMEND_TYPE = 5;

    private List<BannerEntity> bannerEntities;
    private List<ChannelEntity> channelEntities;
    private List<CommentEntity> commentEntities;
    private List<TopNewsEntity> topNewsEntities;
    private List<RecommendEntity> recommendEntities;

    private int itemType;

    public MainEntity(int itemType){
        this.itemType = itemType;
    }

    public List<BannerEntity> getBannerEntities() {
        return bannerEntities;
    }

    public void setBannerEntities(List<BannerEntity> bannerEntities) {
        this.bannerEntities = bannerEntities;
    }

    public List<ChannelEntity> getChannelEntities() {
        return channelEntities;
    }

    public void setChannelEntities(List<ChannelEntity> channelEntities) {
        this.channelEntities = channelEntities;
    }

    public List<CommentEntity> getCommentEntities() {
        return commentEntities;
    }

    public void setCommentEntities(List<CommentEntity> commentEntities) {
        this.commentEntities = commentEntities;
    }

    public List<TopNewsEntity> getTopNewsEntities() {
        return topNewsEntities;
    }

    public void setTopNewsEntities(List<TopNewsEntity> topNewsEntities) {
        this.topNewsEntities = topNewsEntities;
    }

    public List<RecommendEntity> getRecommendEntities() {
        return recommendEntities;
    }

    public void setRecommendEntities(List<RecommendEntity> recommendEntities) {
        this.recommendEntities = recommendEntities;
    }

    @Override
    public int getItemType() {
        return this.itemType;
    }

    //轮播图实体类
    public static class BannerEntity extends BaseEntity{
        private int id;
        private String bannerImageUrl;
        private String clickUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBannerImageUrl() {
            return bannerImageUrl;
        }

        public void setBannerImageUrl(String bannerImageUrl) {
            this.bannerImageUrl = bannerImageUrl;
        }

        public String getClickUrl() {
            return clickUrl;
        }

        public void setClickUrl(String clickUrl) {
            this.clickUrl = clickUrl;
        }
    }

    //各个频道实体类
    public static class ChannelEntity extends BaseEntity{
        private int id;
        private String channelUrl;
        private String channelName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getChannelUrl() {
            return channelUrl;
        }

        public void setChannelUrl(String channelUrl) {
            this.channelUrl = channelUrl;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }
    }

    //公告实体类
    public static class CommentEntity extends BaseEntity{
        private int id;
        private String urlText;
        private String showText;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrlText() {
            return urlText;
        }

        public void setUrlText(String urlText) {
            this.urlText = urlText;
        }

        public String getShowText() {
            return showText;
        }

        public void setShowText(String showText) {
            this.showText = showText;
        }
    }

    //头条实体类
    public static class TopNewsEntity extends BaseEntity{
        private int id;
        private String leftTopImageUrl;
        private String topName;
        private String topDesc;
        private List<String> goodsUrls;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLeftTopImageUrl() {
            return leftTopImageUrl;
        }

        public void setLeftTopImageUrl(String leftTopImageUrl) {
            this.leftTopImageUrl = leftTopImageUrl;
        }

        public String getTopName() {
            return topName;
        }

        public void setTopName(String topName) {
            this.topName = topName;
        }

        public String getTopDesc() {
            return topDesc;
        }

        public void setTopDesc(String topDesc) {
            this.topDesc = topDesc;
        }

        public List<String> getGoodsUrls() {
            return goodsUrls;
        }

        public void setGoodsUrls(List<String> goodsUrls) {
            this.goodsUrls = goodsUrls;
        }
    }

    //为您推荐实体类
    public static class RecommendEntity extends BaseEntity{
        private int id;
        //商品图片url
        private String goodsImageUrl;
        //商品名称
        private String goodsName;
        //商品价格
        private float goodsPrice;
        //商品标签 1 多图   2 认证  3 牛商   0代表没有这个标签，不为0则代表有
        private List<Integer> goodsLabel;
        //商家地址
        private String address;
        //商家名
        private String manName;
        //发布时间
        private String releaseTime;

        private String goodsDesc;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGoodsImageUrl() {
            return goodsImageUrl;
        }

        public void setGoodsImageUrl(String goodsImageUrl) {
            this.goodsImageUrl = goodsImageUrl;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsDesc() {
            return goodsDesc;
        }

        public void setGoodsDesc(String goodsDesc) {
            this.goodsDesc = goodsDesc;
        }

        public float getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(float goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public List<Integer> getGoodsLabel() {
            return goodsLabel;
        }

        public void setGoodsLabel(List<Integer> goodsLabel) {
            this.goodsLabel = goodsLabel;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getManName() {
            return manName;
        }

        public void setManName(String manName) {
            this.manName = manName;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }
    }


}
