package com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ApplyFilterUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ImageUseCase;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;

public class ApplyFilterUseCaseImpl extends ImageUseCase implements ApplyFilterUseCase {
    private ApplyFilterUseCase.Callback callback;
    private long imageId;
    private String filter;

    public ApplyFilterUseCaseImpl(ImageRepository imageRepository,
                                  PostExecutionThread postExecutionThread,
                                  ThreadExecutor threadExecutor) {
        super(imageRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.imageRepository.applyFilter(imageId, filter, repositoryCallback);
    }

    @Override
    public void execute(long imageId, String filter, ApplyFilterUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("ApplyFilterUseCase: Invalid callback!");
        }
        super.execute();
        this.imageId = imageId;
        this.filter = filter;
        this.callback = callback;
    }

    private final ImageRepository.ImageApplyFilterCallback repositoryCallback = new ImageRepository.ImageApplyFilterCallback() {
        @Override
        public void onFilterApplied(Object image) {
            notifyApplyFilterSuccess(image);
        }

        @Override
        public void onError(Exception e) {
            notifyError(e);
        }
    };


    private void notifyApplyFilterSuccess(Object image) {
        this.postExecutionThread.post(() -> callback.onFilterApplied(image));
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
