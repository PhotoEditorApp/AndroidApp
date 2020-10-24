package com.netcracker_study_autumn_2020.data.manager.impl;

import com.netcracker_study_autumn_2020.data.entity.UserEntity;
import com.netcracker_study_autumn_2020.data.entity.api.response.SignInResult;
import com.netcracker_study_autumn_2020.library.UserCredentials;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("users/signup")
    Call<ResponseBody> signUp(@Body UserEntity user);

    @POST("login")
    Call<ResponseBody> signIn(@Body UserCredentials credentials);
}
