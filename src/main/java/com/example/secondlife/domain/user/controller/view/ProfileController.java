package com.example.secondlife.domain.user.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    @GetMapping("/profile")
    public String profile() {
        log.info("profile()");

        return "html/profile";
    }

    @GetMapping("/profile/update")
    public String updateProfile() {
        log.info("updateProfile()");

        return "html/user-update";
    }
}
