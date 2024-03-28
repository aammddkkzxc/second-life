package com.example.secondlife.domain.user.service;

import com.example.secondlife.domain.user.dto.JoinRequest;
import com.example.secondlife.domain.user.dto.JoinResponse;
import com.example.secondlife.domain.user.dto.UserInfo;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.enumType.Role;
import com.example.secondlife.domain.user.repository.UserRepository;
import com.example.secondlife.domain.user.valueType.Introduction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public JoinResponse save(JoinRequest request) {
        log.info("save()");

        User savedUser = userRepository.save(joinRequestToUser(request));

        return savedUser.toJoinResponse();
    }

    @Transactional(readOnly = true)
    public UserInfo getUserInfo(Long userId) {
        log.info("getUserInfo()");

        User user = findById(userId);

        return user.toUserInfo();
    }

    public UserInfo updateUserInfo(Long userId, UserInfo request) {
        log.info("updateUserInfo()");

        User user = findById(userId);

        user.updateUserInfo(request);

        return user.toUserInfo();
    }

    public void deleteUser(Long userId) {
        log.info("deleteUser()");

        User findUser = findById(userId);

        findUser.delete();
    }

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        log.info("findById()");

        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. userId = " + userId));
    }

    public User joinRequestToUser(JoinRequest request) {
        log.info("joinRequestToUser()");

        Introduction introduction = new Introduction(request.getRegion(), request.getBirthDate(),
                request.getSelfIntroduction());

        return User.builder()
                .loginId(request.getLoginId())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .email(request.getEmail())
                .introduction(introduction)
                .role(Role.L1)
                .build();

    }
}
