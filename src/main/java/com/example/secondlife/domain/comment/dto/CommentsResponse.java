package com.example.secondlife.domain.comment.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CommentsResponse {
    private List<CommentResponse> comments;
}
