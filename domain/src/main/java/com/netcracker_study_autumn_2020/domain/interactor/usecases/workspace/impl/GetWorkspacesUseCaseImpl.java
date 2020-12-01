package com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.impl;

import com.netcracker_study_autumn_2020.domain.dto.WorkspaceDto;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.GetWorkspacesUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.WorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.repository.WorkspaceRepository;

import java.util.List;

public class GetWorkspacesUseCaseImpl extends WorkspaceUseCase implements GetWorkspacesUseCase {

    private GetWorkspacesUseCase.Callback callback;
    private long userId;
    private String accessType;

    public GetWorkspacesUseCaseImpl(WorkspaceRepository workspaceRepository,
                                    PostExecutionThread postExecutionThread,
                                    ThreadExecutor threadExecutor) {
        super(workspaceRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.workspaceRepository.allWorkspaces(userId, accessType, repositoryCallback);
    }

    @Override
    public void execute(long userId, String accessType, GetWorkspacesUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("GetWorkspacesUseCase: Invalid callback!");
        }
        super.execute();
        this.userId = userId;
        this.accessType = accessType;
        this.callback = callback;

    }

    private final WorkspaceRepository.WorkspaceListCallback repositoryCallback =
            new WorkspaceRepository.WorkspaceListCallback() {
                @Override
                public void onWorkspaceListLoaded(List<WorkspaceDto> workspaces) {
                    notifyGetWorkspacesSuccess(workspaces);
                }

                @Override
                public void onError(Exception e) {
                    notifyError(e);
                }
            };

    private void notifyGetWorkspacesSuccess(List<WorkspaceDto> workspaces){
        this.postExecutionThread.post(() -> callback.onWorkspaceCreated(workspaces));
    }

    private  void notifyError(Exception e){
        this.postExecutionThread.post(() -> callback.onError(e));
    }

}
