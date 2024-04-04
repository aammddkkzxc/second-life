package com.example.secondlife.domain.user.controller.api;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.secondlife.domain.BasicCRUDTest;
import com.example.secondlife.domain.user.dto.JoinRequest;
import com.example.secondlife.domain.user.dto.JoinResponse;
import com.example.secondlife.domain.user.dto.ProfileResponse;
import com.example.secondlife.domain.user.dto.UpdateUserRequest;
import com.example.secondlife.domain.user.dto.UpdateUserRole;
import com.example.secondlife.domain.user.dto.UserResponse;
import com.example.secondlife.domain.user.service.UserSearchService;
import com.example.secondlife.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;


class UserControllerTest extends BasicCRUDTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserSearchService userSearchService;


    @DisplayName("회원 가입 등록(POST) 요청 테스트")
    @Test
    void join() throws Exception {

        //given
        String url = "/api/users";
        JoinRequest request = new JoinRequest();
        JoinResponse response = new JoinResponse();
        given(userService.save(request)).willReturn(response);

        //when
        ResultActions result = doPost(url, request);

        //then
        result.andExpect(status().isCreated()).andDo(print());

    }


    @DisplayName("유저 프로필 조회(GET) 요청 테스트")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    @WithMockUser(roles = "ADMIN")
    void getUserProfileWithADMIN(Long userId) throws Exception {

        //given
        String url = "/api/users/{userId}";
        ProfileResponse profileResponse = new ProfileResponse();
        given(userSearchService.getProfile(userId)).willReturn(profileResponse);

        //when
        ResultActions result = doGet(url, userId);

        //then
        result.andExpect(status().isOk()).andDo(print());

    }

    //예상한 대로 예외가 발생하는데 왜 테스트 실패하는지 전혀 모르겠음
    @DisplayName("유저 프로필 조회(GET) 요청 테스트 권한 없이 요청을 수행하려 하면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void getUserProfileWithoutAuthorization(Long userId) {

        //given
        String url = "/api/users/{userId}";
        ProfileResponse profileResponse = new ProfileResponse();
        given(userSearchService.getProfile(userId)).willReturn(profileResponse);

        // when & then
        assertThatThrownBy(() -> doGet(url, userId))
                .isInstanceOf(AccessDeniedException.class);
    }

    @DisplayName("유저 정보 업데이트(PATCH) 요청 테스트")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    @WithMockUser(roles = {"ADMIN"})
    void updateUserProfile(Long userId) throws Exception {

        //given
        String url = "/api/users/role/{userId}";
        UpdateUserRequest request = new UpdateUserRequest();
        UserResponse response = new UserResponse();
        given(userService.updateProfile(userId, request)).willReturn(response);

        //when
        ResultActions result = doPatch(url, userId, request);

        //then
        result.andExpect(status().isOk()).andDo(print());

    }

    @DisplayName("유저 등급 업데이트(PATCH) 요청 테스트")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    @WithMockUser(roles = "ADMIN")
    void updateUserRole(Long userId) throws Exception {

        //given
        String url = "/api/users/role/{userId}";
        UpdateUserRole request = new UpdateUserRole();
        UserResponse response = new UserResponse();
        given(userService.updateRole(userId, request)).willReturn(response);

        //when
        ResultActions result = doPatch(url, userId, request);

        //then
        result.andExpect(status().isOk()).andDo(print());

    }

    @DisplayName("유저 등급 업데이트(PATCH) 요청 테스트")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    @WithMockUser(roles = "ADMIN")
    void deleteUser() {
    }
}