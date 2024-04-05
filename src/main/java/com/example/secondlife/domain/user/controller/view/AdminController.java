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
    public String admin(@RequestParam(value = "category", required = false) String category,
                        @RequestParam(value = "keyword", required = false) String keyword,
                        Model model) {
        UserResponse userResponse = new UserResponse();

        if (isValidSearchCriteria(category, keyword)) {
            UserResponse searchedUser = userSearchService.searchByNickName(keyword);

            if (searchedUser != null) {
                userResponse = searchedUser;
            }
        }

        model.addAttribute("user", userResponse);

        return "html/admin";
    }

    private boolean isValidSearchCriteria(String category, String keyword) {
        return category != null && !category.isEmpty() && keyword != null && !keyword.isEmpty() && category.equals(
                "nickname");
    }
}
