package com.demo.FirstProject.Controller;


public class LoginResponse {
    private String token;

    private long expiresIn;


    public LoginResponse setExpiresIn(long expiresIn) {

        this.expiresIn = expiresIn;
        return this;
    }

    public String getToken() {
        this.token = token;
        return token;
    }

    public LoginResponse setToken(String token) {
        this.token = token;
        return this;  // return 'this' to allow chaining
    }

}