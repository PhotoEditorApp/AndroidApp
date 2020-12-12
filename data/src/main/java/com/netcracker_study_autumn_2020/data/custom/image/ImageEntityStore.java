package com.netcracker_study_autumn_2020.data.custom.image;

import android.graphics.Bitmap;

import com.netcracker_study_autumn_2020.data.entity.ImageEntity;

import java.io.File;
import java.util.List;

public interface ImageEntityStore {
    interface Error {
        void onError(Exception e);
    }

    interface CollageCreateCallback extends ImageEntityStore.Error {
        void onCollageCreated();
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

    interface ImageRateCallback extends ImageEntityStore.Error {
        void onImageRated();
    }

    interface ImageApplyFrameCallback extends ImageEntityStore.Error {
        void onFrameApplied(Bitmap image);
    }

    interface ImageApplyFilterCallback extends ImageEntityStore.Error {
        void onFilterApplied(Bitmap image);
    }

    interface FrameUploadCallback extends ImageEntityStore.Error {
        void onFrameUploaded();
    }

    interface FrameGetPreviewCallback extends ImageEntityStore.Error {
        void onFramePreviewLoaded();
    }

    interface UsersFramesGetCallback extends ImageEntityStore.Error {
        void onUsersFramesLoaded(List<Long> usersFrames);
    }


    void getImageById(long imageId, ImageDownloadByIdCallback callback);

    void getCollage(List<Long> imageIds, CollageCreateCallback callback);

    void uploadImage(long userId, long spaceId, File sourceImage,
                     ImageUploadCallback callback);

    void getUsersFrames(UsersFramesGetCallback callback);

    void getFramePreview(long frameId, FrameGetPreviewCallback callback);

    void uploadFrame(File sourceFrame, FrameUploadCallback callback);

    void applyFilter(long imageId, String filter, ImageApplyFilterCallback callback);

    void applyFrame(long imageId, long frameId, ImageApplyFrameCallback callback);

    void getImagesBySpaceId(long spaceId, ImagesBySpaceIdCallback callback);

    void editImageInfo(ImageEntity imageEntity, ImageEditCallback callback);

    void rateImage(long userId, long imageId, int ratingNumber, ImageRateCallback callback);

    void deleteImage(long imageId, ImageDeleteCallback callback);
}
