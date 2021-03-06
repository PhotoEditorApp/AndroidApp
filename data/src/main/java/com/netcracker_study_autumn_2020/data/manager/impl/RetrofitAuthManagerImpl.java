package com.netcracker_study_autumn_2020.data.manager.impl;

import android.util.Log;

import androidx.annotation.NonNull;

import com.netcracker_study_autumn_2020.data.manager.AuthManager;
import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.library.UserCredentials;
import com.netcracker_study_autumn_2020.library.UserSessionValues;
import com.netcracker_study_autumn_2020.library.network.UnsafeOkHttpClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.netcracker_study_autumn_2020.library.network.NetworkUtils.API_ADDRESS;

public class RetrofitAuthManagerImpl extends SessionManager implements AuthManager {

    private AuthService retrofitAuthService;

    public RetrofitAuthManagerImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(UnsafeOkHttpClient.getUnsafeOkHttpClient())
                .baseUrl(API_ADDRESS)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAuthService = retrofit.create(AuthService.class);
    }


    @Override
    public void registerUser(String email, String username, String password, RegisterUserCallback signUpCallback) {
        Call<ResponseBody> result = retrofitAuthService.signUp(email, password);
        result.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                signUpCallback.onRegisterFinished(response.code());
                if (response.body() != null) {
                    try {
                        Log.d("REGISTER", response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                signUpCallback.onError((Exception) t);
            }
        });
    }

    @Override
    public void signInWithEmailAndPassword(String login, String password, SignInWithEmailAndPasswordCallback signInCallback) {
        Call<UserSessionValues> result = retrofitAuthService.signIn(new UserCredentials(login, password));

        result.enqueue(new Callback<UserSessionValues>() {
            @Override
            public void onResponse(@NonNull Call<UserSessionValues> call, @NonNull Response<UserSessionValues> response) {
                if (response.body() != null) {
                    Log.d("TEST", response.body().toString());
                    openSession(response.body().getToken(), response.body().getId());
                    signInCallback.onSignInFinished(response.code(), sessionToken);
                } else {
                    Log.d("SIGNIN", "NULL_BODY");
                    signInCallback.onSignInFinished(response.code(), "");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserSessionValues> call, @NonNull Throwable t) {
                signInCallback.onError((Exception) t);
            }
        });
    }


    @Override
    public void signOut(SignOutCallback signOut) {
        closeSession();
        signOut.onSignOutFinished();
    }

    //@Override
    //public String getSessionToken() {
    //return sessionToken;
    //}

    // @Override
    //public int getCurrentUserId() {
    //return userId;
    //}


    @Override
    public void openSession(String sT, long uI) {
        if (!sT.isEmpty()) {
            sessionToken = sT;
            currentUserId = uI;
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

}
