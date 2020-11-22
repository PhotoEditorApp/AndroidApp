package com.netcracker_study_autumn_2020.data.custom.image;

public class ImageEntityStoreFactory {
    public ImageEntityStore create() {
        ImageEntityStore imageEntityStore;

        imageEntityStore = new RetrofitImageEntityStore();
        return imageEntityStore;
    }
}
