package com.netcracker_study_autumn_2020.domain.repository;

import com.netcracker_study_autumn_2020.domain.dto.ImageDto;

import java.io.File;
import java.util.List;

public interface ImageRepository {

    interface Error {
        void onError(Exception e);
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

    void addImage(long userId, long spaceId, File sourceImage,
                  ImageRepository.ImageUploadCallback callback);

    void getImageBySpaceId(long spaceId,
                           ImageRepository.ImageBySpaceIdCallback callback);

    void editImageInfo(ImageDto imageDto,
                       ImageRepository.ImageInfoEditCallback callback);

    void deleteImage(long imageId,
                     ImageRepository.ImageDeleteCallback callback);
}
