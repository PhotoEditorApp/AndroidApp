package com.netcracker_study_autumn_2020.presentation.mvp.presenter;

import com.netcracker_study_autumn_2020.data.manager.AuthManager;
import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.domain.dto.UserDto;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.EditUserUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.GetUserByIdUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.UploadUserAvatarUseCase;
import com.netcracker_study_autumn_2020.presentation.mapper.UserModelDtoMapper;
import com.netcracker_study_autumn_2020.presentation.mvp.model.UserModel;
import com.netcracker_study_autumn_2020.presentation.mvp.view.UserProfileView;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.UserProfileFragment;

import java.io.File;

public class UserProfilePresenter extends BasePresenter {

    private UserProfileView userProfileView;

    private AuthManager authManager;
    private UserModel userModel;

    private GetUserByIdUseCase getUserByIdUseCase;
    private EditUserUseCase editUserUseCase;

    private UploadUserAvatarUseCase uploadUserAvatarUseCase;

    private UserModelDtoMapper userModelDtoMapper;

    public UserProfilePresenter(AuthManager authManager, GetUserByIdUseCase getUserByIdUseCase,
                                EditUserUseCase editUserUseCase,
                                UploadUserAvatarUseCase uploadUserAvatarUseCase,
                                UserModelDtoMapper userModelDtoMapper) {
        this.authManager = authManager;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.editUserUseCase = editUserUseCase;
        this.uploadUserAvatarUseCase = uploadUserAvatarUseCase;
        this.userModelDtoMapper = userModelDtoMapper;
    }

    public void setView(UserProfileView userProfileView) {
        this.userProfileView = userProfileView;
    }

    public void refreshUserProfile() {
        getUserByIdUseCase.execute(SessionManager.getCurrentUserId(),
                new GetUserByIdUseCase.Callback() {
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

    public void editUserProfile(UserModel editedUser) {
        UserDto userDto = userModelDtoMapper.map2(editedUser);
        editUserUseCase.execute(userDto, new EditUserUseCase.Callback() {
            @Override
            public void onUserEdited() {
                ((UserProfileFragment) userProfileView).showToastMessage(
                        "Данные успешно изменены", true);
                refreshUserProfile();
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

    public void uploadAvatar(File userAvatar) {
        uploadUserAvatarUseCase.execute(userAvatar, new UploadUserAvatarUseCase.Callback() {
            @Override
            public void onUserAvatarUploaded() {
                userProfileView.hideLoading();
                userProfileView.renderAvatar();
            }

            @Override
            public void onError(Exception e) {
                userProfileView.hideLoading();
                e.printStackTrace();
            }
        });
    }
}
