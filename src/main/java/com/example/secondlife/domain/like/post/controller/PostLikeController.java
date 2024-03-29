package com.example.secondlife.domain.like.post.controller;

import com.example.secondlife.domain.like.post.dto.PostLikeCountResponse;
import com.example.secondlife.domain.like.post.dto.PostLikeResponse;
import com.example.secondlife.domain.like.post.service.PostLikeService;
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
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<PostLikeResponse> clickLike(@PathVariable Long postId,
                                                      @AuthenticationPrincipal(expression = "userId") Long userId) {
        PostLikeResponse response = postLikeService.save(postId, userId);

        return ResponseEntity
                .ok(response);
    }

    @GetMapping("/posts/{postId}/like")
    public ResponseEntity<PostLikeCountResponse> getLikesCount(@PathVariable Long postId) {
        PostLikeCountResponse response = postLikeService.getLikeCount(postId);

        return ResponseEntity
                .ok(response);
    }

    @DeleteMapping("/posts/{postId}/like")
    public ResponseEntity<?> cancelLike(@PathVariable Long postId,
                                        @AuthenticationPrincipal(expression = "userId") Long userId) {
        postLikeService.delete(postId, userId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
