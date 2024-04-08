package com.example.secondlife.domain.post.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.secondlife.domain.post.dto.PostDto;
import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.enumType.Forum;
import com.example.secondlife.domain.post.repository.PostRepository;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.service.UserSearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserSearchService userSearchService;

    @InjectMocks
    private PostService postService;

    @Test
    void save_PostSavedSuccessfully_ReturnsPostResponse() {
        // Given
        Long userId = 1L;
        PostDto request = new PostDto("Title", "Contents", Forum.FREE);
        User findUser = new User(); // Mocking required objects
        when(userSearchService.findById(userId)).thenReturn(findUser);
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        PostResponse result = postService.save(userId, request);

        // Then
        assertNotNull(result);
        assertEquals("Title", result.getTitle());
        assertEquals("Contents", result.getContents());
    }

}