package com.pgg.yixiannonapp.domain.Classify;


public class ClassifyDescEntity {
    private int id;
    private String typeName;
    private int classifyTypeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassifyTypeId() {
        return classifyTypeId;
    }

    public void setClassifyTypeId(int classifyTypeId) {
        this.classifyTypeId = classifyTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
