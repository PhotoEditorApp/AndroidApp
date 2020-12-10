package com.netcracker_study_autumn_2020.data.entity;

public class UserEntity {

    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private boolean acceptTermsOfService;
    //private String timeZone;


    public UserEntity() {
    }

    public UserEntity(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isAcceptTermsOfService() {
        return acceptTermsOfService;
    }

    public void setAcceptTermsOfService(boolean acceptTermsOfService) {
        this.acceptTermsOfService = acceptTermsOfService;
    }


}
