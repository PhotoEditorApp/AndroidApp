package com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl;


import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.DownloadImageByIdUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ImageUseCase;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;

public class DownloadImageByIdUseCaseImpl extends ImageUseCase
        implements DownloadImageByIdUseCase {
    private DownloadImageByIdUseCase.Callback callback;
    private long imageId;

    public DownloadImageByIdUseCaseImpl(ImageRepository imageRepository,
                                        PostExecutionThread postExecutionThread,
                                        ThreadExecutor threadExecutor) {
        super(imageRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.imageRepository.downloadImage(imageId, repositoryCallback);
    }

    @Override
    public void execute(long imageId, DownloadImageByIdUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("DownloadImageUseCase: Invalid callback!");
        }
        super.execute();
        this.imageId = imageId;
        this.callback = callback;
    }

    private final ImageRepository.ImageDownloadById repositoryCallback = new ImageRepository.ImageDownloadById() {
        @Override
        public void onImageDownloaded(Object bitmap) {
            notifyDownloadImageSuccess(bitmap);
        }

        @Override
        public void onError(Exception e) {
            notifyError(e);
        }
    };

    private void notifyDownloadImageSuccess(Object bitmap) {
        this.postExecutionThread.post(() -> callback.onImageDownloaded(bitmap));
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
