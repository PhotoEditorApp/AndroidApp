package com.netcracker_study_autumn_2020.domain.interactor.usecases.user;

import com.netcracker_study_autumn_2020.domain.dto.UserDto;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.DeleteWorkspaceUseCase;

public interface GetUserByIdUseCase {
    interface Callback{
        void onUserFound(UserDto userDto);
        void onError(Exception e);
    }

    void execute(long userId, GetUserByIdUseCase.Callback callback);
}
