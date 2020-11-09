package com.netcracker_study_autumn_2020.domain.repository;

import com.netcracker_study_autumn_2020.domain.dto.UserDto;
import com.netcracker_study_autumn_2020.domain.dto.WorkspaceDto;

import java.util.List;

public interface UserRepository {

    interface Error{
        void onError(Exception e);
    }

    interface UserByIdCallback extends UserRepository.Error {
        void onUserLoaded(UserDto user);
    }

    interface UsersByFullNameListCallback extends UserRepository.Error {
        void onUsersByEmailListLoaded(List<UserDto> users);
    }

    interface UserByEmailCallback extends UserRepository.Error {
        void onUserLoaded(UserDto userDto);
    }


    interface UserEditCallback extends UserRepository.Error {
        void onUserEdited();
    }

    void getUserById(long userId, UserRepository.UserByIdCallback callback);
    void getUsersByFullName(String fullName, UserRepository.UsersByFullNameListCallback callback);
    void getUserByEmail(String email, UserRepository.UserByEmailCallback callback);
    void editUser(UserDto user, UserRepository.UserEditCallback callback);

}
