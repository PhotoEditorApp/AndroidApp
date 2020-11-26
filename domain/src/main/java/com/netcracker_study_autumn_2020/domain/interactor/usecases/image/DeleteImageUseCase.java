package com.netcracker_study_autumn_2020.domain.interactor.usecases.image;

public interface DeleteImageUseCase {
    interface Callback {
        void onImageDeleted();

        void onError(Exception e);
    }

    void execute(long spaceId, DeleteImageUseCase.Callback callback);
}
