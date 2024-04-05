package com.example.secondlife.domain.user.entity;

import com.example.secondlife.common.base.BaseTimeEntity;
import com.example.secondlife.domain.user.dto.UpdateUserRequest;
import com.example.secondlife.domain.user.dto.UpdateUserRoleRequest;
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

    @Column(length = 20, nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String nickname;

    @Column(length = 50)
    private String email;

    @Embedded
    private Introduction introduction;

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

    public void updateUserProfile(UpdateUserRequest request) {
        if (request.getNickname() != null && !request.getNickname().isEmpty()) {
            nickname = request.getNickname();
        }
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            password = request.getPassword();
        }

        introduction = new Introduction(
                request.getRegion() != null ? request.getRegion() : introduction.getRegion(),
                request.getBirthDate() != null ? request.getBirthDate() : introduction.getBirthDate(),
                request.getSelfIntroduction() != null && !request.getSelfIntroduction().isEmpty()
                        ? request.getSelfIntroduction()
                        : introduction.getSelfIntroduction()
        );
    }

    public void updateUserRole(UpdateUserRoleRequest request) {
        role = request.getRole() != null ? request.getRole() : role;
    }

    public void delete() {
        isDeleted = true;
    }

    public void updateVerify(String email) {
        isVerified = true;
        this.email = email;
        if (this.role == Role.L1) {
            this.role = Role.L2;
        }
    }
}
