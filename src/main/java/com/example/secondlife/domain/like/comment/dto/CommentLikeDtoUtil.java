package com.example.secondlife.domain.like.comment.dto;

import com.example.secondlife.domain.like.comment.entity.CommentLike;

public class CommentLikeDtoUtil {

    public static CommentLikeResponse commentLikeToCommentLikeResponse(CommentLike commentLike) {
        return CommentLikeResponse.builder()
                .commentLikeId(commentLike.getId())
                .userId(commentLike.getUserId())
                .commentId(commentLike.getCommentId())
                .build();
    }
}
