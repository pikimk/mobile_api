package com.example.MobileDemo;

import com.fasterxml.jackson.annotation.JsonInclude;

public class RegisterResponse {
    private String message;
    private Boolean succsess;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private User user;

    public RegisterResponse(Boolean succsess, String message, User u){
        this.succsess = succsess;
        this.user = u;
        this.message = message;
    }

    public RegisterResponse(){};


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccsess() {
        return succsess;
    }

    public void setSuccsess(Boolean succsess) {
        this.succsess = succsess;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
