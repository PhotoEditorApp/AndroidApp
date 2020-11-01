package com.netcracker_study_autumn_2020.presentation.mvp.model;

import android.graphics.Color;

import java.sql.Timestamp;
import java.util.Random;

public class WorkspaceModel {
    private int id;
    private int ownerId;
    private int color;
    private String name;
    private String description;
    private Timestamp creationTime;
    private Timestamp lastModified;

    public WorkspaceModel(
            int ownerId
    ){
        this.ownerId = ownerId;
        Random rnd = new Random();
        color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public int getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }
}
