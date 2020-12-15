package com.netcracker_study_autumn_2020.data.repository;

import com.netcracker_study_autumn_2020.data.custom.user.UserEntityStore;
import com.netcracker_study_autumn_2020.data.custom.user.UserEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.entity.UserEntity;
import com.netcracker_study_autumn_2020.data.mapper.UserEntityDtoMapper;
import com.netcracker_study_autumn_2020.domain.dto.UserDto;
import com.netcracker_study_autumn_2020.domain.repository.UserRepository;

import java.io.File;

public class UserRepositoryImpl implements UserRepository {

    private static UserRepositoryImpl INSTANCE;


    public static synchronized UserRepositoryImpl getInstance(
            UserEntityStoreFactory userEntityStoreFactory,
            UserEntityDtoMapper userEntityDtoMapper) {
        if (INSTANCE == null) {
            INSTANCE = new UserRepositoryImpl(userEntityDtoMapper,
                    userEntityStoreFactory);
        }
        return INSTANCE;
    }

    private UserEntityDtoMapper userEntityDtoMapper;
    private UserEntityStoreFactory userEntityStoreFactory;

    public UserRepositoryImpl(UserEntityDtoMapper userEntityDtoMapper,
                              UserEntityStoreFactory userEntityStoreFactory) {
        this.userEntityDtoMapper = userEntityDtoMapper;
        this.userEntityStoreFactory = userEntityStoreFactory;
    }


    @Override
    public void uploadAvatar(File userAvatar, UserAvatarUploadCallback callback) {
        UserEntityStore userEntityStore = userEntityStoreFactory.create();

        userEntityStore.uploadAvatar(userAvatar, new UserEntityStore.AvatarUploadCallback() {
            @Override
            public void onAvatarUploaded() {
                callback.onUserAvatarUploaded();
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

    @Override
    public void getUserById(long userId, UserByIdCallback callback) {
        UserEntityStore userEntityStore = userEntityStoreFactory.create();

        userEntityStore.getUserById(userId, new UserEntityStore.UserByIdCallback() {
            @Override
            public void onUserLoaded(UserEntity userEntity) {
                UserDto userDto = userEntityDtoMapper.map2(userEntity);
                callback.onUserLoaded(userDto);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });

    }

    @Override
    public void getUsersByFullName(String fullName, UsersByFullNameListCallback callback) {

    }

    @Override
    public void getUserByEmail(String email, UserByEmailCallback callback) {
        UserEntityStore userEntityStore = userEntityStoreFactory.create();

        userEntityStore.getUserByEmail(email, new UserEntityStore.UserByEmailCallback() {
            @Override
            public void onUserLoaded(UserEntity userEntity) {
                UserDto userDto = userEntityDtoMapper.map2(userEntity);
                callback.onUserLoaded(userDto);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

    @Override
    public void editUser(UserDto user, UserEditCallback callback) {
        UserEntityStore userEntityStore = userEntityStoreFactory.create();
        UserEntity userEntity = userEntityDtoMapper.map1(user);
        userEntityStore.editUserProfile(userEntity, new UserEntityStore.UserEditCallback() {
            @Override
            public void onUserLoaded() {
                callback.onUserEdited();
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });

    }
}
