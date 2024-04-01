package com.example.secondlife.domain.like.post.service;

import com.example.secondlife.domain.like.post.dto.PostLikeCountDto;
import com.example.secondlife.domain.like.post.dto.PostLikeResponse;
import com.example.secondlife.domain.like.post.entity.PostLike;
import com.example.secondlife.domain.like.post.repository.PostLikeRepository;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.service.PostSearchService;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.service.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostSearchService postSearchService;
    private final UserSearchService userSearchService;

    public Optional<PostLikeResponse> saveOrDelete(Long postId, Long userId) {
        Optional<PostLike> findPostLike = postLikeRepository.findByPostIdAndUserId(postId, userId);

        if (findPostLike.isEmpty()) {
            Post findPost = postSearchService.findById(postId);
            User findUser = userService.findById(userId);

            PostLike postLike = PostLike.builder()
                    .user(findUser)
                    .post(findPost)
                    .build();

            PostLike savedPostLike = postLikeRepository.save(postLike);

            PostLikeResponse postLikeResponse = savedPostLike.toPostLikeResponse();

            return Optional.of(postLikeResponse);
        } else {
            postLikeRepository.delete(findPostLike.get());

            return Optional.empty();
        }
    }

    public PostLikeResponse save(Long postId, Long userId) {
        Post findPost = postSearchService.findById(postId);
        User findUser = userSearchService.findById(userId);

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

//    @Transactional(readOnly = true)
//    public PostLikeCountDto getLikeCount(Long postId) {
//
//        Long likeCount = postLikeRepository.countByPostId(postId);
//
//        return PostLikeCountDto.builder()
//                .count(likeCount)
//                .postId(postId)
//                .build();
//    }
}
