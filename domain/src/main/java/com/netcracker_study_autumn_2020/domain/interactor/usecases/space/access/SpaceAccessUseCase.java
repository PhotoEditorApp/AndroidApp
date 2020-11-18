package com.netcracker_study_autumn_2020.domain.interactor.usecases.space.access;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.Interactor;
import com.netcracker_study_autumn_2020.domain.repository.SpaceAccessRepository;

public abstract class SpaceAccessUseCase implements Interactor {
    protected final SpaceAccessRepository spaceAccessRepository;
    protected final PostExecutionThread postExecutionThread;
    private final ThreadExecutor threadExecutor;

    protected SpaceAccessUseCase(SpaceAccessRepository spaceAccessRepository,
                                 PostExecutionThread postExecutionThread,
                                 ThreadExecutor threadExecutor) {
        if (spaceAccessRepository == null || threadExecutor == null || postExecutionThread == null) {
            try {
                throw new IllegalAccessException("Use Case cannot receive null values!");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        this.spaceAccessRepository = spaceAccessRepository;
        this.postExecutionThread = postExecutionThread;
        this.threadExecutor = threadExecutor;
    }

    protected void execute() {
        this.threadExecutor.execute(this);
    }
}
