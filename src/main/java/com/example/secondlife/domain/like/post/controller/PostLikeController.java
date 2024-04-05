package com.example.secondlife.domain.like.post.controller;

import com.example.secondlife.domain.like.post.dto.PostLikeResponse;
import com.example.secondlife.domain.like.post.service.PostLikeService;
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
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/posts/{postId}/likeToggle")
    @Operation(summary = "게시글 추천 토글", description = "게시글 추천을 토글합니다.")
    public ResponseEntity<?> clickLike(@PathVariable Long postId,
                                       @AuthenticationPrincipal(expression = "userId") Long userId) {
        Optional<PostLikeResponse> postLikeResponse = postLikeService.saveOrDelete(postId, userId);

        if (postLikeResponse.isPresent()) {
            return ResponseEntity.ok(postLikeResponse.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/posts/{postId}/like")
    @Operation(summary = "게시글 추천", description = "게시글에 추천을 추가합니다.")
    public ResponseEntity<PostLikeResponse> addLike(@PathVariable Long postId,
                                                    @AuthenticationPrincipal(expression = "userId") Long userId) {
        PostLikeResponse postLikeResponse = postLikeService.save(postId, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postLikeResponse);
    }

    @DeleteMapping("/posts/{postId}/like")
    @Operation(summary = "게시글 추천 취소", description = "게시글에 추가한 추천을 취소합니다.")
    public ResponseEntity<?> cancelLike(@PathVariable Long postId,
                                        @AuthenticationPrincipal(expression = "userId") Long userId) {
        postLikeService.delete(postId, userId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
