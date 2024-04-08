package com.example.secondlife.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.example.secondlife.common.exception.NotFoundException;
import com.example.secondlife.domain.user.dto.ProfileResponse;
import com.example.secondlife.domain.user.dto.UserResponse;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.enumType.Region;
import com.example.secondlife.domain.user.enumType.Role;
import com.example.secondlife.domain.user.repository.UserRepository;
import com.example.secondlife.domain.user.valueType.Introduction;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserSearchServiceTest {

    @Autowired
    private UserSearchService userSearchService;

    @Autowired
    private UserRepository userRepository;

    private User user;

    private final LocalDate localDateEx = LocalDate.of(2024, 4, 5);

    @BeforeEach
    void setUser() {
        user = User.builder()
                .loginId("idExample")
                .nickname("test")
                .password("1234")
                .email("test@example.com")
                .introduction(new Introduction(Region.CHUNGNAM, localDateEx, "hello"))
                .role(Role.L1)
                .build();
        userRepository.save(user);
    }

    @AfterEach
    void clear() {
        userRepository.delete(user);
    }

    @DisplayName("주어진 userId에 해당하는 Profile 존재 시 반환")
    @Test
    void getProfileWithExistingUserID() {
        //given
        Long userId = user.getId();

        // When
        ProfileResponse profile = userSearchService.getProfile(userId);

        // Then
        assertThat(user.getId()).isEqualTo(profile.getUserId());
    }

    @DisplayName("주어진 userId에 해당하는 Profile 없을 시 예외 발생")
    @Test
    void getProfileWithNotExistingUserID() {
        //given
        Long userIdEx = 9552233215151L; // 존재하지 않는 임의의 사용자 ID

        //when & then
        assertThatThrownBy(() -> userSearchService.getProfile(userIdEx)).isInstanceOf(NotFoundException.class);
    }

    @DisplayName("주어진 userId에 해당하는 User 없을 시 예외 발생")
    @Test
    void findByIdWithNotExistingUserId() {
        //given
        Long userIdEx = 9552233215151L; // 존재하지 않는 임의의 사용자 ID

        //when & then
        assertThatThrownBy(() -> userSearchService.findById(userIdEx)).isInstanceOf(NotFoundException.class);
    }

    @DisplayName("주어진 nickName에 해당하는 사용자 존재 시 해당 사용자의 정보를 반환")
    @Test
    void searchByNickName_UserExists() {
        //when
        UserResponse userResponse = userSearchService.searchByNickName("test");

        //then
        assertThat(userResponse.getNickname()).isEqualTo(user.getNickname());
    }

    @DisplayName("주어진 nickName에 해당하는 사용자 없을 시 빈 response 반환")
    @Test
    void searchByNickName_UserNotExist() {
        // given
        String notExistingNickname = "notExisting";

        // when
        UserResponse userResponse = userSearchService.searchByNickName(notExistingNickname);

        // then
        assertAll(
                () -> assertThat(userResponse).isInstanceOf(UserResponse.class),
                () -> assertThat(userResponse.getNickname()).isNull()
        );
    }

    @DisplayName("주어진 email로 등록된 사용자 존재 시 true 반환")
    @Test
    void existByEmail_UserExists() {
        // when
        boolean exists = userSearchService.existByEmail("test@example.com");

        // then
        assertThat(exists).isTrue();
    }
}