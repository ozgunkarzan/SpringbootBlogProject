package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository,ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper=mapper;
        this.categoryRepository=categoryRepository;
    }



    @Override
    public PostDTO createPost(PostDTO postDto) {

        Category category =categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category","id ", String.valueOf(postDto.getCategoryId())));
        //Convert DTO to Entity
        Post post =mapToEntity(postDto);
        post.setCategory(category);

        Post newPost= postRepository.save(post);
        //Convert Entity to DTO

        PostDTO postResponse=mapToDTO(newPost);




        return postResponse;

    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        PageRequest pageable= PageRequest.of(pageNo,pageSize, sort);
        Page<Post> posts= postRepository.findAll(pageable);
        List<Post>listOfPosts=posts.getContent();
        List<PostDTO> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse(content,pageNo,pageSize, posts.getTotalElements(), posts.getTotalPages(), posts.isLast() );
        return postResponse;

    }

    @Override
    public PostDTO getPostById(long id) {

        Post post =postRepository.findById(id).orElseThrow((()-> new ResourceNotFoundException("Post","id",String.valueOf(id))));

        return mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(long id, PostDTO postDto) {
        Post post = postRepository.findById(id).orElseThrow((()-> new ResourceNotFoundException("Post","id",String.valueOf(id))));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category","id",String.valueOf(postDto.getCategoryId()))));
        Post updatePost=postRepository.save(post);

        return mapToDTO(updatePost);
    }

    @Override
    public PostDTO deletePost(long id) {
        Post post =postRepository.findById(id).orElseThrow((()-> new ResourceNotFoundException("Post","id",String.valueOf(id))));
        postRepository.deleteById(id);
        return mapToDTO(post);
    }

    // Conv Entity to DTO Method
    private PostDTO mapToDTO(Post post){

        PostDTO postDTO=mapper.map(post,PostDTO.class);

        // Without using ModelMapper
//        PostDTO postDTO= new PostDTO();
//        postDTO.setId(post.getId());
//        postDTO.setTitle(post.getTitle());
//        postDTO.setDescription(post.getDescription());
//        postDTO.setContent(post.getContent());


        return postDTO;

    }

    //Conv DTO to Entity Method

    private Post mapToEntity(PostDTO postDto){

        Post post=mapper.map(postDto,Post.class);

        // Without using ModelMapper
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;

    }
}
