package com.example.secondlife.domain.likes.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PostLikeResponse {

    private Long postId;
    private Long userId;
    private Long postLikeId;

}
