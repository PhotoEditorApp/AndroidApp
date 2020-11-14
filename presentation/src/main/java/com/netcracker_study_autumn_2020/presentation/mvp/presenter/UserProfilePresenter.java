package com.netcracker_study_autumn_2020.presentation.mvp.presenter;

import com.netcracker_study_autumn_2020.data.manager.AuthManager;
import com.netcracker_study_autumn_2020.domain.dto.UserDto;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.EditUserUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.GetUserByIdUseCase;
import com.netcracker_study_autumn_2020.presentation.mapper.UserModelDtoMapper;
import com.netcracker_study_autumn_2020.presentation.mvp.model.UserModel;
import com.netcracker_study_autumn_2020.presentation.mvp.view.UserProfileView;

public class UserProfilePresenter extends BasePresenter {

    private UserProfileView userProfileView;

    private AuthManager authManager;
    private UserModel userModel;

    private GetUserByIdUseCase getUserByIdUseCase;
    private EditUserUseCase editUserUseCase;

    private UserModelDtoMapper userModelDtoMapper;

    public UserProfilePresenter(AuthManager authManager, GetUserByIdUseCase getUserByIdUseCase,
                                EditUserUseCase editUserUseCase, UserModelDtoMapper userModelDtoMapper) {
        this.authManager = authManager;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.editUserUseCase = editUserUseCase;
        this.userModelDtoMapper = userModelDtoMapper;
    }

    public void setView(UserProfileView userProfileView) {
        this.userProfileView = userProfileView;
    }

    public void refreshUserProfile() {
        getUserByIdUseCase.execute(1, new GetUserByIdUseCase.Callback() {
            @Override
            public void onUserFound(UserDto userDto) {
                userModel = userModelDtoMapper.map1(userDto);
                userProfileView.renderData();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public UserModel getUserModel() {
        return userModel;
    }
}
