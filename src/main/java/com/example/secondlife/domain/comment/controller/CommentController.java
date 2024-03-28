package com.example.secondlife.domain.comment.controller;

import com.example.secondlife.domain.comment.dto.CommentRequest;
import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.dto.CommentUpdateRequest;
import com.example.secondlife.domain.comment.dto.CommentsResponse;
import com.example.secondlife.domain.comment.entity.Comment;
import com.example.secondlife.domain.comment.service.CommentService;
import com.example.secondlife.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentResponse> addComment(@PathVariable Long postId,
                                                      @AuthenticationPrincipal(expression = "userId") Long userId,
                                                      CommentRequest request) {

        CommentResponse commentResponse = commentService.save(postId, userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentResponse);
    }

    @Transactional(readOnly = true)
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentsResponse> getComments(@PathVariable Long postId) {
        CommentsResponse commentsResponse = commentService.getComments(postId);
        return ResponseEntity.status(HttpStatus.FOUND).body(commentsResponse);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long commentId,
                                                         @AuthenticationPrincipal(expression = "userId") Long userId,
                                                         CommentUpdateRequest request) {

        CommentResponse response = commentService.updateComment(commentId, userId, request);

        return ResponseEntity.ok().body(response);
    }
}
