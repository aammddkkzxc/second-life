package com.example.secondlife.domain.user.controller.view;

import com.example.secondlife.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final UserService userService;

    @GetMapping("/profile")
    public String info() {
        log.info("info()");

        return "html/profile";
    }
}
