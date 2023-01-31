package com.springboot.blog.controller;


import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.service.impl.CommentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(name="postId") long postId,@Valid @RequestBody CommentDTO commentDTO){

       return new ResponseEntity<>(commentService.createComment(postId,commentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDTO>>getAllCommentsByPostId(@PathVariable(name="postId")long postId){

        return new ResponseEntity<List <CommentDTO>>(commentService.getAllCommentsByPostId(postId), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO>getCommentById(@PathVariable(name="postId") long postId,
                                                    @PathVariable(name="commentId") long commentId){

        return new ResponseEntity<CommentDTO>(commentService.getCommentById(postId,commentId),HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO>updateComment(@PathVariable(name="postId") long postId,
                                                   @PathVariable(name="commentId") long commentId,
                                                   @Valid @RequestBody CommentDTO commentDTO){

        return new ResponseEntity<>(commentService.updateComment(postId,commentId,commentDTO),HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO>deleteComment(@PathVariable(name="postId") long postId,
                                                   @PathVariable(name="commentId") long commentId){
        return new ResponseEntity<>(commentService.deleteComment(postId,commentId),HttpStatus.OK);
    }
}
