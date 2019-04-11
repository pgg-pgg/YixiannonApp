package com.pgg.yixiannonapp.domain;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.pgg.yixiannonapp.domain.GoodsDetail.GoodsSku;

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
        private String clickUrl;

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

        public String getClickUrl() {
            return clickUrl;
        }

        public void setClickUrl(String clickUrl) {
            this.clickUrl = clickUrl;
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
        private String goodsUrlLeft;
        private String goodsUrlRight;

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

        public String getGoodsUrlLeft() {
            return goodsUrlLeft;
        }

        public void setGoodsUrlLeft(String goodsUrlLeft) {
            this.goodsUrlLeft = goodsUrlLeft;
        }

        public String getGoodsUrlRight() {
            return goodsUrlRight;
        }

        public void setGoodsUrlRight(String goodsUrlRight) {
            this.goodsUrlRight = goodsUrlRight;
        }
    }

    //为您推荐实体类
    public static class RecommendEntity extends BaseEntity{
        private int id;
        //商品图片url
        private String goodsImageUrl;
        //商品轮播图
        private String goodsBannerUrl;
        //商品名称
        private String goodsName;
        //商品价格
        private String goodsPrice;
        //商品原价
        private String goodsOldPrice;
        //商品标签 1 多图   2 认证  3 牛商   0代表没有这个标签，不为0则代表有
        private String goodsLabel;
        //商家地址
        private String address;
        //商家名
        private String manName;
        //发布时间
        private String releaseTime;
        //商品描述
        private String goodsDesc;

        //用户点评
        private List<Comments> comments;
        private List<RecommendEntity> recommends;
        private String goodsDetails;
        private List<GoodsSku> goodsSkus;


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

        public String getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(String goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public String getGoodsLabel() {
            return goodsLabel;
        }

        public void setGoodsLabel(String goodsLabel) {
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

        public String getGoodsBannerUrl() {
            return goodsBannerUrl;
        }

        public void setGoodsBannerUrl(String goodsBannerUrl) {
            this.goodsBannerUrl = goodsBannerUrl;
        }

        public String getGoodsOldPrice() {
            return goodsOldPrice;
        }

        public void setGoodsOldPrice(String goodsOldPrice) {
            this.goodsOldPrice = goodsOldPrice;
        }

        public List<Comments> getComments() {
            return comments;
        }

        public void setComments(List<Comments> comments) {
            this.comments = comments;
        }

        public List<RecommendEntity> getRecommends() {
            return recommends;
        }

        public void setRecommends(List<RecommendEntity> recommends) {
            this.recommends = recommends;
        }

        public String getGoodsDetails() {
            return goodsDetails;
        }

        public void setGoodsDetails(String goodsDetails) {
            this.goodsDetails = goodsDetails;
        }

        public List<GoodsSku> getGoodsSkus() {
            return goodsSkus;
        }

        public void setGoodsSkus(List<GoodsSku> goodsSkus) {
            this.goodsSkus = goodsSkus;
        }
    }


}
