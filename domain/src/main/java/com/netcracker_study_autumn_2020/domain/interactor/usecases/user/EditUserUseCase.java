package com.netcracker_study_autumn_2020.domain.interactor.usecases.user;

import com.netcracker_study_autumn_2020.domain.dto.UserDto;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.DeleteWorkspaceUseCase;

public interface EditUserUseCase {
    interface Callback{
        void onUserEdited();
        void onError(Exception e);
    }

    void execute(UserDto userDto, EditUserUseCase.Callback callback);
}
