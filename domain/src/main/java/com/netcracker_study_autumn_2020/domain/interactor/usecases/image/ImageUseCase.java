package com.netcracker_study_autumn_2020.domain.interactor.usecases.image;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.Interactor;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;

public abstract class ImageUseCase implements Interactor {
    protected final ImageRepository imageRepository;
    protected final PostExecutionThread postExecutionThread;
    private final ThreadExecutor threadExecutor;

    protected ImageUseCase(ImageRepository imageRepository,
                           PostExecutionThread postExecutionThread,
                           ThreadExecutor threadExecutor) {
        if (imageRepository == null || threadExecutor == null || postExecutionThread == null) {
            try {
                throw new IllegalAccessException("Use Case cannot receive null values!");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        this.imageRepository = imageRepository;
        this.postExecutionThread = postExecutionThread;
        this.threadExecutor = threadExecutor;
    }

    protected void execute() {
        this.threadExecutor.execute(this);
    }
}
