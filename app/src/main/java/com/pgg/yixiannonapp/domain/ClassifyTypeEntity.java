package com.pgg.yixiannonapp.domain;

import java.util.List;

public class ClassifyTypeEntity {


    private int id;
    private boolean isSelected;
    private String typeName;
    private List<ClassifyDescEntity> classifyDescEntities;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<ClassifyDescEntity> getClassifyDescEntities() {
        return classifyDescEntities;
    }

    public void setClassifyDescEntities(List<ClassifyDescEntity> classifyDescEntities) {
        this.classifyDescEntities = classifyDescEntities;
    }

    public static class ClassifyDescEntity{
        private int id;
        private String imageUrl;
        private String typeName;
        private List<GoodsTypeEntity> goodsTypeEntities;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public List<GoodsTypeEntity> getGoodsTypeEntities() {
            return goodsTypeEntities;
        }

        public void setGoodsTypeEntities(List<GoodsTypeEntity> goodsTypeEntities) {
            this.goodsTypeEntities = goodsTypeEntities;
        }
    }

    public static class GoodsTypeEntity{
        private int id;
        private String goodsImageUrl;
        private String goodsName;

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
    }

}
