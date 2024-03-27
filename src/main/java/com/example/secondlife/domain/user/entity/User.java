package com.example.secondlife.domain.user.entity;

import com.example.secondlife.common.base.BaseTimeEntity;
import com.example.secondlife.domain.user.dto.JoinResponse;
import com.example.secondlife.domain.user.dto.UserInfo;
import com.example.secondlife.domain.user.enumType.Role;
import com.example.secondlife.domain.user.valueType.Introduction;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 16, nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(length = 10, nullable = false)
    private String nickname;

    private String email;

    @Embedded
    private Introduction introduction;

    private boolean isIntroPublic;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isDeleted;

    public JoinResponse toJoinResponse() {
        return JoinResponse.builder()
                .userId(id)
                .loginId(loginId)
                .nickname(nickname)
                .email(email)
                .region(introduction.getRegion())
                .birthDate(introduction.getBirthDate())
                .selfIntroduction(introduction.getSelfIntroduction())
                .role(role)
                .createdDate(getCreatedDate())
                .build();
    }

    public UserInfo toUserInfo() {
        return UserInfo.builder()
                .nickname(nickname)
                .region(introduction.getRegion())
                .birthDate(introduction.getBirthDate())
                .selfIntroduction(introduction.getSelfIntroduction())
                .build();
    }

    public void updateUserInfo(UserInfo request) {
        nickname = request.getNickname() != null ? request.getNickname() : nickname;
        introduction = new Introduction(
                request.getRegion() != null ? request.getRegion() : introduction.getRegion(),
                request.getBirthDate() != null ? request.getBirthDate() : introduction.getBirthDate(),
                request.getSelfIntroduction() != null ? request.getSelfIntroduction()
                        : introduction.getSelfIntroduction()
        );
    }

    public void delete() {
        isDeleted = true;
    }
}
