package com.example.secondlife.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.secondlife.domain.user.dto.JoinRequest;
import com.example.secondlife.domain.user.dto.JoinResponse;
import com.example.secondlife.domain.user.dto.UserInfo;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.enumType.Region;
import com.example.secondlife.domain.user.repository.UserRepository;
import java.time.LocalDate;
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
    private UserRepository userRepository;

    @Test
    void saveAndFind() {
        JoinResponse joinResponse = saveMember();

        User byId = userService.findById(1L);

        assertEquals(joinResponse.getUserId(), byId.getId());
        assertEquals(joinResponse.getLoginId(), byId.getLoginId());
        assertEquals(joinResponse.getNickname(), byId.getNickname());
        assertEquals(joinResponse.getEmail(), byId.getEmail());
        assertEquals(joinResponse.getRegion(), byId.getIntroduction().getRegion());
        assertEquals(joinResponse.getBirthDate(), byId.getIntroduction().getBirthDate());
        assertEquals(joinResponse.getSelfIntroduction(), byId.getIntroduction().getSelfIntroduction());
    }

    @Test
    void getUserInfo() {
        JoinResponse joinResponse = saveMember();

        UserInfo userInfo = userService.getUserInfo(joinResponse.getUserId());

        assertEquals(joinResponse.getNickname(), userInfo.getNickname());
        assertEquals(joinResponse.getRegion(), userInfo.getRegion());
        assertEquals(joinResponse.getBirthDate(), userInfo.getBirthDate());
        assertEquals(joinResponse.getSelfIntroduction(), userInfo.getSelfIntroduction());
    }

    @Test
    void updateUserInfo() {
        JoinResponse joinResponse = saveMember();

        UserInfo userInfo = UserInfo.builder()
                .nickname("newNickname")
                .region(Region.JEJU)
                .birthDate(LocalDate.of(1991, 1, 1))
                .selfIntroduction("Hello, I'm a new user.")
                .build();

        userService.updateUserInfo(joinResponse.getUserId(), userInfo);
        User byId = userService.findById(joinResponse.getUserId());

        assertEquals(userInfo.getNickname(), byId.getNickname());
        assertEquals(userInfo.getRegion(), byId.getIntroduction().getRegion());
        assertEquals(userInfo.getBirthDate(), byId.getIntroduction().getBirthDate());
        assertEquals(userInfo.getSelfIntroduction(), byId.getIntroduction().getSelfIntroduction());
    }

    @Test
    void deleteUser() {
        JoinResponse joinResponse = saveMember();

        userService.deleteUser(joinResponse.getUserId());

        User user = userRepository.findById(joinResponse.getUserId()).orElse(null);

        assert user != null;
        assertTrue(user.isDeleted());
    }

    private JoinResponse saveMember() {
        JoinRequest joinRequest = JoinRequest.builder()
                .loginId("testLoginId")
                .password("testPassword")
                .nickname("testName")
                .email("testEmail@test.com")
                .region(Region.SEOUL)
                .birthDate(LocalDate.of(1990, 1, 1))
                .selfIntroduction("Hello, I'm a test user.")
                .build();

        return userService.save(joinRequest);
    }
}