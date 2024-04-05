package com.example.secondlife.domain.post.repository;

import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.enumType.Forum;
import com.example.secondlife.domain.user.enumType.Region;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByUserIdAndIsDeletedFalse(Long userId, Pageable pageable);

    Page<Post> findAllByForumAndIsDeletedFalse(Forum forum, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.user u WHERE p.forum = :forum AND u.introduction.region = :region AND p.isDeleted = false")
    Page<Post> findAllByForumAndUserRegionAndIsDeletedFalse(@Param("forum") Forum forum, @Param("region") Region region,
                                                            Pageable pageable);

    @Query("SELECT p.contents FROM Post p WHERE p.id = :postId AND p.isDeleted = false")
    Optional<String> findContentsById(@Param("postId") Long postId);

    @Query(value = "SELECT p.post_id, p.title, p.hits, p.created_date, " +
            "(SELECT COUNT(*) FROM post_like pl WHERE pl.post_id = p.post_id) AS likeCount, " +
            "(SELECT COUNT(*) FROM comment c WHERE c.post_id = p.post_id AND c.is_deleted = false) AS commentCount, " +
            "u.region " +
            "FROM post p " +
            "LEFT JOIN users u ON p.user_id = u.user_id " +
            "WHERE p.created_date >= :startDate AND p.is_deleted = false " +
            "GROUP BY p.post_id, p.hits " +
            "ORDER BY likeCount DESC, p.hits DESC, commentCount DESC " +
            "LIMIT 15", nativeQuery = true)
    List<Object[]> findHotPostsNative(LocalDateTime startDate);
}
