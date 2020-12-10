package com.netcracker_study_autumn_2020.domain.interactor.usecases.image;


public interface ApplyFilterUseCase {
    interface Callback {
        void onFilterApplied(Object image);

        void onError(Exception e);
    }

    void execute(long imageId, String filter,
                 ApplyFilterUseCase.Callback callback);
}
