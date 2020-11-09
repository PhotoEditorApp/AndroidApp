package com.netcracker_study_autumn_2020.data.custom.services;

import com.netcracker_study_autumn_2020.data.entity.UserEntity;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @GET("")
    Call<UserEntity> getUserById(long userId);

    @GET("")
    Call<UserEntity> getUserByEmail(String email);

    @GET("")
    Call<List<UserEntity>> getUsersByFullName(String fullName);

    @POST("")
    Call<ResponseBody> editUser(@Body UserEntity userEntity);

}
