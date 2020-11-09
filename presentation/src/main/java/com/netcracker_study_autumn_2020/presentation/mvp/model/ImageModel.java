package com.netcracker_study_autumn_2020.presentation.mvp.model;

import java.sql.Timestamp;
import java.util.List;

public class ImageModel {

    private long id;
    private long ownerId;
    private long spaceId;
    private String url;
    private String iconUrl;
    private String name;
    private String description;
    private int size;
    private Timestamp creationTime;
    private Timestamp modifiedTime;
    //private List<ImageTagModel> tags;
    //private List<AverageColorModel> mainColors;

}
