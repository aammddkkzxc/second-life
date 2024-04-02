package com.example.secondlife.domain.user.dto;

import com.example.secondlife.domain.user.entity.User;

public class UserDtoUtil {

    public static JoinResponse userToJoinResponse(User user) {
        return JoinResponse.builder()
                .userId(user.getId())
                .loginId(user.getLoginId())
                .nickname(user.getNickname())
                .region(user.getIntroduction().getRegion())
                .birthDate(user.getIntroduction().getBirthDate())
                .selfIntroduction(user.getIntroduction().getSelfIntroduction())
                .role(user.getRole())
                .createdDate(user.getCreatedDate())
                .build();
    }

    public static UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .region(user.getIntroduction().getRegion())
                .birthDate(user.getIntroduction().getBirthDate())
                .selfIntroduction(user.getIntroduction().getSelfIntroduction())
                .role(user.getRole())
                .isDeleted(user.isDeleted())
                .verified(user.isVerified())
                .build();
    }
}
