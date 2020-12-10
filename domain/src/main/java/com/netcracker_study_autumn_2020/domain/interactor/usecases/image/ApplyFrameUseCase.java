package com.netcracker_study_autumn_2020.domain.interactor.usecases.image;

public interface ApplyFrameUseCase {
    interface Callback {
        void onFrameApplied(Object image);

        void onError(Exception e);
    }

    void execute(long imageId, long frameId,
                 ApplyFrameUseCase.Callback callback);
}
