package com.netcracker_study_autumn_2020.domain.interactor.usecases.tag;

public interface DeleteUserTagUseCase {
    interface Callback {
        void onUserTagDeleted();

        void onError(Exception e);
    }

    void execute(long userId, String tagName,
                 DeleteUserTagUseCase.Callback callback);
}
