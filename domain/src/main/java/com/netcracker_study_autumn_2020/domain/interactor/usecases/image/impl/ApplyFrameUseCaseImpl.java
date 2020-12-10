package com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ApplyFrameUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ImageUseCase;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;

public class ApplyFrameUseCaseImpl extends ImageUseCase implements ApplyFrameUseCase {
    private ApplyFrameUseCase.Callback callback;
    private long imageId;
    private long frameId;

    public ApplyFrameUseCaseImpl(ImageRepository imageRepository,
                                 PostExecutionThread postExecutionThread,
                                 ThreadExecutor threadExecutor) {
        super(imageRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.imageRepository.applyFrame(imageId, frameId, repositoryCallback);
    }

    @Override
    public void execute(long imageId, long frameId, ApplyFrameUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("ApplyFrameUseCase: Invalid callback!");
        }
        super.execute();
        this.imageId = imageId;
        this.frameId = frameId;
        this.callback = callback;
    }

    private final ImageRepository.ImageApplyFrameCallback repositoryCallback = new ImageRepository.ImageApplyFrameCallback() {
        @Override
        public void onFrameApplied(Object image) {
            notifyApplyFrameSuccess(image);
        }

        @Override
        public void onError(Exception e) {
            notifyError(e);
        }
    };


    private void notifyApplyFrameSuccess(Object image) {
        this.postExecutionThread.post(() -> callback.onFrameApplied(image));
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
