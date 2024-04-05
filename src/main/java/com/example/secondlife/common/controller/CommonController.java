package com.example.secondlife.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class CommonController {

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
