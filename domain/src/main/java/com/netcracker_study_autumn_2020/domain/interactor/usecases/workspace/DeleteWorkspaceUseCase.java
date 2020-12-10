package com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace;

public interface DeleteWorkspaceUseCase {
    interface Callback{
        void onWorkspaceDeleted();
        void onError(Exception e);
    }

    void execute(long userId, long workspaceId, DeleteWorkspaceUseCase.Callback callback);
}
