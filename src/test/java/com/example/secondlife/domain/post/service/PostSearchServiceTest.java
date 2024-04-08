package com.example.secondlife.domain.post.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.secondlife.domain.post.dto.HotPostDto;
import com.example.secondlife.domain.post.repository.PostRepository;
import com.example.secondlife.domain.user.enumType.Region;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class PostSearchServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostSearchService postSearchService;

    @DisplayName("findHotPostsNative를 통해 쿼리의 결과 각 요소 값을 매핑하여 List<HotPostDto> 로 리턴")
    @Test
    void getHotPosts() {
        // given 데이터 예시
        Object[] post1 = new Object[]{1L, "Post 1", 100, Timestamp.valueOf("2024-04-01 12:00:00"), 50L, 20L, "SEOUL"};
        Object[] post2 = new Object[]{2L, "Post 2", 150, Timestamp.valueOf("2024-04-02 12:00:00"), 60L, 25L, "GANGWON"};
        List<Object[]> dataBefore = new ArrayList<>();
        dataBefore.add(post1);
        dataBefore.add(post2);
        given(postRepository.findHotPostsNative(any(LocalDateTime.class))).willReturn(dataBefore);
        // given 예상 결과
        List<HotPostDto> expected;
        expected = new ArrayList<>();
        expected.add(new HotPostDto(1L, "Post 1", 100, LocalDateTime.of(2024, 4, 1, 12, 0, 0), 50L, 20L, Region.SEOUL));
        expected.add(
                new HotPostDto(2L, "Post 2", 150, LocalDateTime.of(2024, 4, 2, 12, 0, 0), 60L, 25L, Region.GANGWON));

        // when & then
        List<HotPostDto> hotPosts = postSearchService.getHotPosts();
        assertAll(
                () -> assertEquals(expected.size(), hotPosts.size()),
                () -> {
                    for (int i = 0; i < expected.size(); i++) {
                        assertEquals(expected.get(i), hotPosts.get(i));
                    }
                }
        );
    }

}