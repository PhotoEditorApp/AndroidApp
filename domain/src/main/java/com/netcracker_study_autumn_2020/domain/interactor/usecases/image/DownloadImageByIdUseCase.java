package com.netcracker_study_autumn_2020.domain.interactor.usecases.image;


public interface DownloadImageByIdUseCase {
    interface Callback {
        void onImageDownloaded(Object image);

        void onError(Exception e);
    }

    void execute(long imageId, DownloadImageByIdUseCase.Callback callback);
}
