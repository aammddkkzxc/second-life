package com.example.secondlife.domain.post.repository;

import com.example.secondlife.domain.post.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p left join fetch p.comments")
    List<Post> findAllWithComments();

}
