package com.example.learning_english.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.example.learning_english.util.ExceptionMessage.NOT_FOUND;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    private String errorMessage;

    //Course Not Found
    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Object> handleCourseNotFound(
            CourseNotFoundException ex, WebRequest request
    ){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(NOT_FOUND);
    }

    //Not Null
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            this.errorMessage= error.getDefaultMessage();
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.errorMessage);    }
}
