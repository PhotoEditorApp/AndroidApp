package com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace;

import com.netcracker_study_autumn_2020.domain.dto.UserDto;
import com.netcracker_study_autumn_2020.domain.dto.WorkspaceDto;

public interface CreateWorkspaceUseCase {
    interface Callback{
        void onWorkspaceCreated();
        void onError(Exception e);
    }

    void execute(WorkspaceDto userDto, Callback callback);
}
