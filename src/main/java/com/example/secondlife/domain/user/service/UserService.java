package com.example.secondlife.domain.user.service;

import com.example.secondlife.domain.user.dto.JoinRequest;
import com.example.secondlife.domain.user.dto.JoinResponse;
import com.example.secondlife.domain.user.dto.UpdateUserRequest;
import com.example.secondlife.domain.user.dto.UpdateUserRole;
import com.example.secondlife.domain.user.dto.UserDtoUtil;
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
    private final UserSearchService userSearchService;
    private final BCryptPasswordEncoder passwordEncoder;

    public JoinResponse save(JoinRequest request) {
        log.info("save()");

        User savedUser = userRepository.save(joinRequestToUser(request));

        return UserDtoUtil.userToJoinResponse(savedUser);
    }

    public UserResponse updateProfile(Long userId, UpdateUserRequest request) {
        log.info("updateUserProfile()");

        String password = request.getPassword();

        if (password != null && !password.isEmpty()) {
            log.info("password is not empty");
            request.setPassword(passwordEncoder.encode(password));
        }

        User findUser = userSearchService.findById(userId);

        findUser.updateUserProfile(request);

        return UserDtoUtil.userToUserResponse(findUser);
    }

    public UserResponse updateRole(Long userId, UpdateUserRole request) {
        log.info("updateUserRole()");

        User findUser = userSearchService.findById(userId);

        findUser.updateUserRole(request);

        return UserDtoUtil.userToUserResponse(findUser);
    }

    public void updateVerify(Long userId, String email) {
        log.info("updateVerify()");

        User findUser = userSearchService.findById(userId);

        findUser.updateVerify(email);
    }

    public void delete(Long userId) {
        log.info("delete()");

        User findUser = userSearchService.findById(userId);

        findUser.delete();
    }

    private User joinRequestToUser(JoinRequest request) {
        Introduction introduction = new Introduction(request.getRegion(), request.getBirthDate(),
                request.getSelfIntroduction());

        return User.builder()
                .loginId(request.getLoginId())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .introduction(introduction)
                .role(Role.L1)
                .build();
    }
}
