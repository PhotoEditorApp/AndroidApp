package com.netcracker_study_autumn_2020.domain.repository;


import com.netcracker_study_autumn_2020.domain.dto.FrameDto;
import com.netcracker_study_autumn_2020.domain.dto.ImageDto;

import java.io.File;
import java.util.List;

public interface ImageRepository {

    interface Error {
        void onError(Exception e);
    }

    interface CollageGetCallback extends ImageRepository.Error {
        void onCollageCreated();
    }

    interface ImageDownloadByIdCallback extends ImageRepository.Error {
        void onImageDownloaded(Object bitmap);
    }

    interface ImageUploadCallback extends ImageRepository.Error {
        void onImagesUploaded();
    }

    interface ImageBySpaceIdCallback extends ImageRepository.Error {
        void onImagesLoaded(List<ImageDto> imageDtos);
    }

    interface ImageInfoEditCallback extends ImageRepository.Error {
        void onImageInfoEdited();
    }

    interface ImageDeleteCallback extends ImageRepository.Error {
        void onImageDeleted();
    }

    interface ImageRateCallback extends ImageRepository.Error {
        void onImageRated();
    }

    interface UsersFramesGetCallback extends ImageRepository.Error {
        void onUsersFramesLoaded(List<FrameDto> usersFrames);
    }

    interface FrameGetPreviewCallback extends ImageRepository.Error {
        void onFramePreviewLoaded();
    }

    interface FrameUploadCallback extends ImageRepository.Error {
        void onFrameUploaded();
    }

    interface ImageApplyFilterCallback extends ImageRepository.Error {
        void onFilterApplied(Object image);
    }

    interface ImageApplyFrameCallback extends ImageRepository.Error {
        void onFrameApplied(Object image);
    }

    void getUsersFrames(UsersFramesGetCallback callback);

    void getFramePreview(long frameId, FrameGetPreviewCallback callback);

    void uploadFrame(File sourceFrame, FrameUploadCallback callback);

    void applyFilter(long imageId, String filter, ImageApplyFilterCallback callback);

    void applyFrame(long imageId, long frameId, ImageApplyFrameCallback callback);

    void downloadImage(long imageId, ImageDownloadByIdCallback callback);

    void addImage(long userId, long spaceId, File sourceImage,
                  ImageRepository.ImageUploadCallback callback);

    void createCollage(List<Long> imageIds,
                       ImageRepository.CollageGetCallback callback);

    void getImageBySpaceId(long spaceId,
                           ImageRepository.ImageBySpaceIdCallback callback);

    void editImageInfo(ImageDto imageDto,
                       ImageRepository.ImageInfoEditCallback callback);

    void deleteImage(long imageId,
                     ImageRepository.ImageDeleteCallback callback);

    void rateImage(long userId, long imageId, int rating,
                   ImageRepository.ImageRateCallback callback);
}
