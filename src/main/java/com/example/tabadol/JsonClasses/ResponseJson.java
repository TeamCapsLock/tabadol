package com.example.tabadol.JsonClasses;

public class ResponseJson {
    private String message;

    public ResponseJson(String message) {
        this.message = message;
    }

    public ResponseJson() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
