package com.netcracker_study_autumn_2020.presentation.mvp.model;

import android.graphics.Color;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Random;

public class WorkspaceModel implements Serializable {
    private long id;
    private long ownerId;
    private int color;
    private String name;
    private String description;
    private Timestamp creationTime;
    private Timestamp lastModified;

    public WorkspaceModel(
            long ownerId,
            String name,
            String description,
            Timestamp creationTime,
            Timestamp lastModified
    ){
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.creationTime = creationTime;
        this.lastModified = lastModified;
        Random rnd = new Random();
        color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public long getId() {
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

    public long getOwnerId() {
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
