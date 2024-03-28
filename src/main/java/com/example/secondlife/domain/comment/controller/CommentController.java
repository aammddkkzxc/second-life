package com.example.secondlife.domain.comment.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.example.secondlife.domain.comment.dto.CommentRequest;
import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentResponse> addComment(@PathVariable Long postId,
                                                      @AuthenticationPrincipal(expression = "userId") Long userId,
                                                      CommentRequest request) {

        CommentResponse commentResponse = commentService.save(postId, userId, request);

        return ResponseEntity
                .status(CREATED)
                .body(commentResponse);
    }
}
