package com.netcracker_study_autumn_2020.domain.interactor.usecases.tag;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.Interactor;
import com.netcracker_study_autumn_2020.domain.repository.TagRepository;

public abstract class TagUseCase implements Interactor {
    protected final TagRepository tagRepository;
    protected final PostExecutionThread postExecutionThread;
    private final ThreadExecutor threadExecutor;

    protected TagUseCase(TagRepository tagRepository,
                         PostExecutionThread postExecutionThread,
                         ThreadExecutor threadExecutor) {
        if (tagRepository == null || threadExecutor == null || postExecutionThread == null) {
            try {
                throw new IllegalAccessException("Use Case cannot receive null values!");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        this.tagRepository = tagRepository;
        this.postExecutionThread = postExecutionThread;
        this.threadExecutor = threadExecutor;
    }

    protected void execute() {
        this.threadExecutor.execute(this);
    }
}

