package com.example.secondlife.domain.user.controller.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.secondlife.domain.user.dto.UserResponse;
import com.example.secondlife.domain.user.service.UserSearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private UserSearchService userSearchService;

    @Mock
    private Model model;

    @InjectMocks
    private AdminController adminController;

    @DisplayName("키워드(nickName)로 사용자 정보를 얻고, 이를 모델에 담아서 html/admin 페이지를 렌더링")
    @Test
    public void admin() {
        //given
        String keyword = "testKeyword";
        UserResponse userResponse = new UserResponse();
        given(userSearchService.searchByNickName(anyString())).willReturn(userResponse);

        //when
        String viewName = adminController.admin(keyword, model);

        //then
        assertAll(
                () -> assertThat(viewName).isNotNull(),
                () -> assertThat(viewName).isEqualTo("html/admin"),
                () -> verify(userSearchService, times(1)).searchByNickName(keyword),
                () -> verify(model, times(1)).addAttribute("user", userResponse)
        );
    }
}