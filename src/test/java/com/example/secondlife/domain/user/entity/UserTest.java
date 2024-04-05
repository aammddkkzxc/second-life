package com.example.secondlife.domain.user.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.secondlife.domain.user.dto.UpdateUserRequest;
import com.example.secondlife.domain.user.dto.UpdateUserRoleRequest;
import com.example.secondlife.domain.user.enumType.Region;
import com.example.secondlife.domain.user.enumType.Role;
import com.example.secondlife.domain.user.valueType.Introduction;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {
    private User user;
    private final LocalDate localDateEx = LocalDate.of(2024, 4, 5);

    @BeforeEach
    void setUp() {
        user = User.builder()
                .nickname("OldNickname")
                .password("OldPassword")
                .introduction(new Introduction(Region.CHUNGNAM, localDateEx, "OldIntroduction"))
                .role(Role.L1)
                .build();

    }

    @DisplayName("회원 프로필 정보를 업데이트 한다. 업데이트 하는 내역이 존재하는 항목만 업데이트, 나머지는 그대로 유지")
    @Test
    void updateUserProfile() {
        //given
        UpdateUserRequest request = new UpdateUserRequest();
        request.setNickname("NewNickname");

        //when
        user.updateUserProfile(request);

        //then
        assertAll(
                () -> assertThat(user.getNickname()).isEqualTo("NewNickname"),
                () -> assertThat(user.getPassword()).isEqualTo("OldPassword"),
                () -> assertThat(user.getIntroduction().getRegion()).isEqualTo(Region.CHUNGNAM),
                () -> assertThat(user.getIntroduction().getBirthDate()).isEqualTo(localDateEx),
                () -> assertThat(user.getIntroduction().getSelfIntroduction()).isEqualTo("OldIntroduction")
        );
    }

    @DisplayName("회원 Role 변경내역이 있을 경우 업데이트 한다")
    @Test
    void updateUserRoleWithChanges() {
        //given
        UpdateUserRoleRequest requestWithRole = new UpdateUserRoleRequest();
        requestWithRole.setRole(Role.ADMIN);

        // When
        user.updateUserRole(requestWithRole);

        // Then
        assertThat(user.getRole()).isEqualTo(Role.ADMIN);
    }

    @DisplayName("회원을 삭제할 경우 boolean 필드 isDeleted 를 true로 설정한다")
    @Test
    void delete() {
        // When
        user.delete();

        // Then
        assertTrue(user.isDeleted()); // 삭제되었는지 확인
    }

    @DisplayName("이메일 인증을 하면 Role 승격")
    @Test
    void updateVerify() {
        // When
        user.updateVerify("example@example.com");

        // Then
        assertAll("User verification",
                () -> assertThat(user.isVerified()).isEqualTo(true),
                () -> assertThat(user.getEmail()).isEqualTo("example@example.com"),
                () -> assertThat(user.getRole()).isEqualTo(Role.L2)
        );
    }
}