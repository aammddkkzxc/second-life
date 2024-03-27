package com.example.secondlife.common.controller;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.bind.annotation.GetMapping;

@Configurable
public class CommonController {
    @GetMapping("/join")
    public String join() {
        return "html/join";
    }

    @GetMapping("/login")
    public String login() {
        return "html/login";
    }
}
