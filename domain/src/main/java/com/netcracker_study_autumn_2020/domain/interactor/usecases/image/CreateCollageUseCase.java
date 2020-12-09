package com.netcracker_study_autumn_2020.domain.interactor.usecases.image;

import java.util.List;

public interface CreateCollageUseCase {
    interface Callback {
        void onCollageCreated();

        void onError(Exception e);
    }

    void execute(List<Long> imageIds, CreateCollageUseCase.Callback callback);
}
