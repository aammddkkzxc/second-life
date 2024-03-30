package com.example.secondlife.domain.post.controller.page;

import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.service.CommentSearchService;
import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.service.PostSearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private final CommentSearchService commentSearchService;

    @GetMapping("/board")
    public String board(Model model, @PageableDefault Pageable pageable) {
        log.info("board()");

        Page<PostResponse> postResponses = postSearchService.getPosts(pageable);
        model.addAttribute("posts", postResponses.getContent());
        model.addAttribute("page", postResponses);

        return "html/board";
    }

    @GetMapping("/my/board")
    public String myBoard(Model model, @PageableDefault Pageable pageable,
                          @AuthenticationPrincipal(expression = "userId") Long userId) {
        log.info("board()");

        Page<PostResponse> postsByUserId = postSearchService.getPostsByUserId(pageable, userId);

        model.addAttribute("posts", postsByUserId.getContent());
        model.addAttribute("page", postsByUserId);

        return "html/board";
    }

    @GetMapping("/board/{postId}")
    public String post(@PathVariable("postId") Long postId, Model model,
                       @AuthenticationPrincipal(expression = "userId") Long userId) {
        log.info("post()");

        PostResponse postResponse = postSearchService.readWithComments(postId);
        List<CommentResponse> comments = commentSearchService.getComments(postId);

        model.addAttribute("post", postResponse);
        model.addAttribute("comments", comments);

        return "html/detail2";
    }

}
