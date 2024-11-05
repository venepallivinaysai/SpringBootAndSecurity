package com.springBlog.SpringBlog.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {

    private Date timeStamp;
    private String message;
}
