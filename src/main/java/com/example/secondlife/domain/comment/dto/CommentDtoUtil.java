package com.example.secondlife.domain.comment.dto;

import com.example.secondlife.domain.comment.entity.Comment;
import java.util.List;

public class CommentDtoUtil {

    public static CommentResponse commentToCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .postId(comment.getPost().getId())
                .userId(comment.getUser().getId())
                .nickName(comment.getUser().getNickname())
                .contents(comment.getContents())
                .createdDate(comment.getCreatedDate())
                .lastModifiedDate(comment.getLastModifiedDate())
                .build();
    }

    public static List<CommentResponse> commentsToCommentResponses(List<Comment> comments) {
        return comments.stream()
                .map(CommentDtoUtil::commentToCommentResponse)
                .toList();
    }
}
