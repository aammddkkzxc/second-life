package com.example.secondlife.domain.user.service;

import com.example.secondlife.domain.user.dto.UserResponse;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserSearchService {

    private final UserRepository userRepository;

    public UserResponse getUserProfile(Long userId) {
        log.info("getUserInfo()");

        User user = findById(userId);

        return user.userResponse();
    }

    public User findById(Long userId) {
        log.info("findById()");

        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. userId = " + userId));
    }

    public User findByNickname(String nickname) {
        log.info("findByNickname()");

        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. nickname = " + nickname));
    }

    public boolean existByEmail(String email) {
        log.info("existByEmail()");

        return userRepository.existsByEmail(email);
    }
}
