package com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.AddFrameUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ImageUseCase;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;

import java.io.File;

public class AddFrameUseCaseImpl extends ImageUseCase implements AddFrameUseCase {
    private AddFrameUseCase.Callback callback;
    private File sourceFile;

    public AddFrameUseCaseImpl(ImageRepository imageRepository,
                               PostExecutionThread postExecutionThread,
                               ThreadExecutor threadExecutor) {
        super(imageRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.imageRepository.uploadFrame(sourceFile, repositoryCallback);
    }

    @Override
    public void execute(File sourceImage, AddFrameUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("DeleteImageUseCase: Invalid callback!");
        }
        super.execute();
        this.sourceFile = sourceImage;
        this.callback = callback;
    }

    private final ImageRepository.FrameUploadCallback repositoryCallback = new ImageRepository.FrameUploadCallback() {
        @Override
        public void onFrameUploaded() {
            notifyUploadFrameSuccess();
        }

        @Override
        public void onError(Exception e) {
            notifyError(e);
        }
    };


    private void notifyUploadFrameSuccess() {
        this.postExecutionThread.post(() -> callback.onFrameAdded());
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
