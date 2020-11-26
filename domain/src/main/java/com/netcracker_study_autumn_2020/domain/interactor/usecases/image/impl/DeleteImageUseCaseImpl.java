package com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.DeleteImageUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ImageUseCase;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;

public class DeleteImageUseCaseImpl extends ImageUseCase
        implements DeleteImageUseCase {
    private DeleteImageUseCase.Callback callback;
    private long spaceId;

    public DeleteImageUseCaseImpl(ImageRepository imageRepository,
                                  PostExecutionThread postExecutionThread,
                                  ThreadExecutor threadExecutor) {
        super(imageRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.imageRepository.deleteImage(spaceId, repositoryCallback);
    }

    @Override
    public void execute(long spaceId, DeleteImageUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("DeleteImageUseCase: Invalid callback!");
        }
        super.execute();
        this.spaceId = spaceId;
        this.callback = callback;

    }

    private final ImageRepository.ImageDeleteCallback repositoryCallback = new ImageRepository.ImageDeleteCallback() {
        @Override
        public void onImageDeleted() {
            notifyDeleteImageSuccess();
        }

        @Override
        public void onError(Exception e) {
            notifyError(e);
        }
    };


    private void notifyDeleteImageSuccess() {
        this.postExecutionThread.post(() -> callback.onImageDeleted());
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}