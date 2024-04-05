package com.example.secondlife.domain.user.controller.view;


import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.service.PostSearchService;
import com.example.secondlife.domain.user.dto.ProfileResponse;
import com.example.secondlife.domain.user.service.UserSearchService;
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

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final UserSearchService userSearchService;
    private final PostSearchService postSearchService;

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('L1', 'L2', 'ADMIN')")
    public String profile(@AuthenticationPrincipal(expression = "userId") Long userId, Model model,
                          @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        final ProfileResponse profile = userSearchService.getProfile(userId);
        final Page<PostResponse> page = postSearchService.getPostsByUserId(pageable, userId);
        final List<PostResponse> posts = page.getContent();

        model.addAttribute("user", profile);
        model.addAttribute("posts", posts);

        return "html/profile";
    }

    @GetMapping("/profile/update")
    public String updateProfile() {
        return "html/user-update";
    }
}
