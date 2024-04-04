package com.example.secondlife.domain.post.dto;

import com.example.secondlife.domain.user.enumType.Region;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class HotPostDto {

    private Long postId;
    private String title;
    private int hits;
    private Long likeCount;
    private Region region;
    private LocalDateTime createdDate;
    private Long commentCount;

    @QueryProjection
    public HotPostDto(Long postId, String title, Integer hits, LocalDateTime createdDate, Long likeCount,
                      Long commentCount, Region region) {
        this.postId = postId;
        this.title = title;
        this.hits = hits;
        this.createdDate = createdDate;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.region = region;
    }

}
