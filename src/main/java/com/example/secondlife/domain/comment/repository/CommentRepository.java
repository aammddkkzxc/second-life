package com.example.secondlife.domain.comment.repository;

import com.example.secondlife.domain.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT c FROM Comment c WHERE c.post.id = :postId")
    List<Comment> findCommentsByPostId(@Param("postId") Long postId);
}
