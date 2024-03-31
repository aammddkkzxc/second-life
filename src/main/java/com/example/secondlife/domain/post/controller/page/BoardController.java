package com.example.secondlife.domain.post.controller.page;

import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.post.dto.PostRequest;
import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.enumType.Forum;
import com.example.secondlife.domain.post.service.PostSearchService;
import com.example.secondlife.domain.user.enumType.Region;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping(value = {"/board", "/board2"})
    public String board(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                        HttpServletRequest request) {
        log.info("board()");

        Forum forumType = "/board".equals(request.getRequestURI()) ? Forum.FREE : Forum.REGION;
        Page<PostResponse> postResponses = postSearchService.getPostsByForum(forumType, pageable);

        model.addAttribute("posts", postResponses.getContent());
        model.addAttribute("page", postResponses);

        return "html/board";
    }

    @GetMapping("/board2/{region}")
    public String board2(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                         @PathVariable("region") Region region) {
        log.info("board2()");

        Page<PostResponse> postResponses = postSearchService.getPostsByForumAndRegion(Forum.REGION, region, pageable);

        model.addAttribute("posts", postResponses.getContent());
        model.addAttribute("page", postResponses);

        return "html/board";
    }

    @GetMapping("/my/board")
    public String myBoard(Model model,
                          @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                          @AuthenticationPrincipal(expression = "userId") Long userId) {
        log.info("myBoard()");

        Page<PostResponse> postsByUserId = postSearchService.getPostsByUserId(pageable, userId);

        model.addAttribute("posts", postsByUserId.getContent());
        model.addAttribute("page", postsByUserId);

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

        PostRequest postRequest = postSearchService.getPostsByPostId(postId);
        final String forum = postRequest.getForum().name();

        model.addAttribute("post", postRequest);
        model.addAttribute("postId", postId);
        model.addAttribute("forum", forum);

        return "html/edit";
    }
}
