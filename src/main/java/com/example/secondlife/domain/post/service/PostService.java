package com.example.secondlife.domain.post.service;

import com.example.secondlife.common.service.PostCommentService;
import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.post.dto.PostRequest;
import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.repository.PostRepository;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final PostCommentService postCommentService;

    public PostResponse save(Long userId, PostRequest request) {
        log.info("save");

        Post savedPost = postRepository.save(postRequestToPost(userId, request));

        return postToPostResponse(savedPost);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getPosts(Pageable pageable) {
        log.info("getPosts");

        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(this::postToPostResponse);
    }

    @Transactional(readOnly = true)
    public PostResponse readWithComments(Long postId) {
        log.info("readWithComments");

        Post post = postCommentService.findPostById(postId);

        List<CommentResponse> comments = postCommentService.getComments(postId);

        return postWithCommentToPostResponse(post, comments);
    }


    public PostResponse updatePost(Long userId, Long postId, PostRequest request) {
        log.info("updatePost");

        Post findPost = postCommentService.findPostById(postId);

        validUser(userId, findPost);

        findPost.update(request);

        return postToPostResponse(findPost);
    }

    public void deletePost(Long userId, Long postId) {
        log.info("deletePost");

        Post findPost = postCommentService.findPostById(postId);

        validUser(userId, findPost);

        findPost.delete();
    }

    private void validUser(Long userId, Post findPost) {
        Long postUserId = findPost.getUser().getId();

        if (!postUserId.equals(userId)) {
            throw new AccessDeniedException("자신의 게시글만 조작할 수 있습니다.");
        }
    }

    private Post postRequestToPost(Long userId, PostRequest request) {

        User findUser = userService.findById(userId);

        return Post.builder()
                .user(findUser)
                .title(request.getTitle())
                .contents(request.getContents())
                .isPublic(request.isPublic())
                .forum(request.getForum())
                .build();

    }

    public PostResponse postWithCommentToPostResponse(Post post, List<CommentResponse> comments) {

        PostResponse postResponse = postToPostResponse(post);
        postResponse.setCommentResponse(comments);
        return postResponse;

    }

    public PostResponse postToPostResponse(Post post) {

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
