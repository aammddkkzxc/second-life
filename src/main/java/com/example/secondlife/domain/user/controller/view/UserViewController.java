package com.example.secondlife.domain.user.controller.view;

import com.example.secondlife.domain.user.dto.JoinRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class UserViewController {

    @GetMapping("/join")
    public String join(@ModelAttribute("joinRequest") JoinRequest request) {
        return "html/join";
    }

}
