package com.example.secondlife.domain.post.controller.api;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.secondlife.domain.BasicCRUDTest;
import com.example.secondlife.domain.post.dto.PostDto;
import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.enumType.Forum;
import com.example.secondlife.domain.post.service.PostService;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.repository.UserRepository;
import com.example.secondlife.domain.user.service.UserSearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

public class PostControllerTest extends BasicCRUDTest {


    @Mock
    private PostService postService;

    @Mock
    private UserSearchService userSearchService;

    @Autowired
    UserRepository userRepository;

    @DisplayName("게시글 작성 요청 테스트")
    @Test
    void writePostTest() throws Exception {

        Long userId = 123L;
        PostDto request = PostDto.builder().forum(Forum.FREE).build();
        String url = "/posts";
        PostResponse postResponse = postService.save(userId, request);
        given(postService.save(userId, request)).willReturn(
                postResponse);
        Post post = postRequestToPost(userId, request);

        ResultActions result = doPost(url, post);

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