package com.example.secondlife.domain.like.post.repository;

import com.example.secondlife.domain.like.post.dto.PostLikeCountDto;
import com.example.secondlife.domain.like.post.entity.PostLike;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    @Query("select pl from PostLike pl where pl.post.id = :postId and pl.user.id = :userId")
    Optional<PostLike> findByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);

    @Query("SELECT new com.example.secondlife.domain.like.post.dto.PostLikeCountDto(pl.post.id, COUNT(pl)) " +
            "FROM PostLike pl " +
            "WHERE pl.post.id = :postId " +
            "GROUP BY pl.post.id")
    PostLikeCountDto countLikesByPostId(@Param("postId") Long postId);

}
