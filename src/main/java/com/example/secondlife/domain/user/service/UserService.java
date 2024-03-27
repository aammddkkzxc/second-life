package com.example.secondlife.domain.user.service;

import com.example.secondlife.domain.user.dto.JoinRequest;
import com.example.secondlife.domain.user.dto.JoinResponse;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public JoinResponse save(JoinRequest request) {
        User savedUser = userRepository.save(request.toEntity());

        return savedUser.toJoinResponse();
    }
}
