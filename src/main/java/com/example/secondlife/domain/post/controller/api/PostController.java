package com.example.secondlife.domain.post.controller.api;

import static org.springframework.http.HttpStatus.CREATED;

import com.example.secondlife.domain.post.dto.PostRequest;
import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.service.PostSearchService;
import com.example.secondlife.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
public class PostController {

    private final PostService postService;
    private final PostSearchService postSearchService;

    @PostMapping("/posts")
    public ResponseEntity<PostResponse> writePost(@AuthenticationPrincipal(expression = "userId") Long userId,
                                                  @RequestBody PostRequest request) {
        log.info("writePost()");

        PostResponse postResponse = postService.save(userId, request);

        return ResponseEntity
                .status(CREATED)
                .body(postResponse);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        log.info("getPost()");

        PostResponse postResponse = postSearchService.readWithComments(postId);

        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<PostResponse>> getPosts(@PageableDefault Pageable pageable) {
        log.info("getPosts()");

        Page<PostResponse> postResponses = postSearchService.getPosts(pageable);

        return ResponseEntity.ok(postResponses);
    }

    @PatchMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> updatePost(@AuthenticationPrincipal(expression = "userId") Long userId,
                                                   @PathVariable Long postId,
                                                   @RequestBody PostRequest request) {
        log.info("updatePost()");

        PostResponse postResponse = postService.updatePost(userId, postId, request);

        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@AuthenticationPrincipal(expression = "userId") Long userId,
                                        @PathVariable Long postId) {
        log.info("deletePost()");

        postService.deletePost(userId, postId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
