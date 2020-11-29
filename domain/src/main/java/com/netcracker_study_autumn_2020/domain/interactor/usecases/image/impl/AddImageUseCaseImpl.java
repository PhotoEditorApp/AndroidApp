package com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.AddImageUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ImageUseCase;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;

import java.io.File;

public class AddImageUseCaseImpl extends ImageUseCase
        implements AddImageUseCase {
    private AddImageUseCase.Callback callback;
    private long userId;
    private long spaceId;
    private File sourceFile;

    public AddImageUseCaseImpl(ImageRepository imageRepository,
                               PostExecutionThread postExecutionThread,
                               ThreadExecutor threadExecutor) {
        super(imageRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.imageRepository.addImage(userId, spaceId, sourceFile, repositoryCallback);
    }

    @Override
    public void execute(long spaceId, long userId, File sourceImage, AddImageUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("DeleteImageUseCase: Invalid callback!");
        }
        super.execute();
        this.sourceFile = sourceImage;
        this.spaceId = spaceId;
        this.userId = userId;
        this.callback = callback;
    }

    private final ImageRepository.ImageUploadCallback repositoryCallback = new ImageRepository.ImageUploadCallback() {
        @Override
        public void onImagesUploaded() {
            notifyUploadImageSuccess();
        }

        @Override
        public void onError(Exception e) {
            notifyError(e);
        }
    };


    private void notifyUploadImageSuccess() {
        this.postExecutionThread.post(() -> callback.onImageAdded());
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
