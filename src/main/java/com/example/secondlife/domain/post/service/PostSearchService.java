package com.example.secondlife.domain.post.service;

import com.example.secondlife.common.exception.NotFoundException;
import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.service.CommentSearchService;
import com.example.secondlife.domain.like.post.repository.PostLikeRepository;
import com.example.secondlife.domain.post.dto.HotPostDto;
import com.example.secondlife.domain.post.dto.PostDto;
import com.example.secondlife.domain.post.dto.PostDtoUtil;
import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.enumType.Forum;
import com.example.secondlife.domain.post.repository.PostRepository;
import com.example.secondlife.domain.user.enumType.Region;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostSearchService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentSearchService commentSearchService;

    public Page<PostResponse> getPostsByForumAndRegion(Forum forum, Region region, Pageable pageable) {
        Page<Post> posts = postRepository.findAllByForumAndUserRegionAndIsDeletedFalse(forum, region, pageable);

        return posts.map(PostDtoUtil::postToPostResponse);
    }

    public Page<PostResponse> getPostsByForum(Forum forum, Pageable pageable) {
        Page<Post> posts = postRepository.findAllByForumAndIsDeletedFalse(forum, pageable);

        return posts.map(PostDtoUtil::postToPostResponse);
    }

    public Page<PostResponse> getPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(PostDtoUtil::postToPostResponse);
    }

    public List<HotPostDto> getHotPosts() {
        LocalDateTime lastSevenDays = LocalDateTime.now().minusDays(7);

        return postRepository.findHotPostsNative(lastSevenDays).stream()
                .map(obj -> {
                    Long postId = ((Number) obj[0]).longValue();
                    String title = (String) obj[1];
                    int hits = ((Number) obj[2]).intValue();
                    LocalDateTime createdDate = ((Timestamp) obj[3]).toLocalDateTime();
                    Long likeCount = ((Number) obj[4]).longValue();
                    Long commentCount = ((Number) obj[5]).longValue();
                    Region region = Region.valueOf((String) obj[6]);

                    return new HotPostDto(postId, title, hits, createdDate, likeCount, commentCount, region);
                })
                .collect(Collectors.toList());

    }

    public Page<PostResponse> getPostsByUserId(Pageable pageable, Long userId) {
        Page<Post> posts = postRepository.findAllByUserIdAndIsDeletedFalse(userId, pageable);

        return posts.map(PostDtoUtil::postToPostResponse);
    }

    public PostResponse readWithCommentsAndCommentLikes(Long postId) {
        Post findPost = findById(postId);

        List<CommentResponse> commentResponses = commentSearchService.getCommentsWithCommentLikes(postId);

        PostResponse postResponse = PostDtoUtil.postWithCommentResponseToPostResponse(findPost, commentResponses);

        postResponse.setLikeCount(getLikeCount(findPost));

        return postResponse;
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다. postId = " + postId));
    }

    public Long getLikeCount(Post post) {
        Long postId = post.getId();

        return postLikeRepository.countLikesByPostId(postId);
    }

    public String findContentsById(Long postId) {
        return postRepository.findContentsById(postId)
                .orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다. postId = " + postId));
    }

    public PostDto getPostDtoByPostId(Long postId) {
        Post post = findById(postId);

        return PostDto.builder()
                .title(post.getTitle())
                .contents(post.getContents())
                .forum(post.getForum())
                .build();
    }
}
