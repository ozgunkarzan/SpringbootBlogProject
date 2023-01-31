package com.springboot.blog.service;


import com.springboot.blog.payload.CommentDTO;

import java.util.List;


public interface CommentService {

    CommentDTO createComment(long postId, CommentDTO commentDTO);




    List <CommentDTO> getAllCommentsByPostId(long postId);

    CommentDTO getCommentById(long postId, long commentId);

    CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO);

    CommentDTO deleteComment(long postId, long commentId);
}
