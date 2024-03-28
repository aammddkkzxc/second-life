package com.example.secondlife.domain.likes.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PostLikeCountResponse {

    private Long postId;
    private Long count;

}
