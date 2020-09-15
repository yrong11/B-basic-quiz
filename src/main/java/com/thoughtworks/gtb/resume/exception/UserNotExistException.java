package com.thoughtworks.gtb.resume.exception;

public class UserNotExistException extends Exception{
    private String message = "user not exist";

    @Override
    public String getMessage() {
        return message;
    }
}
