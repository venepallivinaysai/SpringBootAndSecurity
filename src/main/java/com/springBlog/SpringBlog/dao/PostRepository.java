package com.springBlog.SpringBlog.dao;

import com.springBlog.SpringBlog.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
//SimpleJpaRepository internally has @Repository and @transactional annotations
public interface PostRepository extends JpaRepository<Post, Long> {
}
