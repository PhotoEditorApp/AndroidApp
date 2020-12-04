package com.netcracker_study_autumn_2020.domain.interactor.usecases.tag;

import java.util.List;

public interface GetImageTagsUseCase {
    interface Callback {
        void onImageTagsLoaded(List<String> tags);

        void onError(Exception e);
    }

    void execute(long imageId, GetImageTagsUseCase.Callback callback);
}
