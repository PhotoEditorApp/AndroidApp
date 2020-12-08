package com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.CreateCollageUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ImageUseCase;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;

public class CreateCollageUseCaseImpl extends ImageUseCase implements CreateCollageUseCase {
    private CreateCollageUseCase.Callback callback;
    private long[] imageIds;

    public CreateCollageUseCaseImpl(ImageRepository imageRepository,
                                    PostExecutionThread postExecutionThread,
                                    ThreadExecutor threadExecutor) {
        super(imageRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.imageRepository.createCollage(imageIds, repositoryCallback);
    }

    @Override
    public void execute(long[] imageIds, CreateCollageUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("CreateCollageUseCase: Invalid callback!");
        }
        super.execute();
        this.imageIds = imageIds;
        this.callback = callback;
    }

    private final ImageRepository.CollageGetCallback repositoryCallback = new ImageRepository.CollageGetCallback() {
        @Override
        public void onCollageCreated() {
            notifyCreateCollageSuccess();
        }

        @Override
        public void onError(Exception e) {
            notifyError(e);
        }
    };

    private void notifyCreateCollageSuccess() {
        this.postExecutionThread.post(() -> callback.onCollageCreated());
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
