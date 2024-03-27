package com.example.secondlife.domain.post.repository;

import com.example.secondlife.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
