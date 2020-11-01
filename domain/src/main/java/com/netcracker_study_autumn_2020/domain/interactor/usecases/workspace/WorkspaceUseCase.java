package com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.Interactor;
import com.netcracker_study_autumn_2020.domain.repository.WorkspaceRepository;

public abstract class WorkspaceUseCase implements Interactor {
    protected final WorkspaceRepository workspaceRepository;
    protected final PostExecutionThread postExecutionThread;
    private final ThreadExecutor threadExecutor;

    protected WorkspaceUseCase(WorkspaceRepository workspaceRepository,
                               PostExecutionThread postExecutionThread,
                               ThreadExecutor threadExecutor){
        if (workspaceRepository == null || threadExecutor == null || postExecutionThread == null){
            try {
                throw new IllegalAccessException("Use Case cannot receive null values!");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        this.workspaceRepository = workspaceRepository;
        this.postExecutionThread = postExecutionThread;
        this.threadExecutor = threadExecutor;
    }

    protected void execute(){
        this.threadExecutor.execute(this);
    }
}
