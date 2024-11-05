package com.springBlog.SpringBlog.controllers;

import com.springBlog.SpringBlog.dtos.PostDto;
import com.springBlog.SpringBlog.dtos.PostResponse;
import com.springBlog.SpringBlog.exceptions.ResourseNotFoundException;
import com.springBlog.SpringBlog.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("posts")
@Tag(
        name = "CRUD Operations for POSTS"
)
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(
            summary = "Saving a Post to Database"
    )
    @ApiResponses(
            { @ApiResponse( responseCode = "201", description = "HTTP Status 201 Created"),
                    @ApiResponse( responseCode = "500", description = "Internal Server Error")}
    )
    @SecurityRequirement(name = "securityName")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto) , HttpStatus.CREATED);
    }

    @GetMapping
    public PostResponse getPosts(@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNumber ,
                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                 @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy){
        return postService.getPosts(pageNumber,pageSize, sortBy);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) throws ResourseNotFoundException {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById( @Valid @RequestBody PostDto postDto, @PathVariable Long id) throws ResourseNotFoundException {
        return ResponseEntity.ok(postService.updatePostById(postDto,id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public PostDto deletePostById(@PathVariable Long id) throws ResourseNotFoundException {
        return postService.deletePostById(id);
    }
}
