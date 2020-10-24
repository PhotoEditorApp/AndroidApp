package com.netcracker_study_autumn_2020.data.entity.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Response;


public class SignInResult {
    @SerializedName("token")
    @Expose
    private String token;

    SignInResult(){
        super();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
