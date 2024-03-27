package com.example.secondlife.domain.user.controller.view;

import com.example.secondlife.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserViewController {

    private final UserService userService;

}
