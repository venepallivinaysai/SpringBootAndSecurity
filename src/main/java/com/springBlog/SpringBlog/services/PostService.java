package com.springBlog.SpringBlog.services;

import com.springBlog.SpringBlog.dao.PostRepository;
import com.springBlog.SpringBlog.dtos.PostDto;
import com.springBlog.SpringBlog.dtos.PostResponse;
import com.springBlog.SpringBlog.entities.Post;
import com.springBlog.SpringBlog.exceptions.ResourseNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PostDto createPost(PostDto postDto) {

        // dto to entity
        Post post= getEntityFromDto(postDto);

        return getDtoFromEntity(postRepository.save(post));
    }

    public PostResponse getPosts(int pageNumber, int pageSize, String sortBy) {

        PageRequest pageable= PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

        Page<Post> pagePosts= postRepository.findAll(pageable);

        List<Post> posts= pagePosts.getContent();

        List<PostDto> content = posts.stream().map(this::getDtoFromEntity).toList();

        return new PostResponse(content, pagePosts.getNumber(), pagePosts.getSize(), pagePosts.getTotalElements(), pagePosts.getTotalPages(), pagePosts.isLast());



    }

    private PostDto getDtoFromEntity(Post post){
        return modelMapper.map(post, PostDto.class) ;
    }

    private Post getEntityFromDto(PostDto postDto){
        return modelMapper.map(postDto, Post.class);
    }

    public PostDto getPostById(Long id) throws ResourseNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourseNotFoundException("Post","Id",String.valueOf(id)));
        return getDtoFromEntity(post);
    }

    public PostDto updatePostById(PostDto postDto, Long id) throws ResourseNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourseNotFoundException("Post","Id",String.valueOf(id)));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        return getDtoFromEntity(postRepository.save(post));

    }

    public PostDto deletePostById(Long id) throws ResourseNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourseNotFoundException("Post","Id",String.valueOf(id)));
        postRepository.deleteById(id);

        return getDtoFromEntity(post);
    }
}
