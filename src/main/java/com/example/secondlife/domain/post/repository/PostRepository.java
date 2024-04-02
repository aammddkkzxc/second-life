package com.example.secondlife.domain.post.repository;

import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.enumType.Forum;
import com.example.secondlife.domain.user.enumType.Region;
import java.time.LocalDateTime;
import java.util.List;
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

    @Query("SELECT p FROM Post p WHERE p.createdDate >= :sevenDaysAgo")
    List<Post> findPostsLastSevenDays(LocalDateTime sevenDaysAgo);

}
