package com.netcracker_study_autumn_2020.data.auth_manager.impl.custom_backend_auth_usecases;

import com.netcracker_study_autumn_2020.data.auth_manager.impl.AuthInteractor;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;

public abstract class CustomAuthUseCase implements AuthInteractor {
    protected final PostExecutionThread postExecutionThread;
    private final ThreadExecutor threadExecutor;

    protected CustomAuthUseCase(ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread) {
        if (threadExecutor == null || postExecutionThread == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null");
        }

        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected void execute() {
        this.threadExecutor.execute(this);
    }
}
