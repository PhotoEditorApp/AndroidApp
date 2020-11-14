package com.netcracker_study_autumn_2020.presentation.mvp.model;

import java.io.Serializable;
import java.util.Date;

public class WorkspaceModel implements Serializable {
    private long id;
    private long ownerId;
    private int color;
    private String name;
    private String description;
    private Date creationTime;
    private Date modificationTime;

    public WorkspaceModel() {
    }

    public WorkspaceModel(
            long ownerId,
            String name,
            String description,
            int color,
            Date creationTime,
            Date modificationTime
    ) {
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.creationTime = creationTime;
        this.modificationTime = modificationTime;
        this.color = color;
        //Random rnd = new Random();
        //color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
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

    public void setColor(int color) {
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setModificationTime(Date lastModified) {
        this.modificationTime = lastModified;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public Date getModificationTime() {
        return modificationTime;
    }
}
