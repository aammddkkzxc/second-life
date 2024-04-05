package com.example.secondlife.domain.comment.controller;

import com.example.secondlife.domain.comment.dto.CommentRequest;
import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    @PreAuthorize("hasAnyRole('L1', 'L2', 'ADMIN')")
    @Operation(summary = "댓글 작성", description = "게시글에 댓글을 작성합니다.")
    public ResponseEntity<CommentResponse> addComment(@PathVariable Long postId,
                                                      @AuthenticationPrincipal(expression = "userId") Long userId,
                                                      @RequestBody CommentRequest request) {
        CommentResponse commentResponse = commentService.save(postId, userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentResponse);
    }

    @PatchMapping("/comments/{commentId}")
    @PreAuthorize("hasAnyRole('L1', 'L2', 'ADMIN')")
    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다. + 본인이 작성한 댓글만 수정할 수 있습니다.")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long commentId,
                                                         @AuthenticationPrincipal(expression = "userId") Long userId,
                                                         @RequestBody CommentRequest request) {
        CommentResponse response = commentService.update(commentId, userId, request);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/comments/{commentId}")
    @PreAuthorize("hasAnyRole('L1', 'L2', 'ADMIN')")
    @Operation(summary = "댓글 삭제", description = "댓글의 isDeleted 값을 true로 변경합니다. + 본인이 작성한 댓글만 삭제할 수 있습니다.")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId,
                                           @AuthenticationPrincipal(expression = "userId") Long userId) {
        commentService.delete(commentId, userId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
