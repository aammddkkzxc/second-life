package com.example.secondlife.domain.comment.repository;

import com.example.secondlife.domain.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostIdAndIsDeletedFalse(Long postId);

}
