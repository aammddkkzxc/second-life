package com.example.secondlife.domain.post.service;

import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.service.CommentSearchService;
import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.enumType.Forum;
import com.example.secondlife.domain.post.repository.PostRepository;
import com.example.secondlife.domain.user.enumType.Region;
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
    private final CommentSearchService commentSearchService;

    public Page<PostResponse> getPostsByForumAndRegion(Forum forum, Region region, Pageable pageable) {
        log.info("getPostsByForumAndRegion");

        Page<Post> posts = postRepository.findAllByForumAndUserRegion(forum, region, pageable);

        return posts.map(this::postToPostResponse);
    }

    public Page<PostResponse> getPostsByForum(Forum forum, Pageable pageable) {
        log.info("getPostsByForum");

        Page<Post> posts = postRepository.findAllByForum(forum, pageable);

        return posts.map(this::postToPostResponse);
    }

    public Page<PostResponse> getPosts(Pageable pageable) {
        log.info("getPosts");

        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(this::postToPostResponse);
    }

    public Page<PostResponse> getPostsByUserId(Pageable pageable, Long userId) {
        log.info("getPostsByUserId");

        Page<Post> posts = postRepository.findAllByUserId(userId, pageable);

        return posts.map(this::postToPostResponse);
    }

    public PostResponse readWithCommentsAndCommentLikes(Long postId) {
        log.info("readWithComments");

        Post post = findById(postId);

        List<CommentResponse> commentResponses = commentSearchService.getCommentsWithCommentLikes(postId);

        return postWithCommentCommentLikesToPostResponse(post, commentResponses);
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

    private PostResponse postWithCommentCommentLikesToPostResponse(Post post, List<CommentResponse> comments) {

        PostResponse postResponse = postToPostResponse(post);
        postResponse.setCommentResponses(comments);
        return postResponse;

    }

    private PostResponse postToPostResponse(Post post) {

        return PostResponse.builder()
                .userId(post.getUser().getId())
                .postId(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .hits(post.getHits())
                .createdDate(post.getCreatedDate())
                .lastModifiedDate(post.getLastModifiedDate())
                .createdBy(post.getCreatedBy())
                .lastModifiedBy(post.getLastModifiedBy())
                .build();

    }
}
