package com.example.secondlife.domain.like.post.controller;

import com.example.secondlife.domain.like.post.dto.PostLikeCountDto;
import com.example.secondlife.domain.like.post.dto.PostLikeResponse;
import com.example.secondlife.domain.like.post.service.PostLikeService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/posts/{postId}/likeToggle")
    public ResponseEntity<?> clickLike(@PathVariable Long postId,
                                                      @AuthenticationPrincipal(expression = "userId") Long userId) {
        log.info("clickLike()");

        Optional<PostLikeResponse> postLikeResponse  = postLikeService.saveOrDelete(postId, userId);

        if (postLikeResponse.isPresent()) {
            return ResponseEntity.ok(postLikeResponse.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

//    @GetMapping("/posts/{postId}/like")
//    public ResponseEntity<PostLikeCountDto> getLikesCount(@PathVariable Long postId) {
//        PostLikeCountDto response = postLikeService.getLikeCount(postId);
//
//        return ResponseEntity
//                .ok(response);
//    }

    @DeleteMapping("/posts/{postId}/like")
    public ResponseEntity<?> cancelLike(@PathVariable Long postId,
                                        @AuthenticationPrincipal(expression = "userId") Long userId) {
        postLikeService.delete(postId, userId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
