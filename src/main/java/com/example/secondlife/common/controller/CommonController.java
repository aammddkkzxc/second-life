package com.example.secondlife.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class CommonController {

    @GetMapping("/")
    public String mainPage(@AuthenticationPrincipal(expression = "userId") Long userId) {
        log.error("userId: {}", userId);
        log.info("mainPage()");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        log.info("mainPage - 현재 로그인된 사용자: {}", currentUserName);
        log.info("mainPage - 현재 사용자의 권한: {}", authentication.getAuthorities().iterator().next().getAuthority());
        log.info("mainPage - 현재 사용자의 인증정보: {}", authentication.getDetails());
        log.info("mainPage - 현재 사용자의 인증여부: {}", authentication.isAuthenticated());
        log.info("mainPage - 현재 사용자의 Principal: {}", authentication.getPrincipal());
        log.info("mainPage - 현재 사용자의 Credentials: {}", authentication.getCredentials());

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

    @GetMapping("/info")
    public String info() {
        log.info("info()");

        return "html/info";
    }
}
