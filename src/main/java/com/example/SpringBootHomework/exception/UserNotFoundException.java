package com.example.SpringBootHomework.exception;

public class UserNotFoundException extends RuntimeException {
    private String message;

    public UserNotFoundException() {
        this.message = "User not found";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
