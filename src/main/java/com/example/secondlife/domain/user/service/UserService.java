package com.example.secondlife.domain.user.service;

import com.example.secondlife.domain.user.dto.JoinRequest;
import com.example.secondlife.domain.user.dto.JoinResponse;
import com.example.secondlife.domain.user.dto.UpdateUserRequest;
import com.example.secondlife.domain.user.dto.UserResponse;
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
    public UserResponse getUserProfile(Long userId) {
        log.info("getUserInfo()");

        User user = findById(userId);

        return user.UserResponse();
    }

    public UserResponse updateUserProfile(Long userId, UpdateUserRequest request) {
        log.info("updateUserProfile()");

        String password = request.getPassword();

        if (password != null && !password.isEmpty()) {
            log.info("password is not empty");
            request.setPassword(passwordEncoder.encode(password));
        }

        User user = findById(userId);

        user.updateUserProfile(request);

        return user.UserResponse();
    }

    public void delete(Long userId) {
        log.info("delete()");

        User findUser = findById(userId);

        findUser.delete();
    }

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        log.info("findById()");

        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. userId = " + userId));
    }

    private User joinRequestToUser(JoinRequest request) {
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
