package com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.impl;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.DeleteWorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.WorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.repository.WorkspaceRepository;

public class DeleteWorkspaceUseCaseImpl extends WorkspaceUseCase implements DeleteWorkspaceUseCase {
    private DeleteWorkspaceUseCase.Callback callback;
    private long workspaceId;
    private long userId;

    public DeleteWorkspaceUseCaseImpl(WorkspaceRepository workspaceRepository,
                                      PostExecutionThread postExecutionThread,
                                      ThreadExecutor threadExecutor) {
        super(workspaceRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.workspaceRepository.deleteWorkspace(userId, workspaceId, repositoryCallback);
    }

    @Override
    public void execute(long userId, long workspaceId, DeleteWorkspaceUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("DeleteWorkspaceUseCase: Invalid callback!");
        }
        super.execute();
        this.workspaceId = workspaceId;
        this.userId = userId;
        this.callback = callback;

    }

    private final WorkspaceRepository.WorkspaceDeleteCallback repositoryCallback =
            new WorkspaceRepository.WorkspaceDeleteCallback() {
                @Override
                public void onWorkspaceDeleted() {
                    notifyDeleteWorkspaceSuccess();
                }

                @Override
                public void onError(Exception e) {
                    notifyError(e);
                }

            };

    private void notifyDeleteWorkspaceSuccess(){
        this.postExecutionThread.post(() -> callback.onWorkspaceDeleted());
    }

    private  void notifyError(Exception e){
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
