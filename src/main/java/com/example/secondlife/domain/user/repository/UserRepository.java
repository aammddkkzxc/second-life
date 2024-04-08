package com.example.secondlife.domain.user.repository;

import com.example.secondlife.domain.user.dto.PostUser;
import com.example.secondlife.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String username);

    boolean existsByEmail(String email);

    Optional<User> findByNicknameContaining(String nickname);

    @Query("SELECT new com.example.secondlife.domain.user.dto.PostUser(u.id, u.nickname) " +
            "FROM Post p JOIN p.user u WHERE p.id = :postId")
    Optional<PostUser> findPostUserByPostId(Long postId);
}
