package com.thoughtworks.gtb.resume.exception;

// GTB: 一般会从 RuntimeException 来继承，形成自己的自定义的异常体系
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
