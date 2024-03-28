package com.example.secondlife.domain.user.controller.api;

import static org.springframework.http.HttpStatus.CREATED;

import com.example.secondlife.domain.user.dto.JoinRequest;
import com.example.secondlife.domain.user.dto.JoinResponse;
import com.example.secondlife.domain.user.dto.UserInfo;
import com.example.secondlife.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<JoinResponse> join(@RequestBody JoinRequest request) {
        log.info("join()");

        log.info("joinRequest: {}", request);

        JoinResponse joinResponse = userService.save(request);

        return ResponseEntity
                .status(CREATED)
                .body(joinResponse);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable Long userId) {
        log.info("getUserInfo()");

        UserInfo userInfo = userService.getUserInfo(userId);

        return ResponseEntity.ok(userInfo);
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<UserInfo> updateUserInfo(@PathVariable Long userId, UserInfo request) {
        log.info("updateUserInfo()");

        UserInfo userInfo = userService.updateUserInfo(userId, request);

        return ResponseEntity.ok(userInfo);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        log.info("deleteUser()");

        userService.deleteUser(userId);

        return ResponseEntity
                .noContent()
                .build();
    }

}