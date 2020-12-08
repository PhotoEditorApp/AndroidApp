package com.netcracker_study_autumn_2020.presentation.mvp.model;

import java.util.Date;

public class ImageModel {

    private long id;
    private String name;
    private long userId;
    private String path;
    private String previewPath;
    private Date createTime;
    private float rating;
    private int averageColor;
    private long size;

    private Date modifiedTime;

    //private List<ImageTagModel> tags;
    //private List<AverageColorModel> mainColors;


    public ImageModel() {
    }

    public ImageModel(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getAverageColor() {
        return averageColor;
    }

    public void setAverageColor(int averageColor) {
        this.averageColor = averageColor;
    }

    public String getPreviewPath() {
        return previewPath;
    }

    public void setPreviewPath(String previewPath) {
        this.previewPath = previewPath;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
