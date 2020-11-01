package com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace;

import com.netcracker_study_autumn_2020.domain.dto.WorkspaceDto;

import java.util.List;

public interface GetWorkspacesUseCase {
    interface Callback{
        void onWorkspaceCreated(List<WorkspaceDto> workspaces);
        void onError(Exception e);
    }

    void execute(int userId, GetWorkspacesUseCase.Callback callback);
}
