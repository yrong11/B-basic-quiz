package com.thoughtworks.gtb.resume.exception;

public class EducationException extends Exception{
    private String message = "";

    @Override
    public String getMessage() {
        return message;
    }

    public EducationException(String message) {
        this.message = message;
    }
}
