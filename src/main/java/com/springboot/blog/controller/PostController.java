package com.springboot.blog.controller;


import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import org.springframework.data.mapping.model.AbstractPersistentProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    //create post API
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){

    return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    //Get All Posts API
    @GetMapping
    public PostResponse getAllPosts(@RequestParam (value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                    @RequestParam(value = "sortBy", defaultValue= AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir)
    {

      return  postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

    //Get Post by Id API
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name="id") long id){
    return ResponseEntity.ok(postService.getPostById(id));
    }

    //Update Post API
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable(name="id") long id,@Valid @RequestBody PostDTO postDTO){
        return ResponseEntity.ok(postService.updatePost(id, postDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostDTO>deletePost(@PathVariable(name="id") long id){
        return ResponseEntity.ok(postService.deletePost(id));
    }


}
