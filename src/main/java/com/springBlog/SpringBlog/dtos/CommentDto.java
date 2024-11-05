package com.springBlog.SpringBlog.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private long id;
    @NotEmpty(message = "Name should not be Null or Empty")
    private String name;
    @NotEmpty(message = "Email should not be Empty")
    @Email
    private String email;
    @NotEmpty(message = "Body should not be null")
    @Size(min = 10, message = "Body should have atleast 10 characters")
    private String body;
}
