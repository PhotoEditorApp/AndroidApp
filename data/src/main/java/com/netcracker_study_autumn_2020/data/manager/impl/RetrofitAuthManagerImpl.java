package com.netcracker_study_autumn_2020.data.manager.impl;

import android.util.Log;

import androidx.annotation.NonNull;

import com.netcracker_study_autumn_2020.data.entity.UserEntity;
import com.netcracker_study_autumn_2020.data.manager.AuthManager;
import com.netcracker_study_autumn_2020.library.UserCredentials;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.netcracker_study_autumn_2020.library.network.NetworkUtils.API_ADDRESS;

public class RetrofitAuthManagerImpl implements AuthManager {

    //Понадобится позднее для формирования запросов о пространствах пользователя
    //private static String currentUserId;

    //TODO удалить ненужные классы и реализации, которые появились
    // после внедрения Retrofit

    private static String sessionToken = "";
    private static boolean isSessionOpened = false;

    private AuthService retrofitAuthService;

    public RetrofitAuthManagerImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_ADDRESS)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAuthService = retrofit.create(AuthService.class);
    }


    @Override
    public void registerUser(String email, String username, String password, RegisterUserCallback signUpCallback) {
        Call<ResponseBody> result = retrofitAuthService.signUp(new UserEntity(email, username, password));
        result.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                signUpCallback.onRegisterFinished(response.code());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                signUpCallback.onError((Exception) t);
            }
        });
    }

    @Override
    public void signInWithEmailAndPassword(String login, String password, SignInWithEmailAndPasswordCallback signInCallback) {
        Call<ResponseBody> result = retrofitAuthService.signIn(new UserCredentials(login, password));
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.body() != null) {
                    try {
                        signInCallback.onSignInFinished(response.code(), response.body().string());
                    } catch (IOException e) {
                        signInCallback.onSignInFinished(response.code(), "");
                        e.printStackTrace();
                    }
                } else {
                    signInCallback.onSignInFinished(response.code(), "");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                signInCallback.onError((Exception) t);
            }
        });
    }

    @Override
    public void signOut(SignOutCallback signOut) {

    }

    @Override
    public String getSessionToken() {
        return sessionToken;
    }

    @Override
    public void openSession(@NonNull String sT) {
        if (!sT.isEmpty()) {
            sessionToken = sT;
            isSessionOpened = true;
        } else {
            Log.w("Warning:", "Auth manager got empty token. " +
                    "Session has not been opened.");
        }
    }

    @Override
    public void closeSession() {
        sessionToken = "";
        isSessionOpened = false;
    }

    @Override
    public boolean isSessionOpened() {
        return isSessionOpened;
    }

}
