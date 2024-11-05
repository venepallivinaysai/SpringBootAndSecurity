package com.springBlog.SpringBlog.controllers;

import com.springBlog.SpringBlog.dtos.CommentDto;
import com.springBlog.SpringBlog.exceptions.ResourseNotFoundException;
import com.springBlog.SpringBlog.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto, @PathVariable long postId) throws ResourseNotFoundException {
        return  new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("posts/{postId}/comments")
    public List<CommentDto> getComments(@PathVariable long postId){
        return commentService.getComments(postId);
    }

    @GetMapping("posts/{postId}/comments/{commentId}")
    public CommentDto getComments(@PathVariable long postId, @PathVariable long commentId) throws Exception {
        return commentService.getCommentById(postId,commentId);
    }

    @PutMapping("posts/{postId}/comments/{commentId}")
    public CommentDto updateComment(@PathVariable long postId, @PathVariable long commentId, @Valid @RequestBody CommentDto commentDto) throws Exception {
        return commentService.updateCommentById(postId,commentId, commentDto);
    }

    @DeleteMapping("posts/{postId}/comments/{commentId}")
    public String deleteComment(@PathVariable long postId, @PathVariable long commentId) throws Exception {
        return commentService.deleteCommentById(postId,commentId);
    }


}
