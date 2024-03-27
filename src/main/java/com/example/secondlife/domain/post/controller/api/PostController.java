package com.example.secondlife.domain.post.controller.api;

import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.dto.PostUpdateRequest;
import com.example.secondlife.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //TODO : Spring Security 적용 후 수정
//    @PostMapping("/post")
//    public ResponseEntity<PostResponse> writePost(@AuthenticationPrincipal(expression = "id") Long userId,
//                                                  PostingRequest request) {
//        PostResponse postingResponse = postService.save(userId, request);
//
//        return ResponseEntity
//                .status(CREATED)
//                .body(postingResponse);
//    }

    //TODO : Comment class 생성 후 수정
//    @GetMapping("/posts/{postId}")
//    public ResponseEntity<PostResponse> getPost(Long postId) {
//        PostResponse postResponse = postService.readAllWithComments(postId);
//
//        return ResponseEntity.ok(postResponse);
//    }

    @GetMapping("/posts")
    public ResponseEntity<Page<PostResponse>> getPosts(@PageableDefault Pageable pageable) {
        Page<PostResponse> postResponses = postService.getPosts(pageable);

        return ResponseEntity.ok(postResponses);
    }

    @PatchMapping("/post/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long postId, PostUpdateRequest request) {

        PostResponse postResponse = postService.updatePost(postId, request);

        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
