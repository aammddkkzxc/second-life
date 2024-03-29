package com.example.secondlife.domain.like.comment.repository;

import com.example.secondlife.domain.like.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
}
