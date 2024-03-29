package com.example.secondlife.common.security;

import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userLoginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(userLoginId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userLoginId));

        if (user.isDeleted()) {
            throw new UsernameNotFoundException("탈퇴한 사용자입니다: " + userLoginId);
        }

        return new CustomUserDetails(user);
    }

}
