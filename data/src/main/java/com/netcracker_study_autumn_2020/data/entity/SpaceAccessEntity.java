package com.netcracker_study_autumn_2020.data.entity;

import com.google.gson.annotations.SerializedName;

public class SpaceAccessEntity {
    @SerializedName("user_id")
    private long userId;
    @SerializedName("space_id")
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
