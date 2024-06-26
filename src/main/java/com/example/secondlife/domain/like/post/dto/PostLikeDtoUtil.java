package com.example.secondlife.domain.like.post.dto;

import com.example.secondlife.domain.like.post.entity.PostLike;

public class PostLikeDtoUtil {

    public static PostLikeResponse postLikeToPostLikeResponse(PostLike postLike) {
        return PostLikeResponse.builder()
                .postLikeId(postLike.getId())
                .userId(postLike.getUserId())
                .postId(postLike.getPostId())
                .build();
    }
}
