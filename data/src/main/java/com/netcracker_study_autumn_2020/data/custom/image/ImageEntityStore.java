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

    interface ImageEditCallback extends ImageEntityStore.Error {
        void onImageEdited();
    }

    interface ImageDeleteCallback extends ImageEntityStore.Error {
        void onImageDeleted();
    }

    void getImagesBySpaceId(long spaceId, ImagesBySpaceIdCallback callback);

    void editImageInfo(ImageEntity imageEntity, ImageEditCallback callback);

    void deleteImage(long imageId, ImageDeleteCallback callback);
}
