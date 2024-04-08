package com.example.secondlife.domain.user.controller.api;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.secondlife.BasicCRUDTest;
import com.example.secondlife.domain.user.dto.JoinRequest;
import com.example.secondlife.domain.user.dto.JoinResponse;
import com.example.secondlife.domain.user.dto.ProfileResponse;
import com.example.secondlife.domain.user.dto.UpdateUserRequest;
import com.example.secondlife.domain.user.dto.UpdateUserRoleRequest;
import com.example.secondlife.domain.user.dto.UserResponse;
import com.example.secondlife.domain.user.service.UserSearchService;
import com.example.secondlife.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

class UserControllerTest extends BasicCRUDTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserSearchService userSearchService;


    @DisplayName("회원 가입 등록(POST) 요청 테스트, 적합한 URL로 요청 시 성공")
    @WithAnonymousUser
    @Test
    void joinTestWithValidURL() throws Exception {
        //given
        String url = "/api/users";
        JoinRequest request = new JoinRequest();
        JoinResponse response = new JoinResponse();
        given(userService.save(request)).willReturn(response);

        //when
        ResultActions result = doPost(url, request);

        //then
        assertAll(
                () -> result.andExpect(status().isCreated()).andDo(print()),
                () -> result.andExpect(jsonPath("$.userId").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.loginId").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.nickname").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.region").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.birthDate").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.selfIntroduction").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.role").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.createdDate").hasJsonPath()),
                () -> verify(userService).save(request)
        );
    }

    @DisplayName("회원 가입 등록(POST) 요청 테스트, 부적합한 URL로 요청 시 상태코드 404 반환")
    @WithAnonymousUser
    @Test
    void joinTestInvalidURL() throws Exception {
        //given
        String url = "/api/us11";
        JoinRequest request = new JoinRequest();
        JoinResponse response = new JoinResponse();
        given(userService.save(request)).willReturn(response);

        //when
        ResultActions result = doPost(url, request);

        //then
        result.andExpect(status().isNotFound()).andDo(print());
    }


    @DisplayName("유저 프로필 조회(GET) 요청 테스트")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    @WithMockUser(roles = {"ADMIN"})
    void getUserProfileWithADMIN(Long userId) throws Exception {
        //given
        String url = "/api/users/{userId}";
        ProfileResponse profileResponse = new ProfileResponse();
        given(userSearchService.getProfile(userId)).willReturn(profileResponse);

        //when
        ResultActions result = doGet(url, userId);

        //then
        assertAll(
                () -> result.andExpect(status().isOk()).andDo(print()),
                () -> result.andExpect(jsonPath("$.userId").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.nickname").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.email").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.region").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.birthDate").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.selfIntroduction").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.role").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.verified").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.postCount").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.commentCount").hasJsonPath()),
                () -> verify(userSearchService).getProfile(userId)
        );
    }

    @DisplayName("유저 프로필 조회(GET) 요청 테스트, 권한 없이 요청을 수행 시 GlobalExceptionHandler로 인하여 상태코드 401 반환")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void getUserProfileWithoutAuthorization(Long userId) throws Exception {
        //given
        String url = "/api/users/{userId}";
        ProfileResponse profileResponse = new ProfileResponse();
        given(userSearchService.getProfile(userId)).willReturn(profileResponse);

        //when
        ResultActions result = doGet(url, userId);

        //then
        result.andExpect(status().isUnauthorized()).andDo(print());
    }

    @DisplayName("유저 정보 업데이트(PATCH) 요청 테스트")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    @WithMockUser(roles = {"L1", "L2", "ADMIN"})
    void updateUserProfile(Long userId) throws Exception {
        //given
        String url = "/api/users/{userId}";
        UpdateUserRequest request = new UpdateUserRequest();
        UserResponse response = new UserResponse();
        given(userService.updateProfile(userId, request)).willReturn(response);

        //when
        ResultActions result = doPatch(url, userId, request);

        //then
        assertAll(
                () -> result.andExpect(status().isOk()).andDo(print()),
                () -> result.andExpect(jsonPath("$.userId").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.nickname").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.email").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.region").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.birthDate").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.selfIntroduction").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.role").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.deleted").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.verified").hasJsonPath()),
                () -> verify(userService).updateProfile(userId, request)
        );
    }

    @DisplayName("유저 등급 업데이트(PATCH) 요청 테스트")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    @WithMockUser(roles = "ADMIN")
    void updateUserRole(Long userId) throws Exception {
        //given
        String url = "/api/users/role/{userId}";
        UpdateUserRoleRequest request = new UpdateUserRoleRequest();
        UserResponse response = new UserResponse();
        given(userService.updateRole(userId, request)).willReturn(response);

        //when
        ResultActions result = doPatch(url, userId, request);

        //then
        assertAll(
                () -> result.andExpect(status().isOk()).andDo(print()),
                () -> result.andExpect(jsonPath("$.userId").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.nickname").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.email").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.region").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.birthDate").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.selfIntroduction").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.role").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.deleted").hasJsonPath()),
                () -> result.andExpect(jsonPath("$.verified").hasJsonPath()),
                () -> verify(userService).updateRole(userId, request)
        );
    }

    @DisplayName("회원 탈퇴(DELETE) 요청 테스트")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    @WithMockUser(roles = {"L1", "L2", "ADMIN"})
    void deleteUser(Long userId) throws Exception {
        //given
        String url = "/api/users/{userId}";

        //when
        ResultActions result = doDelete(url, userId);

        //then
        assertAll(
                () -> result.andExpect(status().isNoContent()).andDo(print()),
                () -> verify(userService).delete(userId)
        );
    }

}