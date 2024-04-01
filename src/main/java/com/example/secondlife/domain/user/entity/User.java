package com.example.secondlife.domain.user.entity;

import com.example.secondlife.common.base.BaseTimeEntity;
import com.example.secondlife.domain.user.dto.JoinResponse;
import com.example.secondlife.domain.user.dto.UpdateUserRequest;
import com.example.secondlife.domain.user.dto.UpdateUserRole;
import com.example.secondlife.domain.user.dto.UserResponse;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
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

    private boolean isVerified;

    private boolean isDeleted;

    @Builder
    public User(String loginId, String password, String nickname, String email, Introduction introduction, Role role) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.introduction = introduction;
        this.role = role;
    }

    public User(BaseTimeEntityBuilder<?, ?> b, String loginId, String password, String nickname, String email,
                Introduction introduction) {
        super(b);
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.introduction = introduction;
    }

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

    public void updateUserProfile(UpdateUserRequest request) {
        nickname = request.getNickname().isEmpty() ? nickname : request.getNickname();
        password = request.getPassword().isEmpty() ? password : request.getPassword();
        introduction = new Introduction(
                request.getRegion() != null ? request.getRegion() : introduction.getRegion(),
                request.getBirthDate() != null ? request.getBirthDate() : introduction.getBirthDate(),
                request.getSelfIntroduction().isEmpty() ? introduction.getSelfIntroduction()
                        : request.getSelfIntroduction()
        );
    }

    public void updateUserRole(UpdateUserRole request) {
        role = request.getRole() != null ? request.getRole() : role;
    }

    public void delete() {
        isDeleted = true;
    }

    public UserResponse userResponse() {

        return UserResponse.builder()
                .userId(id)
                .nickname(nickname)
                .email(email)
                .region(introduction.getRegion())
                .birthDate(introduction.getBirthDate())
                .selfIntroduction(introduction.getSelfIntroduction())
                .role(role)
                .isDeleted(isDeleted)
                .verified(isVerified)
                .lastModifiedDate(getLastModifiedDate())
                .build();

    }

    public void updateVerify(String email) {
        isVerified = true;
        this.email = email;
        if (this.role == Role.L1) {
            this.role = Role.L2;
        }
    }
}
