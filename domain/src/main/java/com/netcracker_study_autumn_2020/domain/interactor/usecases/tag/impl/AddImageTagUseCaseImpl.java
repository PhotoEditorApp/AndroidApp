package com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.impl;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.AddImageTagUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.TagUseCase;
import com.netcracker_study_autumn_2020.domain.repository.TagRepository;

public class AddImageTagUseCaseImpl extends TagUseCase
        implements AddImageTagUseCase {
    private AddImageTagUseCase.Callback callback;
    private long userId;
    private long imageId;
    private String tagName;

    public AddImageTagUseCaseImpl(TagRepository tagRepository,
                                  PostExecutionThread postExecutionThread,
                                  ThreadExecutor threadExecutor) {
        super(tagRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void execute(long userId, long imageId, String tagName, Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("AddImageTagUseCase: Invalid callback!");
        }
        this.userId = userId;
        this.imageId = imageId;
        this.tagName = tagName;
        this.callback = callback;
    }

    @Override
    public void run() {
        this.tagRepository.addImageTag(userId, imageId, tagName, repositoryCallback);
    }

    private TagRepository.ImageTagCreateCallback repositoryCallback = new TagRepository.ImageTagCreateCallback() {
        @Override
        public void onImageTagCreated() {
            notifyCreateImageTagSuccess();
        }

        @Override
        public void onError(Exception e) {
            notifyError(e);
        }
    };


    private void notifyCreateImageTagSuccess() {
        this.postExecutionThread.post(() -> callback.onImageTagAdded());
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
