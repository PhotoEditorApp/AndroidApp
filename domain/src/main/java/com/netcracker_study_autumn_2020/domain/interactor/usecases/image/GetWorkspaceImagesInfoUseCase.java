package com.netcracker_study_autumn_2020.domain.interactor.usecases.image;

import com.netcracker_study_autumn_2020.domain.dto.ImageDto;

import java.util.List;

public interface GetWorkspaceImagesInfoUseCase {
    interface Callback {
        void onImagesLoaded(List<ImageDto> images);

        void onError(Exception e);
    }

    void execute(long spaceId, GetWorkspaceImagesInfoUseCase.Callback callback);
}
