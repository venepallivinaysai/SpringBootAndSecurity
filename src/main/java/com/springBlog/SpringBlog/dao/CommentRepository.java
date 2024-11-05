package com.springBlog.SpringBlog.dao;

import com.springBlog.SpringBlog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPostId(long postId);
    Optional<Comment> findByIdAndPostId(long commentId, long postId);
}
