package com.example.secondlife.domain.post.service;

import com.example.secondlife.domain.post.dto.PostRequest;
import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.repository.PostRepository;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.service.UserSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserSearchService userSearchService;
    private final PostSearchService postSearchService;

    public PostResponse save(Long userId, PostRequest request) {
        log.info("save");

        Post savedPost = postRepository.save(postRequestToPost(userId, request));

        return savedPost.toPostResponse();
    }

    public PostResponse updatePost(Long userId, Long postId, PostRequest request) {
        log.info("updatePost");

        Post findPost = postSearchService.findById(postId);

        validUser(userId, findPost);

        findPost.update(request);

        return findPost.toPostResponse();
    }

    public void deletePost(Long userId, Long postId) {
        log.info("deletePost");

        Post findPost = postSearchService.findById(postId);

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

        User findUser = userSearchService.findById(userId);

        return Post.builder()
                .user(findUser)
                .title(request.getTitle())
                .contents(request.getContents())
                .forum(request.getForum())
                .build();

    }

}
