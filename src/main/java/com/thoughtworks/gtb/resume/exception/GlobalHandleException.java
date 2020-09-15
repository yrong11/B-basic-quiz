package com.thoughtworks.gtb.resume.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandleException {

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity handleRuntimeException(MethodArgumentNotValidException exception) {
//        ErrorResult errorResult = ErrorResult.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(exception.getBindingResult().getFieldError().getDefaultMessage()).build();
//        return ResponseEntity.badRequest().body(errorResult);
//    }


}
