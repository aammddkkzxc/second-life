package com.example.secondlife.domain.comment.dto;

import com.example.secondlife.domain.like.comment.dto.CommentLikeResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CommentResponse {
    
    private Long commentId;
    private Long postId;
    private Long userId;
    private String nickName;
    private String contents;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;
    private Long likeCount;

}
