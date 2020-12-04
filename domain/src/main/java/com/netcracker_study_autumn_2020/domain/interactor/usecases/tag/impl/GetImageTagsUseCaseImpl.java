package com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.impl;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.GetImageTagsUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.TagUseCase;
import com.netcracker_study_autumn_2020.domain.repository.TagRepository;

import java.util.List;

public class GetImageTagsUseCaseImpl extends TagUseCase
        implements GetImageTagsUseCase {
    private GetImageTagsUseCase.Callback callback;
    private long imageId;

    public GetImageTagsUseCaseImpl(TagRepository tagRepository,
                                   PostExecutionThread postExecutionThread,
                                   ThreadExecutor threadExecutor) {
        super(tagRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void execute(long imageId, GetImageTagsUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("GetImageTagsUseCase: Invalid callback!");
        }
        this.imageId = imageId;
        this.callback = callback;
    }

    @Override
    public void run() {
        this.tagRepository.getImageTags(imageId, repositoryCallback);
    }

    private TagRepository.ImageTagsGetCallback repositoryCallback = new TagRepository.ImageTagsGetCallback() {
        @Override
        public void onImageTagsLoaded(List<String> tags) {
            notifyGetImageTagsSuccess(tags);
        }

        @Override
        public void onError(Exception e) {
            notifyError(e);
        }
    };


    private void notifyGetImageTagsSuccess(List<String> tags) {
        this.postExecutionThread.post(() -> callback.onImageTagsLoaded(tags));
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
