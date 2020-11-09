package com.netcracker_study_autumn_2020.domain.interactor.usecases.user;

import com.netcracker_study_autumn_2020.domain.dto.UserDto;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.DeleteWorkspaceUseCase;

import java.util.List;

public interface GetUsersByFullNameUseCase {
    interface Callback{
        void onUsersFound(List<UserDto> users);
        void onError(Exception e);
    }

    void execute(String fullName, GetUsersByFullNameUseCase.Callback callback);
}
