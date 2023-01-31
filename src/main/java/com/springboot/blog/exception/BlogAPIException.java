package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException{

    private String message;
    private HttpStatus status;

    public BlogAPIException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public BlogAPIException(String message, String message1, HttpStatus status) {
        super(message);
        this.message = message1;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
