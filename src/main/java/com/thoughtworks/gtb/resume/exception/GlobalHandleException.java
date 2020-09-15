package com.thoughtworks.gtb.resume.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.swing.tree.ExpandVetoException;
import java.util.Date;

@ControllerAdvice
public class GlobalHandleException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleArgNotValidException(MethodArgumentNotValidException exception) {
        ErrorResult errorResult = ErrorResult.builder().status(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date()).error("Bad Request")
                .message(exception.getBindingResult().getFieldError().getDefaultMessage()).build();
        return ResponseEntity.badRequest().body(errorResult);
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity handleUserNotExist(UserNotExistException exception) {
        return ResponseEntity.badRequest().body(assemblyBadRequestError(exception));
    }

    @ExceptionHandler(EducationException.class)
    public ResponseEntity handleEducationException(EducationException exception) {
        return ResponseEntity.badRequest().body(assemblyBadRequestError(exception));
    }

    private ErrorResult assemblyBadRequestError(Exception exception) {
        ErrorResult errorResult = ErrorResult.builder().status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request").timestamp(new Date()).message(exception.getMessage()).build();
        return errorResult;
    }

}
