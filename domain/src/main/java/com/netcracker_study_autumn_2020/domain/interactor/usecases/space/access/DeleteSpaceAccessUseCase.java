package com.netcracker_study_autumn_2020.domain.interactor.usecases.space.access;

public interface DeleteSpaceAccessUseCase {
    interface Callback {
        void onSpaceAccessDeleted();

        void onError(Exception e);
    }

    void execute(long spaceAccessId, DeleteSpaceAccessUseCase.Callback callback);
}
