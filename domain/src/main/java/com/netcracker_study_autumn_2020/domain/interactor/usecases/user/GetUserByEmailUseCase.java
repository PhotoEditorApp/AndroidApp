package com.netcracker_study_autumn_2020.domain.interactor.usecases.user;

import com.netcracker_study_autumn_2020.domain.dto.UserDto;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.DeleteWorkspaceUseCase;

public interface GetUserByEmailUseCase {
    interface Callback{
        void onUserFound(UserDto user);
        void onError(Exception e);
    }

    void execute(String email, GetUserByEmailUseCase.Callback callback);
}
