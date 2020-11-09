package com.netcracker_study_autumn_2020.domain.interactor.usecases.user;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.Interactor;
import com.netcracker_study_autumn_2020.domain.repository.UserRepository;
import com.netcracker_study_autumn_2020.domain.repository.WorkspaceRepository;

public abstract class UserUseCase implements Interactor {

    protected final UserRepository userRepository;
    protected final PostExecutionThread postExecutionThread;
    private final ThreadExecutor threadExecutor;

    protected UserUseCase(UserRepository userRepository,
                               PostExecutionThread postExecutionThread,
                               ThreadExecutor threadExecutor){
        if (userRepository == null || threadExecutor == null || postExecutionThread == null){
            try {
                throw new IllegalAccessException("Use Case cannot receive null values!");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        this.userRepository = userRepository;
        this.postExecutionThread = postExecutionThread;
        this.threadExecutor = threadExecutor;
    }

    protected void execute(){
        this.threadExecutor.execute(this);
    }

}
