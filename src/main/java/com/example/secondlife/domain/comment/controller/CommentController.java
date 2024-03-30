package com.example.secondlife.domain.comment.controller;

import com.example.secondlife.domain.comment.dto.CommentRequest;
import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> addComment(@PathVariable Long postId,
                                                      @AuthenticationPrincipal(expression = "userId") Long userId,
                                                      CommentRequest request) {
        log.info("addComment()");

        CommentResponse commentResponse = commentService.save(postId, userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentResponse);
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long commentId,
                                                         @AuthenticationPrincipal(expression = "userId") Long userId,
                                                         @RequestBody CommentRequest request) {
        log.info("updateComment()");

        CommentResponse response = commentService.updateComment(commentId, userId, request);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId,
                                           @AuthenticationPrincipal(expression = "userId") Long userId) {

        commentService.deleteComment(commentId, userId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
