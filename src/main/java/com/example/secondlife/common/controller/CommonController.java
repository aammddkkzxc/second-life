package com.example.secondlife.common.controller;

import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.service.PostSearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CommonController {

    PostSearchService postSearchService;

    @Autowired
    public CommonController(PostSearchService postSearchService) {
        this.postSearchService = postSearchService;
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        log.info("mainPage()");

        final List<PostResponse> hotPostResponses = postSearchService.getHotPosts();

        model.addAttribute("posts", hotPostResponses);

        return "html/main";
    }

    @GetMapping("/join")
    public String join() {
        log.info("join()");

        return "html/join";
    }

    @GetMapping("/login")
    public String login() {
        log.info("login()");

        return "html/login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        log.info("accessDenied()");

        return "html/access-denied";
    }

    @GetMapping("/mail")
    public String mail() {
        log.info("mail()");

        return "html/mail";
    }
}
