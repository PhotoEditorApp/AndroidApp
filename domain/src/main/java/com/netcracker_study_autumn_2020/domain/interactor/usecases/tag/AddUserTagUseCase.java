package com.netcracker_study_autumn_2020.domain.interactor.usecases.tag;

public interface AddUserTagUseCase {
    interface Callback {
        void onUserTagAdded();

        void onError(Exception e);
    }

    void execute(long userId, String tagName,
                 AddUserTagUseCase.Callback callback);
}