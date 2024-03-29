package com.example.secondlife.domain.like.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CommentLikeCountResponse {

    private Long commentId;
    private Long count;

}
