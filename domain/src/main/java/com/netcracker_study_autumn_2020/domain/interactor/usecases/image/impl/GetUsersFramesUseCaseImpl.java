package com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl;

import com.netcracker_study_autumn_2020.domain.dto.FrameDto;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.GetUsersFramesUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ImageUseCase;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;

import java.util.List;

public class GetUsersFramesUseCaseImpl extends ImageUseCase implements GetUsersFramesUseCase {
    private GetUsersFramesUseCase.Callback callback;

    public GetUsersFramesUseCaseImpl(ImageRepository imageRepository,
                                     PostExecutionThread postExecutionThread,
                                     ThreadExecutor threadExecutor) {
        super(imageRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.imageRepository.getUsersFrames(repositoryCallback);
    }

    @Override
    public void execute(GetUsersFramesUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("GetUsersFramesUseCase: Invalid callback!");
        }
        super.execute();
        this.callback = callback;
    }

    private final ImageRepository.UsersFramesGetCallback repositoryCallback = new ImageRepository.UsersFramesGetCallback() {
        @Override
        public void onUsersFramesLoaded(List<FrameDto> usersFrames) {
            notifyGetUsersFramesSuccess(usersFrames);
        }

        @Override
        public void onError(Exception e) {
            notifyError(e);
        }
    };


    private void notifyGetUsersFramesSuccess(List<FrameDto> usersFrames) {
        this.postExecutionThread.post(() -> callback.onUsersFramesLoaded(usersFrames));
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
