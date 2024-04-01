package com.example.secondlife.domain.like.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PostLikeCountDto {

    private Long postId;
    private Long likeCount;

}
