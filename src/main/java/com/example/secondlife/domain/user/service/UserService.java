package com.example.secondlife.domain.user.service;

import com.example.secondlife.domain.user.dto.JoinRequest;
import com.example.secondlife.domain.user.dto.JoinResponse;
import com.example.secondlife.domain.user.dto.UserInfo;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public JoinResponse save(JoinRequest request) {
        User savedUser = userRepository.save(request.toEntity());

        return savedUser.toJoinResponse();
    }

    @Transactional(readOnly = true)
    public UserInfo getUserInfo(Long userId) {
        User user = findById(userId);

        return user.toUserInfo();
    }

    public UserInfo updateUserInfo(Long id, UserInfo request) {
        User user = findById(id);

        user.updateUserInfo(request);

        return user.toUserInfo();
    }

    public void deleteUser(Long userId) {
        User findUser = findById(userId);
        findUser.delete();
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. userId = " + id));
    }
}
