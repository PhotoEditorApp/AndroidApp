package com.netcracker_study_autumn_2020.presentation.mvp.model;

public class SpaceAccessModel {
    private long userId;
    private long spaceId;
    private String type;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(long spaceId) {
        this.spaceId = spaceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
