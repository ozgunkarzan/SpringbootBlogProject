package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper=mapper;
    }

    CommentRepository commentRepository;

    ModelMapper mapper;



    PostRepository postRepository;



    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {

        Comment comment=mapToEntity(commentDTO);
        //Retrieve the post by id
        Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",String.valueOf(postId)));
        comment.setPost(post);
        Comment createdComment = commentRepository.save(comment);
        return mapToDto(createdComment);
    }

    @Override
    public List<CommentDTO> getAllCommentsByPostId(long postId) {

        //Retrieve all comments to the given Post by PostId
        List<Comment> commentList =commentRepository.findByPostId(postId);

        //Returning List of comments by mapping to DTO
        return commentList.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(long postId, long commentId) {
        Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",String.valueOf(postId)));

        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",String.valueOf(commentId)));

        if(comment.getPost().getId()!=postId){
            throw new BlogAPIException("This comment do not belongs to this post", HttpStatus.BAD_REQUEST);
        }


        return mapToDto(comment);
    }

    @Override
    public CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO) {
        Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",String.valueOf(postId)));

        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",String.valueOf(commentId)));

        if(comment.getPost().getId()!=postId){
            throw new BlogAPIException("This comment do not belongs to this post", HttpStatus.BAD_REQUEST);
        }


        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());

        CommentDTO updatedCommentDto=mapToDto(commentRepository.save(comment));

        return updatedCommentDto;
    }

    @Override
    public CommentDTO deleteComment(long postId, long commentId) {
        Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",String.valueOf(postId)));

        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",String.valueOf(commentId)));

        if(comment.getPost().getId()!=postId){
            throw new BlogAPIException("This comment do not belongs to this post", HttpStatus.BAD_REQUEST);
        }
        commentRepository.deleteById(commentId);

        return mapToDto(comment);
    }


    private CommentDTO mapToDto(Comment comment){

        CommentDTO commentDTO= mapper.map(comment,CommentDTO.class);


//        CommentDTO commentDTO= new CommentDTO();
//        commentDTO.setId(comment.getId());
//        commentDTO.setName(comment.getName());
//        commentDTO.setEmail(comment.getEmail());
//        commentDTO.setBody(comment.getBody());


        return commentDTO;
    }

    private Comment mapToEntity(CommentDTO commentDTO){

        Comment comment=mapper.map(commentDTO,Comment.class);

//        Comment comment=new Comment();
//        comment.setId(commentDTO.getId());
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setBody(commentDTO.getBody());

        return comment;
    }
}
