package com.springboot.blog.payload;


import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
public class CommentDTO {
    private long id;
    @NotEmpty(message = "Name cannot be empty or null")
    private String name;
    @NotEmpty(message = "E-mail address cannot be empty ")
    @Email(message = "Invalid E-mail address")
    private String email;
    @NotEmpty(message = "Comments without body is not allowed")
    @Size(min=10, message = "Body should not be less than 10 characters")
    private String body;

}
