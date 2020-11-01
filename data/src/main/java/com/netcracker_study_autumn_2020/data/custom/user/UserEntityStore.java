package com.netcracker_study_autumn_2020.data.custom.user;

import com.netcracker_study_autumn_2020.data.entity.UserEntity;

import java.util.List;

public interface UserEntityStore {
    interface Error{
        void onError(Exception e);
    }

    interface UserByIdCallback extends Error{
        void onUserLoaded(UserEntity userEntity);
    }

    interface UserByEmailCallback extends Error{
        void onUserLoaded(UserEntity userEntity);
    }

    interface UsersByNameCallback extends Error{
        void onUserLoaded(List<UserEntity> userEntity);
    }

    interface UserCreateCallback extends Error{
        void onUserLoaded();
    }

    interface UserEditCallback extends Error{
        void onUserLoaded();
    }

    void getUserById(int userId, UserByIdCallback callback);
    void getUsersByEmail(String email, UserByEmailCallback callback);
    void getUsersByName(String fullName, UsersByNameCallback callback);
    void createUserProfile(UserEntity userEntity, UserCreateCallback callback);
    void editUserProfile(UserEntity userEntity, UserEditCallback callback);
}
