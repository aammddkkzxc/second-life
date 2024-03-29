package system.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SystemController {

    @GetMapping("/board1")
    public String board1P(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("user", user.getUsername());
        return "html/board1";
    }
}


