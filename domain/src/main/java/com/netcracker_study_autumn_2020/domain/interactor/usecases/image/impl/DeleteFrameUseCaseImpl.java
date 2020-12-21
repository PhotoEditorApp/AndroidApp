package com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.DeleteFrameUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ImageUseCase;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;

public class DeleteFrameUseCaseImpl extends ImageUseCase
        implements DeleteFrameUseCase {
    private DeleteFrameUseCase.Callback callback;
    private long frameId;

    public DeleteFrameUseCaseImpl(ImageRepository imageRepository,
                                  PostExecutionThread postExecutionThread,
                                  ThreadExecutor threadExecutor) {
        super(imageRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.imageRepository.deleteFrame(frameId, repositoryCallback);
    }

    @Override
    public void execute(long frameId, DeleteFrameUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("DeleteFrameUseCase: Invalid callback!");
        }
        super.execute();
        this.frameId = frameId;
        this.callback = callback;

    }

    private final ImageRepository.FrameDeleteCallback repositoryCallback = new ImageRepository.FrameDeleteCallback() {
        @Override
        public void onFrameDeleted() {
            notifyDeleteFrameSuccess();
        }

        @Override
        public void onError(Exception e) {
            notifyError(e);
        }
    };


    private void notifyDeleteFrameSuccess() {
        this.postExecutionThread.post(() -> callback.onFrameDeleted());
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
