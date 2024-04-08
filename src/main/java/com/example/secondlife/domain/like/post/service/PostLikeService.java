package com.example.secondlife.domain.like.post.service;

import com.example.secondlife.domain.like.post.dto.PostLikeDtoUtil;
import com.example.secondlife.domain.like.post.dto.PostLikeResponse;
import com.example.secondlife.domain.like.post.entity.PostLike;
import com.example.secondlife.domain.like.post.repository.PostLikeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    public Optional<PostLikeResponse> saveOrDelete(Long postId, Long userId) {
        Optional<PostLike> findPostLike = postLikeRepository.findByPostIdAndUserId(postId, userId);

        if (findPostLike.isEmpty()) {
            final PostLikeResponse postLikeResponse = save(postId, userId);

            return Optional.of(postLikeResponse);
        } else {
            postLikeRepository.delete(findPostLike.get());

            return Optional.empty();
        }
    }

    public PostLikeResponse save(Long postId, Long userId) {
        PostLike postLike = PostLike.builder()
                .postId(postId)
                .userId(userId)
                .build();

        PostLike savedPostLike = postLikeRepository.save(postLike);

        return PostLikeDtoUtil.postLikeToPostLikeResponse(savedPostLike);
    }

    public void delete(Long postId, Long userId) {
        postLikeRepository.findByPostIdAndUserId(postId, userId)
                .ifPresent(postLikeRepository::delete);
    }

}
