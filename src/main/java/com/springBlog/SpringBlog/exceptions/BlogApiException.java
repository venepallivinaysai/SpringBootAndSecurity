package com.springBlog.SpringBlog.exceptions;

import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException {

    private String message;
    private HttpStatus status;
    public BlogApiException(String message, HttpStatus status) {
        super(message);
        this.message= message;
        this.status= status;
    }
}
