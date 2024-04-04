package com.example.secondlife.domain.user.repository;

import com.example.secondlife.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String username);

    boolean existsByEmail(String email);

    Optional<User> findByNicknameContaining(String nickname);
}
