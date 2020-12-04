package com.netcracker_study_autumn_2020.domain.interactor.usecases.tag;

public interface AddImageTagUseCase {
    interface Callback {
        void onImageTagAdded();

        void onError(Exception e);
    }

    void execute(long userId, long imageId, String tagName,
                 AddImageTagUseCase.Callback callback);
}
