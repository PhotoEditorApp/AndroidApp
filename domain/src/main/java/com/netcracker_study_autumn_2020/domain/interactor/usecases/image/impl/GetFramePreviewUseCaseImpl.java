package com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.GetFramePreviewUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ImageUseCase;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;

public class GetFramePreviewUseCaseImpl extends ImageUseCase implements GetFramePreviewUseCase {
    protected GetFramePreviewUseCaseImpl(ImageRepository imageRepository, PostExecutionThread postExecutionThread, ThreadExecutor threadExecutor) {
        super(imageRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {

    }
}
