package com.example.secondlife.domain.post.dto;

import com.example.secondlife.domain.user.enumType.Region;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class hotPostDto {

    private Long postId;
    private String title;
    private int hits;
    private Long likeCount;
    private Region region;
    private LocalDateTime createdDate;
    private Long commentCount;

}
