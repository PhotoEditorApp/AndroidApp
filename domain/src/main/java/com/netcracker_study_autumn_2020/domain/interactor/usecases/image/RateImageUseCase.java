package com.netcracker_study_autumn_2020.domain.interactor.usecases.image;

public interface RateImageUseCase {
    interface Callback {
        void onImageRated();

        void onError(Exception e);
    }

    void execute(long userId, long imageId, int rating,
                 RateImageUseCase.Callback callback);
}
