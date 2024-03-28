package com.example.secondlife.domain.user.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    @GetMapping("/html/login")
    public String login() {
        return "/html/login";
    }
}
