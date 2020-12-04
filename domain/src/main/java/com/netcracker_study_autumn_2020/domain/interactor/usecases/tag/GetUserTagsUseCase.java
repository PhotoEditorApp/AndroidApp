package com.netcracker_study_autumn_2020.domain.interactor.usecases.tag;

import java.util.List;

public interface GetUserTagsUseCase {
    interface Callback {
        void onUserTagsLoaded(List<String> tags);

        void onError(Exception e);
    }

    void execute(long userId, GetUserTagsUseCase.Callback callback);
}
