package com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.impl;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.GetUserTagsUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.TagUseCase;
import com.netcracker_study_autumn_2020.domain.repository.TagRepository;

import java.util.List;

public class GetUserTagsUseCaseImpl extends TagUseCase
        implements GetUserTagsUseCase {
    private GetUserTagsUseCase.Callback callback;
    private long userId;

    public GetUserTagsUseCaseImpl(TagRepository tagRepository,
                                  PostExecutionThread postExecutionThread,
                                  ThreadExecutor threadExecutor) {
        super(tagRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void execute(long userId, GetUserTagsUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("GetUserTagsUseCase: Invalid callback!");
        }
        this.userId = userId;
        this.callback = callback;
    }

    @Override
    public void run() {
        this.tagRepository.getUserTags(userId, repositoryCallback);
    }

    private TagRepository.UserTagsGetCallback repositoryCallback = new TagRepository.UserTagsGetCallback() {
        @Override
        public void onUserTagsLoaded(List<String> tags) {
            notifyGetUserTagsSuccess(tags);
        }

        @Override
        public void onError(Exception e) {
            notifyError(e);
        }
    };


    private void notifyGetUserTagsSuccess(List<String> tags) {
        this.postExecutionThread.post(() -> callback.onUserTagsLoaded(tags));
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
