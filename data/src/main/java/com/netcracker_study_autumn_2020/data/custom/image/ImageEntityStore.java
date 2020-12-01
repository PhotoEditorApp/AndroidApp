package com.netcracker_study_autumn_2020.data.custom.image;

import android.graphics.Bitmap;

import com.netcracker_study_autumn_2020.data.entity.ImageEntity;

import java.io.File;
import java.util.List;

public interface ImageEntityStore {
    interface Error {
        void onError(Exception e);
    }

    interface ImageDownloadByIdCallback extends ImageEntityStore.Error {
        void onImagesDownloaded(Bitmap image);
    }

    interface ImageUploadCallback extends ImageEntityStore.Error {
        void onImagesUploaded();
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

    void getImageById(long imageId, ImageDownloadByIdCallback callback);

    void uploadImage(long userId, long spaceId, File sourceImage,
                     ImageUploadCallback callback);

    void getImagesBySpaceId(long spaceId, ImagesBySpaceIdCallback callback);

    void editImageInfo(ImageEntity imageEntity, ImageEditCallback callback);

    void deleteImage(long imageId, ImageDeleteCallback callback);
}
