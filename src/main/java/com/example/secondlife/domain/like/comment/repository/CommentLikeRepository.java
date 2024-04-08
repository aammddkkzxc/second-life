package com.example.secondlife.domain.like.comment.repository;

import com.example.secondlife.domain.like.comment.entity.CommentLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    @Query("select cl from CommentLike cl where cl.comment.id = :commentId and cl.user.id = :userId")
    Optional<CommentLike> findByCommentIdAndUserId(@Param("commentId") Long commentId, @Param("userId") Long userId);

}
