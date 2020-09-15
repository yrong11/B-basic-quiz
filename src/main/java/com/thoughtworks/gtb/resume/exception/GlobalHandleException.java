package com.thoughtworks.gtb.resume.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandleException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleArgNotValidException(MethodArgumentNotValidException exception) {
        ErrorResult errorResult = ErrorResult.builder().code(HttpStatus.BAD_REQUEST.value()).message(exception.getBindingResult().getFieldError().getDefaultMessage()).build();
        return ResponseEntity.badRequest().body(errorResult);
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity handleUserNotExist(UserNotExistException exception) {
        ErrorResult errorResult = ErrorResult.builder().code(HttpStatus.BAD_REQUEST.value()).message(exception.getMessage()).build();
        return ResponseEntity.badRequest().body(errorResult);
    }

    @ExceptionHandler(EducationException.class)
    public ResponseEntity handleEducationException(EducationException exception) {
        ErrorResult errorResult = ErrorResult.builder().code(HttpStatus.BAD_REQUEST.value()).message(exception.getMessage()).build();
        return ResponseEntity.badRequest().body(errorResult);
    }

}
