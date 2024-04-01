package com.example.secondlife.domain.post.controller.page;

import com.example.secondlife.common.security.CustomUserDetails;
import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.post.dto.PostRequest;
import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.enumType.Forum;
import com.example.secondlife.domain.post.service.PostSearchService;
import com.example.secondlife.domain.user.enumType.Region;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final PostSearchService postSearchService;

    @GetMapping("/board")
    public String freeBoard(Model model,
                            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("freeBoard()");

        final Page<PostResponse> postResponses = postSearchService.getPostsByForum(Forum.FREE, pageable);

        model.addAttribute("posts", postResponses.getContent());
        model.addAttribute("page", postResponses);

        return "html/board";
    }

    @GetMapping("/board2")
    @PreAuthorize("hasAnyRole('L2', 'ADMIN')")
    public String regionBoard(Model model,
                              @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("regionBoard()");

        final Page<PostResponse> postResponses = postSearchService.getPostsByForum(Forum.REGION, pageable);

        model.addAttribute("posts", postResponses.getContent());
        model.addAttribute("page", postResponses);

        return "html/board";
    }

    @GetMapping("/board2/{region}")
    @PreAuthorize("hasAnyRole('L2', 'ADMIN')")
    public String specificRegionBoard(Model model, @PathVariable Region region,
                                      @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("specificRegionBoard(), region: {}", region);

        final Page<PostResponse> postResponses = postSearchService.getPostsByForumAndRegion(Forum.REGION, region,
                pageable);

        model.addAttribute("posts", postResponses.getContent());
        model.addAttribute("page", postResponses);

        return "html/board";
    }


    @GetMapping("/my/board")
    public String myBoard(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("myBoard()");

        final Page<PostResponse> postResponses = postSearchService.getPostsByUserId(pageable, userDetails.getUserId());

        model.addAttribute("posts", postResponses.getContent());
        model.addAttribute("page", postResponses);

        return "html/board";
    }


    @GetMapping("/board/{postId}")
    public String post(@PathVariable("postId") Long postId, Model model) {
        log.info("post()");

        final PostResponse postResponse = postSearchService.readWithCommentsAndCommentLikes(postId);
        final List<CommentResponse> commentResponses = postResponse.getCommentResponses();
        final int size = commentResponses.size();

        model.addAttribute("post", postResponse);
        model.addAttribute("comments", commentResponses);
        model.addAttribute("commentsCount", size);

        return "html/detail";
    }

    @GetMapping("/write")
    public String write() {
        log.info("write()");

        return "html/write";
    }

    @GetMapping("/board/{postId}/edit")
    public String edit(@PathVariable("postId") Long postId, Model model) {
        log.info("edit()");

        final PostRequest postRequest = postSearchService.getPostsByPostId(postId);
        final String forum = postRequest.getForum().name();

        model.addAttribute("post", postRequest);
        model.addAttribute("postId", postId);
        model.addAttribute("forum", forum);

        return "html/edit";
    }
}
