package com.example.secondlife.domain.post.service;

import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.service.CommentSearchService;
import com.example.secondlife.domain.like.post.repository.PostLikeRepository;
import com.example.secondlife.domain.post.dto.PostDto;
import com.example.secondlife.domain.post.dto.PostDtoUtil;
import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.enumType.Forum;
import com.example.secondlife.domain.post.repository.PostRepository;
import com.example.secondlife.domain.user.enumType.Region;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostSearchService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentSearchService commentSearchService;

    public Page<PostResponse> getPostsByForumAndRegion(Forum forum, Region region, Pageable pageable) {
        log.info("getPostsByForumAndRegion");

        Page<Post> posts = postRepository.findAllByForumAndUserRegionAndIsDeletedFalse(forum, region, pageable);

        return posts.map(PostDtoUtil::postToPostResponse);
    }

    public Page<PostResponse> getPostsByForum(Forum forum, Pageable pageable) {
        log.info("getPostsByForum");

        Page<Post> posts = postRepository.findAllByForumAndIsDeletedFalse(forum, pageable);

        return posts.map(PostDtoUtil::postToPostResponse);
    }

    public Page<PostResponse> getPosts(Pageable pageable) {
        log.info("getPosts");

        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(PostDtoUtil::postToPostResponse);
    }

    public Page<PostResponse> getPostsByUserId(Pageable pageable, Long userId) {
        log.info("getPostsByUserId");

        Page<Post> posts = postRepository.findAllByUserIdAndIsDeletedFalse(userId, pageable);

        return posts.map(PostDtoUtil::postToPostResponse);
    }

    // 최근 7일의 게시글 중 총 10개의 인기 게시글 뽑음
    // 게시글 추천 수 비교 => 동률 시 조회 수 비교 => 동률 시 포스트 아이디 비교
    public List<PostResponse> getHotPosts() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<Post> lastSevenDaysPosts = postRepository.findPostsLastSevenDays(sevenDaysAgo);

        return lastSevenDaysPosts.stream()
                .map(post -> readWithCommentsAndCommentLikes(post.getId()))
                .sorted(
                        Comparator.comparing(PostResponse::getLikeCount).reversed()
                                .thenComparingInt(PostResponse::getHits).reversed()
                                .thenComparingLong(PostResponse::getPostId).reversed()
                )
                .limit(10).toList();
    }

    public PostResponse readWithCommentsAndCommentLikes(Long postId) {
        log.info("readWithCommentsAndCommentLikes");

        Post findPost = findById(postId);

        List<CommentResponse> commentResponses = commentSearchService.getCommentsWithCommentLikes(postId);

        PostResponse postResponse = PostDtoUtil.postWithCommentLikesToPostResponse(findPost, commentResponses);
        postResponse.setLikeCount(getLikeCount(findPost));

        return postResponse;
    }

    public PostDto getPostDtoByPostId(Long postId) {
        log.info("getPostDtoByPostId");

        Post post = findById(postId);

        return PostDto.builder()
                .title(post.getTitle())
                .contents(post.getContents())
                .forum(post.getForum())
                .build();
    }

    public Post findById(Long postId) {
        log.info("findById");

        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. postId = " + postId));
    }

    public Long getPostCount(Long userId) {
        log.info("getPostCount");

        return postRepository.countByUserId(userId);
    }

    public Long getLikeCount(Post post) {
        Long postId = post.getId();

        return postLikeRepository.countLikesByPostId(postId);
    }

}
