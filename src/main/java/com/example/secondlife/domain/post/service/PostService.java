package com.example.secondlife.domain.post.service;

import com.example.secondlife.common.llm.event.PostCreatedEvent;
import com.example.secondlife.domain.post.dto.PostDto;
import com.example.secondlife.domain.post.dto.PostDtoUtil;
import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.repository.PostRepository;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.service.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserSearchService userSearchService;
    private final PostSearchService postSearchService;

    private final ApplicationEventPublisher publisher;

    public PostResponse save(Long userId, PostDto request) {
        Post savedPost = postRepository.save(postRequestToPost(userId, request));

        publisher.publishEvent(new PostCreatedEvent(this, savedPost.getId()));
        
        return PostDtoUtil.postToPostResponse(savedPost);
    }

    public void incrementViewCount(Long postId) {
        Post findPost = postSearchService.findById(postId);

        findPost.increaseViewCount();
    }

    public PostResponse update(Long userId, Long postId, PostDto request) {
        Post findPost = postSearchService.findById(postId);

        validUser(userId, findPost);

        findPost.update(request);

        return PostDtoUtil.postToPostResponse(findPost);
    }

    public void delete(Long userId, Long postId) {
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
