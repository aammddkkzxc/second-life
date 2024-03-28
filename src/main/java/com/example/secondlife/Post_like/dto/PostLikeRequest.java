package com.example.secondlife.Post_like.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeRequest {
    private Long member_id;
    private Long post_id;

    public PostLikeRequest(Long member_id, Long post_id) {
        this.member_id = member_id;
        this.post_id = post_id;
    }


}
