package com.example.secondlife.domain.user.service;

import com.example.secondlife.common.exception.NotFoundException;
import com.example.secondlife.domain.user.dto.ProfileResponse;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.repository.UserQRepository;
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
    private final UserQRepository userQRepository;

    public ProfileResponse getProfile(Long userId) {
        log.info("getUserInfo()");

        return userQRepository.findProfile(userId);
    }

    public User findById(Long userId) {
        log.info("findById()");

        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("해당 사용자가 존재하지 않습니다. userId = " + userId));
    }

    public User findByNickname(String nickname) {
        log.info("findByNickname()");

        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new NotFoundException("해당 사용자가 존재하지 않습니다. nickname = " + nickname));
    }

    public boolean existByEmail(String email) {
        log.info("existByEmail()");

        return userRepository.existsByEmail(email);
    }
}
