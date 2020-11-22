package com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl;

import com.netcracker_study_autumn_2020.domain.dto.ImageDto;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.GetWorkspaceImagesInfoUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ImageUseCase;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;

import java.util.List;

public class GetWorkspaceImagesInfoUseCaseImpl extends ImageUseCase
        implements GetWorkspaceImagesInfoUseCase {
    private GetWorkspaceImagesInfoUseCase.Callback callback;
    private long spaceId;

    public GetWorkspaceImagesInfoUseCaseImpl(ImageRepository imageRepository,
                                             PostExecutionThread postExecutionThread,
                                             ThreadExecutor threadExecutor) {
        super(imageRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.imageRepository.getImageBySpaceId(spaceId, repositoryCallback);
    }

    @Override
    public void execute(long spaceId, GetWorkspaceImagesInfoUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("GetWorkspacesUseCase: Invalid callback!");
        }
        super.execute();
        this.spaceId = spaceId;
        this.callback = callback;

    }

    private final ImageRepository.ImageBySpaceIdCallback repositoryCallback =
            new ImageRepository.ImageBySpaceIdCallback() {
                @Override
                public void onImagesLoaded(List<ImageDto> imageDtos) {
                    notifyGetImagesSuccess(imageDtos);
                }

                @Override
                public void onError(Exception e) {
                    notifyError(e);
                }
            };

    private void notifyGetImagesSuccess(List<ImageDto> images) {
        this.postExecutionThread.post(() -> callback.onImagesLoaded(images));
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
