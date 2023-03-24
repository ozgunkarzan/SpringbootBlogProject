package com.springboot.blog.payload;


import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDTO {


    private long id;

    @NotEmpty
    @Size(min = 2,message = "Post Title cannot have less than 2 characters")
    private String title;
    @NotEmpty
    @Size(min=10,message = "Description cannot have less than 10 characters")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDTO> comments;

    private Long categoryId;
}
