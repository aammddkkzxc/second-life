package com.example.secondlife.domain.user.controller.view;

import com.example.secondlife.domain.user.dto.UserResponse;
import com.example.secondlife.domain.user.service.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserSearchService userSearchService;

    @GetMapping("/admin")
    public String admin(
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {
        final UserResponse userResponse = userSearchService.searchByNickName(keyword);

        model.addAttribute("user", userResponse);

        return "html/admin";
    }
}
