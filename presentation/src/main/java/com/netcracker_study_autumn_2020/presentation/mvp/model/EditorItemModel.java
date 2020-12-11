package com.netcracker_study_autumn_2020.presentation.mvp.model;

public class EditorItemModel {
    public enum EditorItemType {
        FILTER, FRAME
    }

    private EditorItemType itemType;
    private long id;
    private String title;
    private int imageResourceId;

    //Frame constructor
    public EditorItemModel(long frameId, String frameName) {
        itemType = EditorItemType.FRAME;
        this.id = frameId;
        this.title = frameName;
    }

    //Filter constructor
    public EditorItemModel(String filterName, int imageResourceId) {
        itemType = EditorItemType.FILTER;
        this.title = filterName;
        this.imageResourceId = imageResourceId;
    }

    public EditorItemType getItemType() {
        return itemType;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
