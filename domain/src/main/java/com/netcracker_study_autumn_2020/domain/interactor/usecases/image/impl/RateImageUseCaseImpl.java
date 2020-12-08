package com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ImageUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.RateImageUseCase;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;

public class RateImageUseCaseImpl extends ImageUseCase
        implements RateImageUseCase {
    private RateImageUseCase.Callback callback;
    private long userId;
    private long imageId;
    private int rating;

    public RateImageUseCaseImpl(ImageRepository imageRepository,
                                PostExecutionThread postExecutionThread,
                                ThreadExecutor threadExecutor) {
        super(imageRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.imageRepository.rateImage(userId, imageId, rating
                , repositoryCallback);
    }

    @Override
    public void execute(long userId, long imageId, int rating,
                        RateImageUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("RateImageUseCase: Invalid callback!");
        }
        super.execute();
        this.userId = userId;
        this.imageId = imageId;
        this.rating = rating;
        this.callback = callback;

    }

    private final ImageRepository.ImageRateCallback repositoryCallback =
            new ImageRepository.ImageRateCallback() {
                @Override
                public void onImageRated() {
                    notifyRateImagesSuccess();
                }

                @Override
                public void onError(Exception e) {
                    notifyError(e);
                }
            };

    private void notifyRateImagesSuccess() {
        this.postExecutionThread.post(() -> callback.onImageRated());
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
