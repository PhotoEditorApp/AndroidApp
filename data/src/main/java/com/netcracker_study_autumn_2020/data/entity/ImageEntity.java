package com.netcracker_study_autumn_2020.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageEntity {
    private long id;
    private String name;
    @SerializedName("user_id")
    private long userId;
    private String path;
    @SerializedName("preview_path")
    private String previewPath;
    private String createTime;
    @SerializedName("average_color")
    private long averageColor;
    private float rating;
    private long size;
    private String modifiedTime;
    private List<String> tags;

    public ImageEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getAverageColor() {
        return averageColor;
    }

    public void setAverageColor(long averageColor) {
        this.averageColor = averageColor;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getPreviewPath() {
        return previewPath;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setPreviewPath(String previewPath) {
        this.previewPath = previewPath;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
