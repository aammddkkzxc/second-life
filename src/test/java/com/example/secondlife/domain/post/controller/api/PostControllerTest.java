package com.example.secondlife.domain.post.controller.api;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.secondlife.BasicCRUDTest;
import com.example.secondlife.domain.post.dto.PostDto;
import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.enumType.Forum;
import com.example.secondlife.domain.post.service.PostService;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.service.UserSearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

public class PostControllerTest extends BasicCRUDTest {

    @MockBean
    private PostService postService;

    @MockBean
    private UserSearchService userSearchService;

    @DisplayName("게시글 작성 요청 테스트")
    @Test
    void writePostTest() throws Exception {
        //given
        Long userId = 123L;
        PostDto request = PostDto.builder().forum(Forum.FREE).build();
        String url = "/posts";
        PostResponse postResponse = postService.save(userId, request);
        given(postService.save(userId, request)).willReturn(
                postResponse);
        Post post = postRequestToPost(userId, request);

        //when
        ResultActions result = doPost(url, post);

        //then
        assertAll(
                () -> result.andExpect(status().is3xxRedirection()),
                () -> verify(postService).save(userId, request)
        );
    }

    private Post postRequestToPost(Long userId, PostDto request) {
        User findUser = userSearchService.findById(userId);

        return Post.builder()
                .user(findUser)
                .title(request.getTitle())
                .contents(request.getContents())
                .forum(request.getForum())
                .build();
    }

}