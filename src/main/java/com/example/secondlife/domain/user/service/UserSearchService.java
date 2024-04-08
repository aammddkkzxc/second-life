package com.example.secondlife.domain.user.service;

import com.example.secondlife.common.exception.NotFoundException;
import com.example.secondlife.domain.user.dto.PostUser;
import com.example.secondlife.domain.user.dto.ProfileResponse;
import com.example.secondlife.domain.user.dto.UserDtoUtil;
import com.example.secondlife.domain.user.dto.UserResponse;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.repository.UserQRepository;
import com.example.secondlife.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSearchService {

    private final UserRepository userRepository;
    private final UserQRepository userQRepository;

    public ProfileResponse getProfile(Long userId) {
        final ProfileResponse profile = userQRepository.findProfile(userId);

        if (profile == null) {
            throw new NotFoundException("해당 사용자가 존재하지 않습니다. userId = " + userId);
        }

        return profile;
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("해당 사용자가 존재하지 않습니다. userId = " + userId));
    }

    public PostUser findPostUserByPostId(Long postId) {
        return userRepository.findPostUserByPostId(postId)
                .orElseThrow(() -> new NotFoundException("해당 사용자가 존재하지 않습니다. postId = " + postId));
    }

    public UserResponse searchByNickName(String nickname) {
        final Optional<User> optionalUser = userRepository.findByNicknameContaining(nickname);

        return optionalUser.map(UserDtoUtil::userToUserResponse).orElse(new UserResponse());
    }

    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
