package com.netcracker_study_autumn_2020.presentation.mvp.presenter;

import com.netcracker_study_autumn_2020.data.manager.AuthManager;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.EditUserUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.GetUserByIdUseCase;
import com.netcracker_study_autumn_2020.presentation.mvp.view.UserProfileView;

public class UserProfilePresenter {

    private UserProfileView userProfileView;

    private AuthManager authManager;

    private GetUserByIdUseCase getUserByIdUseCase;
    private EditUserUseCase editUserUseCase;

    public UserProfilePresenter(AuthManager authManager, GetUserByIdUseCase getUserByIdUseCase,
                                EditUserUseCase editUserUseCase) {
        this.authManager = authManager;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.editUserUseCase = editUserUseCase;
    }

    public void setView(UserProfileView userProfileView) {
        this.userProfileView = userProfileView;
    }
}
