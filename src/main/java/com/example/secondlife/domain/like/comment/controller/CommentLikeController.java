package com.example.secondlife.domain.like.comment.controller;

import com.example.secondlife.domain.like.comment.dto.CommentLikeResponse;
import com.example.secondlife.domain.like.comment.service.CommentLikeService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<?> clickLike(@PathVariable Long commentId,
                                       @AuthenticationPrincipal(expression = "userId") Long userId) {
        Optional<CommentLikeResponse> commentLikeResponse = commentLikeService.saveOrDelete(commentId, userId);

        if (commentLikeResponse.isPresent()) {
            return ResponseEntity.ok(commentLikeResponse.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/comments/{commentId}/addLike")
    public ResponseEntity<CommentLikeResponse> addLike(@PathVariable Long commentId,
                                                       @AuthenticationPrincipal(expression = "userId") Long userId) {
        CommentLikeResponse commentLikeResponse = commentLikeService.save(commentId, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentLikeResponse);
    }

    @DeleteMapping("/comments/{commentId}/cancelLike")
    public ResponseEntity<?> cancelLike(@PathVariable Long commentId,
                                        @AuthenticationPrincipal(expression = "userId") Long userId) {
        commentLikeService.delete(commentId, userId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
