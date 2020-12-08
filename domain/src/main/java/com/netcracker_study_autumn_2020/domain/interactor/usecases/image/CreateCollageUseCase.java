package com.netcracker_study_autumn_2020.domain.interactor.usecases.image;

public interface CreateCollageUseCase {
    interface Callback {
        void onCollageCreated();

        void onError(Exception e);
    }

    void execute(long[] imageIds, CreateCollageUseCase.Callback callback);
}
