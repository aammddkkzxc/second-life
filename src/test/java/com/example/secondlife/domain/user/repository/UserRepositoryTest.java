package com.example.secondlife.domain.user.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.enumType.Region;
import com.example.secondlife.domain.user.enumType.Role;
import com.example.secondlife.domain.user.valueType.Introduction;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

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

    @DisplayName("존재하는 유저 로그인 아이디로 찾을 시 해당 유저 반환")
    @Test
    void findByLoginIdWithExistingUser() {
        //given
        String loginId = "idExample";

        //when
        Optional<User> optionalUser = userRepository.findByLoginId(loginId);

        //then
        assertAll(
                () -> assertTrue(optionalUser.isPresent()),
                () -> assertThat(user).isEqualTo(optionalUser.get())
        );
    }


    @DisplayName("존재하지 유저 로그인 아이디로 찾을 시 빈 Optional 반환")
    @Test
    void findByLoginIdWithNotExistingUser() {
        //given
        String loginId = "nonExistingUser";

        //when
        Optional<User> optionalUser = userRepository.findByLoginId(loginId);

        //then
        assertTrue(optionalUser.isEmpty());
    }

    @DisplayName("이메일이 존재하는 경우 true를 반환")
    @Test
    void existsByEmailWithExistingEmail() {
        //given
        String email = "test@example.com";

        //when
        boolean exists = userRepository.existsByEmail(email);

        //then
        assertTrue(exists);
    }

    @DisplayName("이메일이 존재하지 않는 경우 false를 반환")
    @Test
    void existsByEmailWithNotExistingEmail() {
        //given
        String email = "nonexisting@example.com";

        //when
        boolean exists = userRepository.existsByEmail(email);

        //then
        assertFalse(exists);
    }

    @DisplayName("존재하는 닉네임으로 유저를 찾을 시 해당 유저 반환")
    @Test
    void findByNicknameContainingWithExistingNickname() {
        //given
        String nickname = "test";

        //when
        Optional<User> optionalUser = userRepository.findByNicknameContaining(nickname);

        //then

        assertAll(
                () -> assertTrue(optionalUser.isPresent()),
                () -> assertThat(user).isEqualTo(optionalUser.get())
        );
    }

    @DisplayName("존재하지 않는 닉네임으로 유저를 찾을 시 빈 Optional 반환")
    @Test
    void findByNicknameContainingWithNotExistingNickname() {
        //given
        String partialNickname = "nonExisting";

        //when
        Optional<User> optionalUser = userRepository.findByNicknameContaining(partialNickname);

        //then
        assertTrue(optionalUser.isEmpty());
    }

}