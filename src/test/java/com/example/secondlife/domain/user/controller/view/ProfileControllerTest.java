package com.example.secondlife.domain.user.controller.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.service.PostSearchService;
import com.example.secondlife.domain.user.dto.ProfileResponse;
import com.example.secondlife.domain.user.service.UserSearchService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.Model;

@ExtendWith(MockitoExtension.class)
public class ProfileControllerTest {

    @Mock
    private UserSearchService userSearchService;

    @Mock
    private PostSearchService postSearchService;

    @Mock
    private Model model;

    @InjectMocks
    private ProfileController profileController;

    @DisplayName("현재 로그인 중인 사용자의 정보와 작성한 포스트에 대한 정보얻고 이를 Model에 담아 html/profile에 랜더링")
    @WithMockUser(roles = {"L1", "L2", "ADMIN"})
    @Test
    public void profile() {
        //given
        Long userId = 123L;
        ProfileResponse profileResponse = new ProfileResponse();
        List<PostResponse> postResponses = List.of(new PostResponse(), new PostResponse());
        Page<PostResponse> postPage = new PageImpl<>(postResponses);
        given(userSearchService.getProfile(anyLong())).willReturn(profileResponse);
        given(postSearchService.getPostsByUserId(any(Pageable.class), anyLong())).willReturn(postPage);

        //when
        String viewName = profileController.profile(userId, model, PageRequest.of(0, 5));

        //then
        assertAll(
                () -> assertThat(viewName).isEqualTo("html/profile"),
                () -> verify(userSearchService, times(1)).getProfile(userId),
                () -> verify(postSearchService, times(1)).getPostsByUserId(any(Pageable.class), anyLong()),
                () -> verify(model, times(1)).addAttribute("user", profileResponse),
                () -> verify(model, times(1)).addAttribute("posts", postResponses)
        );
    }

    @Test
    @DisplayName("프로필 업데이트 페이지 렌더링 테스트")
    public void testUpdateProfilePageRendering() {
        //when
        String viewName = profileController.updateProfile();

        //then
        assertThat(viewName).isEqualTo("html/user-update");
    }

}