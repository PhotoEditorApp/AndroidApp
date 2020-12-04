package com.netcracker_study_autumn_2020.domain.interactor.usecases.tag;

public interface DeleteImageTagUseCase {
    interface Callback {
        void onImageTagDeleted();

        void onError(Exception e);
    }

    void execute(long userId, long imageId, String tagName,
                 DeleteImageTagUseCase.Callback callback);
}
