package com.springBlog.SpringBlog.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourseNotFoundException extends Exception{

    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public ResourseNotFoundException( String resourceName, String fieldName, String fieldValue){
        super(String.format("%s not found with %s : %s",resourceName,fieldName,fieldValue));
        this.resourceName= resourceName;
        this.fieldName= fieldName;
        this.fieldValue = fieldValue;
    }


}
