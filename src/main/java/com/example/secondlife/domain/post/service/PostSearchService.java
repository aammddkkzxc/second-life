package com.example.secondlife.domain.post.service;

import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.service.CommentSearchService;
import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.enumType.Forum;
import com.example.secondlife.domain.post.repository.PostRepository;
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

    public Page<PostResponse> getPosts(Forum forum, Pageable pageable) {
        log.info("getPosts");

        Page<Post> posts = postRepository.findAllByForum(forum, pageable);

        return posts.map(this::postToPostResponse);
    }

    public Page<PostResponse> getPostsByUserId(Pageable pageable, Long userId) {
        log.info("getPostsByUserId");

        Page<Post> posts = postRepository.findAllByUserId(userId, pageable);

        return posts.map(this::postToPostResponse);
    }

    public PostResponse readWithComments(Long postId) {
        log.info("readWithComments");

        Post post = findById(postId);

        List<CommentResponse> comments = commentSearchService.getComments(postId);

        return postWithCommentToPostResponse(post, comments);
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

    private PostResponse postWithCommentToPostResponse(Post post, List<CommentResponse> comments) {

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
