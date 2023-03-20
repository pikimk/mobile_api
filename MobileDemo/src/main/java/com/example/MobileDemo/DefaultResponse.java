package com.example.MobileDemo;

public class DefaultResponse {
    private String message;
    private Boolean succsess;

    public DefaultResponse(String message, Boolean succsess){
        this.message = message;
        this.succsess = succsess;
    }

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
}
