package com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.impl;

import com.netcracker_study_autumn_2020.domain.dto.WorkspaceDto;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.CreateWorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.WorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.repository.WorkspaceRepository;

public class CreateWorkspaceUseCaseImpl extends WorkspaceUseCase implements CreateWorkspaceUseCase {
    private CreateWorkspaceUseCase.Callback callback;
    private WorkspaceDto workspaceDto;

    public CreateWorkspaceUseCaseImpl(WorkspaceRepository workspaceRepository,
                                      PostExecutionThread postExecutionThread,
                                      ThreadExecutor threadExecutor) {
        super(workspaceRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.workspaceRepository.createWorkspace(workspaceDto, repositoryCallback);
    }

    @Override
    public void execute(WorkspaceDto workspaceDto, CreateWorkspaceUseCase.Callback callback) {
        if(callback == null){
            throw new IllegalArgumentException("CreateWorkspaceUseCase: Invalid callback!");
        }
        super.execute();
        this.workspaceDto = workspaceDto;
        this.callback = callback;

    }

    private final WorkspaceRepository.WorkspaceCreateCallback repositoryCallback =
            new WorkspaceRepository.WorkspaceCreateCallback() {
                @Override
                public void onWorkspaceCreated() {
                    notifyCreateWorkspaceSuccess();
                }

                @Override
                public void onError(Exception e) {
                    notifyError(e);
                }
            };

    private void notifyCreateWorkspaceSuccess(){
        this.postExecutionThread.post(() -> callback.onWorkspaceCreated());
    }

    private  void notifyError(Exception e){
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
