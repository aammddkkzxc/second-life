package com.example.secondlife.domain.like.post.repository;

import com.example.secondlife.domain.like.post.entity.PostLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    @Query("select pl from PostLike pl where pl.postId = :postId and pl.userId = :userId")
    Optional<PostLike> findByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);

    @Query("SELECT COUNT(pl) FROM PostLike pl WHERE pl.postId = :postId")
    Long countLikesByPostId(@Param("postId") Long postId);

}
