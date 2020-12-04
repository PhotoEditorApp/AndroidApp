package com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.impl;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.DeleteUserTagUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.TagUseCase;
import com.netcracker_study_autumn_2020.domain.repository.TagRepository;

public class DeleteUserTagUseCaseImpl extends TagUseCase
        implements DeleteUserTagUseCase {
    private DeleteUserTagUseCase.Callback callback;
    private long userId;
    private String tagName;

    public DeleteUserTagUseCaseImpl(TagRepository tagRepository,
                                    PostExecutionThread postExecutionThread,
                                    ThreadExecutor threadExecutor) {
        super(tagRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void execute(long userId, String tagName, DeleteUserTagUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("DeleteUserTagUseCase: Invalid callback!");
        }
        super.execute();
        this.userId = userId;
        this.tagName = tagName;
        this.callback = callback;
    }

    @Override
    public void run() {
        this.tagRepository.deleteUserTag(userId, tagName, repositoryCallback);
    }

    private TagRepository.UserTagDeleteCallback repositoryCallback = new TagRepository.UserTagDeleteCallback() {
        @Override
        public void onUserTagDeleted() {
            notifyDeleteUserTagSuccess();
        }

        @Override
        public void onError(Exception e) {
            notifyError(e);
        }
    };


    private void notifyDeleteUserTagSuccess() {
        this.postExecutionThread.post(() -> callback.onUserTagDeleted());
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
