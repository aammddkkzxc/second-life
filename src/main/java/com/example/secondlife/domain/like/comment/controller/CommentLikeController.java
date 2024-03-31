package com.example.secondlife.domain.like.comment.controller;

import com.example.secondlife.domain.like.comment.dto.CommentLikeResponse;
import com.example.secondlife.domain.like.comment.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<CommentLikeResponse> clickLike(@PathVariable Long commentId,
                                                         @AuthenticationPrincipal(expression = "userId") Long userId) {
        CommentLikeResponse response = commentLikeService.save(commentId, userId);

        return ResponseEntity
                .ok(response);
    }

//    @GetMapping("/comments/{commentId}/like")
//    public ResponseEntity<CommentLikeCountResponse> getLikesCount(@PathVariable Long commentId) {
//        CommentLikeCountResponse response = commentLikeService.getLikeCount(commentId);
//
//        return ResponseEntity
//                .ok(response);
//    }

    @DeleteMapping("/comments/{commentId}/like")
    public ResponseEntity<?> cancelLike(@PathVariable Long commentId,
                                        @AuthenticationPrincipal(expression = "userId") Long userId) {
        commentLikeService.delete(commentId, userId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
