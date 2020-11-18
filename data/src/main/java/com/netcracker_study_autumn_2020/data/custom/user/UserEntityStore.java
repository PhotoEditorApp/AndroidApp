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

    void getUserById(long userId, UserByIdCallback callback);

    void getUserByEmail(String email, UserByEmailCallback callback);

    void getUsersByFullName(String fullName, UsersByNameCallback callback);
    void createUserProfile(UserEntity userEntity, UserCreateCallback callback);
    void editUserProfile(UserEntity userEntity, UserEditCallback callback);
}
