package com.example.secondlife.domain.post.repository;

import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.enumType.Forum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Long countByUserId(Long userId);

    Page<Post> findAllByUserId(Long userId, Pageable pageable);

    Page<Post> findAllByForum(Forum forum, Pageable pageable);
}
