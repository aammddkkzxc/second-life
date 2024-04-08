package com.example.secondlife.domain.post.controller.page;

import com.example.secondlife.common.security.CustomUserDetails;
import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.post.dto.HotPostDto;
import com.example.secondlife.domain.post.dto.PostDto;
import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.enumType.Forum;
import com.example.secondlife.domain.post.service.PostSearchService;
import com.example.secondlife.domain.post.service.PostService;
import com.example.secondlife.domain.user.enumType.Region;
import com.example.secondlife.domain.user.enumType.Role;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final PostSearchService postSearchService;
    private final PostService postService;

    @GetMapping("/")
    public String mainPage(Model model) {
        final List<HotPostDto> hotPosts = postSearchService.getHotPosts();

        model.addAttribute("posts", hotPosts);

        return "html/main";
    }

    @GetMapping("/board")
    @PreAuthorize("hasAnyRole('L1', 'L2', 'ADMIN')")
    public String freeBoard(Model model,
                            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        final Page<PostResponse> postResponses = postSearchService.getPostsByForum(Forum.FREE, pageable);

        model.addAttribute("posts", postResponses.getContent());
        model.addAttribute("page", postResponses);

        return "html/board";
    }

    @GetMapping("/board2")
    @PreAuthorize("hasAnyRole('L2', 'ADMIN')")
    public String regionBoard(Model model,
                              @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        final Page<PostResponse> postResponses = postSearchService.getPostsByForum(Forum.REGION, pageable);

        model.addAttribute("posts", postResponses.getContent());
        model.addAttribute("page", postResponses);

        return "html/board";
    }

    @GetMapping("/board2/{region}")
    @PreAuthorize("hasAnyRole('L2', 'ADMIN')")
    public String specificRegionBoard(Model model, @PathVariable Region region,
                                      @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        final Page<PostResponse> postResponses = postSearchService.getPostsByForumAndRegion(Forum.REGION, region,
                pageable);

        model.addAttribute("posts", postResponses.getContent());
        model.addAttribute("page", postResponses);

        return "html/board";
    }

    @GetMapping("/my/board")
    @PreAuthorize("hasAnyRole('L1', 'L2', 'ADMIN')")
    public String myBoard(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        final Page<PostResponse> postResponses = postSearchService.getPostsByUserId(pageable, userDetails.getUserId());

        model.addAttribute("posts", postResponses.getContent());
        model.addAttribute("page", postResponses);

        return "html/board";
    }

    @GetMapping("/board/{postId}")
    @PreAuthorize("hasAnyRole('L1', 'L2', 'ADMIN')")
    public String viewPost(@PathVariable("postId") Long postId, Model model,
                           HttpServletRequest request, HttpServletResponse response) {
        updateViewCountIfNotViewed(postId, request, response);

        final PostResponse postResponse = postSearchService.readWithCommentsAndCommentLikes(postId);
        final Forum forum = postResponse.getForum();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Role userRole = userDetails.getUserRole();

        if (userRole == Role.L1 && forum == Forum.REGION) {
            return "redirect:/access-denied";
        }

        final List<CommentResponse> commentResponses = postResponse.getCommentResponses();
        final int size = commentResponses.size();

        model.addAttribute("post", postResponse);
        model.addAttribute("comments", commentResponses);
        model.addAttribute("commentsCount", size);

        return "html/detail";
    }

    @GetMapping("/write")
    @PreAuthorize("hasAnyRole('L1', 'L2', 'ADMIN')")
    public String write() {
        return "html/write";
    }

    @GetMapping("/board/{postId}/edit")
    @PreAuthorize("hasAnyRole('L1', 'L2', 'ADMIN')")
    public String edit(@PathVariable("postId") Long postId, Model model) {
        final PostDto postDto = postSearchService.getPostDtoByPostId(postId);
        final String forum = postDto.getForum().name();

        model.addAttribute("post", postDto);
        model.addAttribute("postId", postId);
        model.addAttribute("forum", forum);

        return "html/edit";
    }

    public void updateViewCountIfNotViewed(Long postId, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = Optional.ofNullable(
                request.getCookies()
        ).orElseGet(() -> new Cookie[0]);

        // 쿠키가 있다면 cookie에 넣고, 없다면 조회수 증가 및 쿠키 생성
        Cookie cookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("postView"))
                .findFirst()
                .orElseGet(() -> {
                    postService.incrementViewCount(postId);
                    return new Cookie("postView", "[" + postId + "]");
                });

        // 쿠키가 없다면 조회수 증가 및 쿠키 생성
        if (!cookie.getValue().contains("[" + postId + "]")) {
            postService.incrementViewCount(postId);
            cookie.setValue(cookie.getValue() + "[" + postId + "]");
        }

        long todayEndSecond = LocalDate.now().atTime(LocalTime.MAX).toEpochSecond(ZoneOffset.UTC);
        long currentSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

        cookie.setPath("/"); // 모든 경로에서 접근 가능
        cookie.setMaxAge((int) (todayEndSecond - currentSecond)); // 오늘 하루 자정까지 남은 시간초 설정

        response.addCookie(cookie);
    }

}
