package com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.impl;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.DeleteImageTagUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.TagUseCase;
import com.netcracker_study_autumn_2020.domain.repository.TagRepository;

public class DeleteImageTagUseCaseImpl extends TagUseCase
        implements DeleteImageTagUseCase {
    private DeleteImageTagUseCase.Callback callback;
    private long userId;
    private long imageId;
    private String tagName;

    public DeleteImageTagUseCaseImpl(TagRepository tagRepository,
                                     PostExecutionThread postExecutionThread,
                                     ThreadExecutor threadExecutor) {
        super(tagRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void execute(long userId, long imageId, String tagName, DeleteImageTagUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("DeleteImageTagUseCase: Invalid callback!");
        }
        super.execute();
        this.userId = userId;
        this.imageId = imageId;
        this.tagName = tagName;
        this.callback = callback;
    }

    @Override
    public void run() {
        this.tagRepository.deleteImageTag(userId, imageId, tagName, repositoryCallback);
    }

    private TagRepository.ImageTagDeleteCallback repositoryCallback = new TagRepository.ImageTagDeleteCallback() {
        @Override
        public void onImageTagDeleted() {
            notifyDeleteImageTagSuccess();
        }

        @Override
        public void onError(Exception e) {
            notifyError(e);
        }
    };


    private void notifyDeleteImageTagSuccess() {
        this.postExecutionThread.post(() -> callback.onImageTagDeleted());
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
