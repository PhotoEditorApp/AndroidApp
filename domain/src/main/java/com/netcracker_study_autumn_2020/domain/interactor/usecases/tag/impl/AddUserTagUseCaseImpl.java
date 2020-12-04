package com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.impl;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.AddUserTagUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.TagUseCase;
import com.netcracker_study_autumn_2020.domain.repository.TagRepository;

public class AddUserTagUseCaseImpl extends TagUseCase
        implements AddUserTagUseCase {
    private AddUserTagUseCase.Callback callback;
    private long userId;
    private String tagName;

    public AddUserTagUseCaseImpl(TagRepository tagRepository,
                                 PostExecutionThread postExecutionThread,
                                 ThreadExecutor threadExecutor) {
        super(tagRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void execute(long userId, String tagName, AddUserTagUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("AddUserTagUseCase: Invalid callback!");
        }
        super.execute();
        this.userId = userId;
        this.tagName = tagName;
        this.callback = callback;
    }

    @Override
    public void run() {
        this.tagRepository.addUserTag(userId, tagName, repositoryCallback);
    }

    private TagRepository.UserTagCreateCallback repositoryCallback = new TagRepository.UserTagCreateCallback() {
        @Override
        public void onUserTagCreated() {
            notifyCreateUserTagSuccess();
        }

        @Override
        public void onError(Exception e) {
            notifyError(e);
        }
    };


    private void notifyCreateUserTagSuccess() {
        this.postExecutionThread.post(() -> callback.onUserTagAdded());
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
