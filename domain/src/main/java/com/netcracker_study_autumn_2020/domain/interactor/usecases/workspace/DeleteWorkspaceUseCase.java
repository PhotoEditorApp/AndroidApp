package com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace;

import com.netcracker_study_autumn_2020.domain.dto.WorkspaceDto;

public interface DeleteWorkspaceUseCase {
    interface Callback{
        void onWorkspaceDeleted();
        void onError(Exception e);
    }

    void execute(int workspaceId, DeleteWorkspaceUseCase.Callback callback);
}
