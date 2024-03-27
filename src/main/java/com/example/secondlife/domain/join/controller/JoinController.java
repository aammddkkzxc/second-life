package com.example.secondlife.domain.join.controller;

import com.example.secondlife.domain.join.dto.JoinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {

    private final JoinService joinService;

    @Autowired
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }


    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/joinProcess")
    public String joinProcess(JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO);

        return "redirect:/login";
    }
}
