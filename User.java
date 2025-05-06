package com.yorku.parking.model;

public abstract class User {
    private String email;
    private String password;
    private String userType;

    public boolean login(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String type) {
        this.userType = type;
    }
}
