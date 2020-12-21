package com.netcracker_study_autumn_2020.domain.interactor.usecases.image;

public interface DeleteFrameUseCase {
    interface Callback {
        void onFrameDeleted();

        void onError(Exception e);
    }

    void execute(long frameId, DeleteFrameUseCase.Callback callback);
}
