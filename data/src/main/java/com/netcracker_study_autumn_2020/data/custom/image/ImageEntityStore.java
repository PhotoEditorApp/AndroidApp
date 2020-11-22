package com.netcracker_study_autumn_2020.data.custom.image;

import com.netcracker_study_autumn_2020.data.entity.ImageEntity;

import java.util.List;

public interface ImageEntityStore {
    interface Error {
        void onError(Exception e);
    }

    interface ImagesBySpaceIdCallback extends ImageEntityStore.Error {
        void onImagesLoaded(List<ImageEntity> imageEntityList);
    }

    void getImagesBySpaceId(long spaceId, ImagesBySpaceIdCallback callback);
}
