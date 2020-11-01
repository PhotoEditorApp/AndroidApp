package com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace;

import com.netcracker_study_autumn_2020.domain.dto.WorkspaceDto;

public interface EditWorkspaceUseCase {
    interface Callback{
        void onWorkspaceEdited();
        void onError(Exception e);
    }

    void execute(WorkspaceDto userDto, EditWorkspaceUseCase.Callback callback);
}
