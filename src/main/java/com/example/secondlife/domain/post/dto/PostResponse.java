package com.example.secondlife.domain.post.dto;

import com.example.secondlife.domain.comment.dto.CommentResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {

    private Long userId;
    private Long postId;
    private String title;
    private String contents;
    private int hits;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private LocalDateTime lastContentUpdate;
    private String postUserNickname;
    private List<CommentResponse> commentResponses;
    private Long likeCount;

}
