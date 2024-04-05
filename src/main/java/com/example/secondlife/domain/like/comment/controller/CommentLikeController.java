package com.example.secondlife.domain.like.comment.controller;

import com.example.secondlife.domain.like.comment.dto.CommentLikeResponse;
import com.example.secondlife.domain.like.comment.service.CommentLikeService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/comments/{commentId}/likeToggle")
    @Operation(summary = "댓글 추천 토글", description = "댓글 추천을 토글합니다.")
    public ResponseEntity<?> clickLike(@PathVariable Long commentId,
                                       @AuthenticationPrincipal(expression = "userId") Long userId) {
        Optional<CommentLikeResponse> commentLikeResponse = commentLikeService.saveOrDelete(commentId, userId);

        if (commentLikeResponse.isPresent()) {
            return ResponseEntity.ok(commentLikeResponse.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/comments/{commentId}/like")
    @Operation(summary = "댓글 추천", description = "댓글에 추천을 추가합니다.")
    public ResponseEntity<CommentLikeResponse> addLike(@PathVariable Long commentId,
                                                       @AuthenticationPrincipal(expression = "userId") Long userId) {
        CommentLikeResponse commentLikeResponse = commentLikeService.save(commentId, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentLikeResponse);
    }

    @DeleteMapping("/comments/{commentId}/like")
    @Operation(summary = "댓글 추천 취소", description = "댓글에 추가한 추천을 취소합니다.")
    public ResponseEntity<?> cancelLike(@PathVariable Long commentId,
                                        @AuthenticationPrincipal(expression = "userId") Long userId) {
        commentLikeService.delete(commentId, userId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
