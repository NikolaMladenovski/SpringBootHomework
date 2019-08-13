package com.example.SpringBootHomework.exception;

public class TweetNotFoundException extends RuntimeException{
    private String message;
    public TweetNotFoundException(){
        this.message = "Tweet not found";

    }

    @Override
    public String getMessage() {
        return message;
    }
}
