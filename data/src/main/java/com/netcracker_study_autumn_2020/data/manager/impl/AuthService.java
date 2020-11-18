package com.netcracker_study_autumn_2020.data.manager.impl;

import com.netcracker_study_autumn_2020.library.UserCredentials;
import com.netcracker_study_autumn_2020.library.UserSessionValues;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthService {

    @POST("user/signup")
    Call<ResponseBody> signUp(@Query("email") String email,
                              @Query("password") String password);

    @POST("login")
    Call<UserSessionValues> signIn(@Body UserCredentials credentials);
}
