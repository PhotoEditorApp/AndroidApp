package com.netcracker_study_autumn_2020.data.repository;

import com.netcracker_study_autumn_2020.domain.dto.UserDto;
import com.netcracker_study_autumn_2020.domain.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public void getUserById(long userId, UserByIdCallback callback) {

    }

    @Override
    public void getUsersByFullName(String fullName, UsersByFullNameListCallback callback) {

    }

    @Override
    public void getUserByEmail(String email, UserByEmailCallback callback) {

    }

    @Override
    public void editUser(UserDto user, UserEditCallback callback) {

    }
}
