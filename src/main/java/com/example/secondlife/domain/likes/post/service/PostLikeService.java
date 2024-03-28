package com.example.secondlife.domain.likes.post.service;

import com.example.secondlife.domain.likes.post.dto.PostLikeCountResponse;
import com.example.secondlife.domain.likes.post.dto.PostLikeResponse;
import com.example.secondlife.domain.likes.post.entity.PostLike;
import com.example.secondlife.domain.likes.post.repository.PostLikeRepository;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.service.PostService;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostService postService;
    private final UserService userService;

    public PostLikeResponse save(Long postId, Long userId) {
        Post findPost = postService.findById(postId);
        User findUser = userService.findById(userId);

        PostLike postLike = PostLike.builder()
                .post(findPost)
                .user(findUser)
                .build();

        PostLike savedPostLike = postLikeRepository.save(postLike);

        return PostLikeResponse.builder()
                .postId(savedPostLike.getPost().getId())
                .userId(savedPostLike.getUser().getId())
                .postLikeId(savedPostLike.getId())
                .build();
    }

    public void delete(Long postId, Long userId) {
        postLikeRepository.findByPostIdAndUserId(postId, userId)
                .ifPresent(postLikeRepository::delete);
    }

    @Transactional(readOnly = true)
    public PostLikeCountResponse getLikeCount(Long postId) {

        Long likeCount = postLikeRepository.countByPostId(postId);

        return PostLikeCountResponse.builder()
                .count(likeCount)
                .postId(postId)
                .build();
    }
}