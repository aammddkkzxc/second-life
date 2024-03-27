package com.example.secondlife.domain.user.controller.api;

import static org.springframework.http.HttpStatus.CREATED;

import com.example.secondlife.domain.user.dto.JoinRequest;
import com.example.secondlife.domain.user.dto.JoinResponse;
import com.example.secondlife.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<JoinResponse> join(JoinRequest request) {
        JoinResponse joinResponse = userService.save(request);

        return ResponseEntity
                .status(CREATED)
                .body(joinResponse);
    }
    
}
