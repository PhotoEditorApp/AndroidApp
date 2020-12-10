package com.netcracker_study_autumn_2020.domain.interactor.usecases.image;

import com.netcracker_study_autumn_2020.domain.dto.FrameDto;

import java.util.List;

public interface GetUsersFramesUseCase {
    interface Callback {
        void onUsersFramesLoaded(List<FrameDto> usersFrames);

        void onError(Exception e);
    }

    void execute(GetUsersFramesUseCase.Callback callback);
}
