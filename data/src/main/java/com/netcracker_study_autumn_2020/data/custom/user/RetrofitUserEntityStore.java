package com.netcracker_study_autumn_2020.data.custom.user;

import com.netcracker_study_autumn_2020.data.entity.UserEntity;
import com.netcracker_study_autumn_2020.data.custom.services.UserService;
import com.netcracker_study_autumn_2020.library.network.NetworkUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitUserEntityStore implements UserEntityStore {

    private UserService userService;

    public RetrofitUserEntityStore(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.API_ADDRESS)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
    }

    @Override
    public void getUserById(int userId, UserByIdCallback callback) {
    }

    @Override
    public void getUsersByEmail(String email, UserByEmailCallback callback) {

    }

    @Override
    public void getUsersByName(String fullName, UsersByNameCallback callback) {

    }

    @Override
    public void createUserProfile(UserEntity userEntity, UserCreateCallback callback) {

    }

    @Override
    public void editUserProfile(UserEntity userEntity, UserEditCallback callback) {

    }
}
