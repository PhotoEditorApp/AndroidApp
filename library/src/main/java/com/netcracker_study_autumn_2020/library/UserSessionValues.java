package com.netcracker_study_autumn_2020.library;

public class UserSessionValues {
    private int id;
    private String token;

    public UserSessionValues(){}

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
