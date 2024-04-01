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
    public String admin(Model model) {
        log.info("admin()");

        model.addAttribute("user", new UserResponse());

        return "html/admin";
    }

    @GetMapping("/admin/search")
    public String search(@RequestParam("category") String category,
                         @RequestParam("keyword") String keyword,
                         Model model) {

        if (category.equals(NICKNAME)) {
            User user = userSearchService.findByNickname(keyword);

            final UserResponse userResponse = user.userResponse();

            model.addAttribute("user", userResponse);
        }

        return "html/admin";
    }
}
