package com.example.secondlife.domain.post.controller.api;

import static org.springframework.http.HttpStatus.CREATED;

import com.example.secondlife.common.exception.AuthenticationException;
import com.example.secondlife.common.security.CustomUserDetails;
import com.example.secondlife.domain.post.dto.PostDto;
import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.enumType.Forum;
import com.example.secondlife.domain.post.service.PostSearchService;
import com.example.secondlife.domain.post.service.PostService;
import com.example.secondlife.domain.user.enumType.Role;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class PostController {

    private final PostService postService;
    private final PostSearchService postSearchService;

    @PostMapping("/posts")
    @PreAuthorize("hasAnyRole('L1', 'L2', 'ADMIN')")
    @Operation(summary = "게시글 작성", description = "L1 이상 권한을 가진 유저가 게시글을 작성합니다. "
            + "L1 은 자유 게시판에만 작성할 수 있습니다.")
    public ResponseEntity<PostResponse> writePost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @RequestBody PostDto request) {
        final Long userId = userDetails.getUserId();
        final Role userRole = userDetails.getUserRole();
        final Forum forum = request.getForum();

        validRoleWithForum(userRole, forum);

        PostResponse postResponse = postService.save(userId, request);

        return ResponseEntity
                .status(CREATED)
                .body(postResponse);
    }

    @GetMapping("/posts/{postId}")
    @PreAuthorize("hasAnyRole('L2', 'ADMIN')")
    @Operation(summary = "게시글 상세 조회", description = "게시글의 상세 정보를 조회합니다.")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        final PostResponse postResponse = postSearchService.readWithCommentsAndCommentLikes(postId);

        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/posts")
    @PreAuthorize("hasAnyRole('L2', 'ADMIN')")
    @Operation(summary = "모든 게시글 조회", description = "모든 게시글을 조회합니다.")
    public ResponseEntity<Page<PostResponse>> getPosts(@PageableDefault Pageable pageable) {
        final Page<PostResponse> postResponses = postSearchService.getPosts(pageable);

        return ResponseEntity.ok(postResponses);
    }

    @PatchMapping("/posts/{postId}")
    @PreAuthorize("hasAnyRole('L1', 'L2', 'ADMIN')")
    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    public ResponseEntity<PostResponse> updatePost(@AuthenticationPrincipal(expression = "userId") Long userId,
                                                   @PathVariable Long postId,
                                                   @RequestBody PostDto request) {
        final PostResponse postResponse = postService.update(userId, postId, request);

        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/posts/{postId}")
    @PreAuthorize("hasAnyRole('L1', 'L2', 'ADMIN')")
    @Operation(summary = "게시글 삭제", description = "게시글의 isDeleted 값을 true로 변경합니다. + "
            + "자신이 작성한 게시글만 삭제할 수 있습니다.")
    public ResponseEntity<?> deletePost(@AuthenticationPrincipal(expression = "userId") Long userId,
                                        @PathVariable Long postId) {
        postService.delete(userId, postId);

        return ResponseEntity
                .noContent()
                .build();
    }

    private void validRoleWithForum(Role userRole, Forum forum) {
        if (forum.equals(Forum.REGION) && userRole.equals(Role.L1)) {
            throw new AuthenticationException("지역 게시판은 인증 유저만 접근할 수 있습니다");
        }
    }

}
