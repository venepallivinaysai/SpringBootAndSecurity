package com.springBlog.SpringBlog.services;

import com.springBlog.SpringBlog.dao.CommentRepository;
import com.springBlog.SpringBlog.dao.PostRepository;
import com.springBlog.SpringBlog.dtos.CommentDto;
import com.springBlog.SpringBlog.entities.Comment;
import com.springBlog.SpringBlog.entities.Post;
import com.springBlog.SpringBlog.exceptions.ResourseNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CommentDto createComment(long postId,CommentDto commentDto) throws ResourseNotFoundException {
        Comment comment= getEntity(commentDto);
        Post post= postRepository.findById(postId).orElseThrow(() ->new ResourseNotFoundException("Post","id",String.valueOf(postId)));

        comment.setPost(post);
        return  getDto(commentRepository.save(comment));
    }

    private Comment getEntity(CommentDto commentDto){
        return modelMapper.map(commentDto, Comment.class);
    }

    private CommentDto getDto(Comment comment){
        return modelMapper.map(comment, CommentDto.class);
    }

    public List<CommentDto> getComments(long postId) {

        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return  comments.stream().map(this::getDto).toList();
    }

    public CommentDto getCommentById(long postId, long commentId) throws Exception {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId).orElseThrow(() -> new ResourseNotFoundException("Comments", "Id", String.valueOf(commentId)));
        return getDto(comment);

    }

    public CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto) throws ResourseNotFoundException {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId).orElseThrow(() -> new ResourseNotFoundException("Comments", "Id", String.valueOf(commentId)));
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        return getDto(commentRepository.save(comment));
    }

    public String deleteCommentById(long postId, long commentId) throws ResourseNotFoundException {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId).orElseThrow(() -> new ResourseNotFoundException("Comments", "Id", String.valueOf(commentId)));
        commentRepository.deleteById(commentId);
        return "Comment Deleted Successfully with Id "+ commentId;
    }



}
