package com.netcracker_study_autumn_2020.domain.dto;

public class UserDto {

    private long user_id;
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private boolean acceptTermsOfService;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
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
