package com.netcracker_study_autumn_2020.data.entity;

public class UserEntity {
    //private int id;
    //private int accountId;
    private String email;
    //private String login;
    private String password;
    private String username;
    //private String firstName;
    //private String lastName;
    //private String fullName;
    //private boolean acceptTermsOfService;
    //private String timeZone;

    public UserEntity(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
