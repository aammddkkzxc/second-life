package com.example.secondlife.domain.user.controller.view;

import com.example.secondlife.domain.user.dto.UserResponse;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.service.UserSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    public static final String NICKNAME = "nickname";

    private final UserSearchService userSearchService;

    @GetMapping("/admin")
    public String admin(@RequestParam(value = "category", required = false) String category,
                        @RequestParam(value = "keyword", required = false) String keyword,
                        Model model) {
        log.info("admin()");

        if (category == null || category.isEmpty() || keyword == null || keyword.isEmpty()) {
            model.addAttribute("user", new UserResponse());
        } else {
            if (category.equals(NICKNAME)) {
                User user = userSearchService.findByNickname(keyword);
                final UserResponse userResponse = user.userResponse();
                model.addAttribute("user", userResponse);
            }
        }

        return "html/admin";
    }
}
