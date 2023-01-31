package com.springboot.blog.service;

import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy,String sortDir);

    PostDTO getPostById(long id);

    PostDTO updatePost(long id,PostDTO postDTO);

    PostDTO deletePost(long id);
}
