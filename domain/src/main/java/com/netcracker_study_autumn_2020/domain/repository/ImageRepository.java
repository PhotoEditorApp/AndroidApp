package com.netcracker_study_autumn_2020.domain.repository;

import com.netcracker_study_autumn_2020.domain.dto.ImageDto;

import java.util.List;

public interface ImageRepository {

    interface Error {
        void onError(Exception e);
    }

    interface ImageBySpaceIdCallback extends ImageRepository.Error {
        void onImagesLoaded(List<ImageDto> imageDtos);
    }

    void getImageBySpaceId(long spaceId,
                           ImageRepository.ImageBySpaceIdCallback callback);
}
