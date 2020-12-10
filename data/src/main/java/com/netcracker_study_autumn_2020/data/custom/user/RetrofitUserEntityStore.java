package com.netcracker_study_autumn_2020.data.custom.user;

import android.util.Log;

import com.netcracker_study_autumn_2020.data.custom.services.UserService;
import com.netcracker_study_autumn_2020.data.entity.UserEntity;
import com.netcracker_study_autumn_2020.data.exception.EntityStoreException;
import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.library.network.NetworkUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;
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
    public void getUserById(long userId, UserByIdCallback callback) {
        Response<UserEntity> response;
        try {
            response = userService.getUserById(SessionManager.getSessionToken(),
                    userId).execute();
            if (response.body() == null) {
                callback.onError(new EntityStoreException("USER_ENTITY_STORE getUserProfile: code - " +
                        +response.code()));
            } else {
                callback.onUserLoaded(response.body());
            }
        } catch (IOException e) {
            callback.onError(e);
        }
        callback.onError(new EntityStoreException());
    }

    @Override
    public void getUserByEmail(String email, UserByEmailCallback callback) {
        Response<UserEntity> response;
        try {
            response = userService.getUserByEmail(SessionManager.getSessionToken(),
                    email).execute();
            if (response.body() == null) {
                callback.onError(new EntityStoreException("USER_ENTITY_STORE getUserProfile: code - " +
                        +response.code()));
            } else {
                callback.onUserLoaded(response.body());
            }
        } catch (IOException e) {
            callback.onError(e);
        }
        callback.onError(new EntityStoreException());

    }

    @Override
    public void getUsersByFullName(String fullName, UsersByNameCallback callback) {

    }

    @Override
    public void createUserProfile(UserEntity userEntity, UserCreateCallback callback) {

    }

    @Override
    public void editUserProfile(UserEntity userEntity, UserEditCallback callback) {
        Response<ResponseBody> response;
        try {
            response = userService.editUser(userEntity.getId(),
                    SessionManager.getSessionToken(),
                    userEntity).execute();
            if (response.code() == 200) {
                callback.onUserLoaded();
            } else {
                Log.w("USER_ENTITY_STORE", "editUserProfile: code - " +
                        +response.code());
                callback.onError(new EntityStoreException());
            }

        } catch (IOException e) {
            callback.onError(e);
        }
        callback.onError(new EntityStoreException());

    }
}
