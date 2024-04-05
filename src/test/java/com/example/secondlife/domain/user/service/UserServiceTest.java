package com.example.secondlife.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.secondlife.domain.user.dto.JoinRequest;
import com.example.secondlife.domain.user.dto.JoinResponse;
import com.example.secondlife.domain.user.dto.UpdateUserRequest;
import com.example.secondlife.domain.user.dto.UpdateUserRoleRequest;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.enumType.Region;
import com.example.secondlife.domain.user.enumType.Role;
import com.example.secondlife.domain.user.repository.UserRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSearchService userSearchService;

    @Autowired
    private UserRepository userRepository;

    private JoinResponse joinResponse;

    @BeforeEach
    void setUpUser() {
        JoinRequest joinRequest = JoinRequest.builder()
                .loginId("testLoginId")
                .password("testPassword")
                .nickname("testName")
                .region(Region.SEOUL)
                .birthDate(LocalDate.of(1990, 1, 1))
                .selfIntroduction("Hello, I'm a test user.")
                .build();

        joinResponse = userService.save(joinRequest);
    }

    @AfterEach
    void deleteUser() {
        userService.delete(joinResponse.getUserId());

        User user = userRepository.findById(joinResponse.getUserId()).orElse(null);

        assert user != null;
        assertTrue(user.isDeleted());
    }

    @DisplayName("setUpUser() 통해 저장된 user를 확인한다")
    @Test
    void checkSavedUser() {
        //when
        User savedUser = userSearchService.findById(joinResponse.getUserId());

        //then
        assertAll(
                () -> assertEquals(joinResponse.getUserId(), savedUser.getId()),
                () -> assertEquals(joinResponse.getLoginId(), savedUser.getLoginId()),
                () -> assertEquals(joinResponse.getNickname(), savedUser.getNickname()),
                () -> assertEquals(joinResponse.getRegion(), savedUser.getIntroduction().getRegion()),
                () -> assertEquals(joinResponse.getBirthDate(), savedUser.getIntroduction().getBirthDate()),
                () -> assertEquals(joinResponse.getSelfIntroduction(), savedUser.getIntroduction().getSelfIntroduction())
        );
    }

    @DisplayName("유저 프로필 업데이트 request dto 사용하여 유저 정보 업데이트 후 response dto 반환")
    @Test
    void updateProfile() {
        //given
        UpdateUserRequest request = UpdateUserRequest.builder()
                .nickname("newNickname")
                .region(Region.JEJU)
                .birthDate(LocalDate.of(1991, 1, 1))
                .selfIntroduction("hello everyone")
                .build();

        //when
        userService.updateProfile(joinResponse.getUserId(), request);
        User updatedUser = userSearchService.findById(joinResponse.getUserId());

        //then
        assertAll(
                () -> assertThat(updatedUser.getNickname()).isEqualTo(request.getNickname()),
                () -> assertThat(updatedUser.getIntroduction().getRegion()).isEqualTo(request.getRegion()),
                () -> assertThat(updatedUser.getIntroduction().getBirthDate()).isEqualTo(request.getBirthDate()),
                () -> assertThat(updatedUser.getIntroduction().getSelfIntroduction()).isEqualTo(request.getSelfIntroduction())
        );
    }

    @DisplayName("유저 Role 업데이트 request dto 사용하여 유저 정보 업데이트 후 response dto 반환")
    @Test
    void updateRole() {
        //given
        UpdateUserRoleRequest request = UpdateUserRoleRequest.builder().role(Role.L2).build();

        //when
        userService.updateRole(joinResponse.getUserId(), request);
        User updatedUser = userSearchService.findById(joinResponse.getUserId());

        //then
        assertThat(updatedUser.getRole()).isEqualTo(request.getRole());
    }

    @Test
    void updateVerify() {
        //given
        String email = "test@example.com";

        //when & then
        assertDoesNotThrow(() -> userService.updateVerify(joinResponse.getUserId(), email));
    }

    @Test
    void delete() {
        //when & then
        assertDoesNotThrow(() -> userService.delete(joinResponse.getUserId()));
    }
}