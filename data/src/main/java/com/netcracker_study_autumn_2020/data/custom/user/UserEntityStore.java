package com.netcracker_study_autumn_2020.data.custom.user;

import com.netcracker_study_autumn_2020.data.entity.UserEntity;

public interface UserEntityStore {

    void getUserById(int userId);
    void getUsersByEmail(String email);
    void getUserByName(String fullName);
    void createUserProfile(UserEntity userEntity);
    void editUserProfile(UserEntity userEntity);
}
