package com.example.secondlife.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

    @GetMapping("/join")
    public String join() {
        return "html/join";
    }

    @GetMapping("/login")
    public String login() {
        return "html/login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "html/access-denied";
    }

    @GetMapping("/mail")
    public String mail() {
        return "html/mail";
    }
}
