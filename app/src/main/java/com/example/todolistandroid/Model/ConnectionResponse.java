package com.example.todolistandroid.Model;

public class ConnectionResponse {

    private String response;
    private int errorCode;
    private String message;

    public ConnectionResponse(String response, int errorCode, String message) {
        this.response = response;
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
