package com.netcracker_study_autumn_2020.data.custom.services;

import com.netcracker_study_autumn_2020.data.entity.UserEntity;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @GET("/profile/{user_id}")
    Call<UserEntity> getUserById(@Path("user_id") long userId);

    @GET("")
    Call<UserEntity> getUserByEmail(String email);

    @GET("")
    Call<List<UserEntity>> getUsersByFullName(String fullName);

    @PUT("/profile/{user_id}")
    Call<ResponseBody> editUser(@Path("user_id") long userId,
                                @Body UserEntity userEntity);

}
