package com.example.secondlife.domain.user.controller.api;

import static org.springframework.http.HttpStatus.CREATED;

import com.example.secondlife.domain.user.dto.JoinRequest;
import com.example.secondlife.domain.user.dto.JoinResponse;
import com.example.secondlife.domain.user.dto.ProfileResponse;
import com.example.secondlife.domain.user.dto.UpdateUserRequest;
import com.example.secondlife.domain.user.dto.UpdateUserRole;
import com.example.secondlife.domain.user.dto.UserResponse;
import com.example.secondlife.domain.user.service.UserSearchService;
import com.example.secondlife.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final UserSearchService userSearchService;

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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfileResponse> getUserProfile(@PathVariable Long userId) {
        log.info("getUserInfo()");

        final ProfileResponse profile = userSearchService.getProfile(userId);

        return ResponseEntity.ok(profile);
    }

    @PatchMapping("/users/{userId}")
    @PreAuthorize("hasAnyRole('L1', 'L2')")
    public ResponseEntity<UserResponse> updateUserProfile(@PathVariable Long userId, UpdateUserRequest request) {
        log.info("updateUserProfile()");

        UserResponse userResponse = userService.updateProfile(userId, request);

        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/users/role/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUserRole(@PathVariable Long userId, @RequestBody UpdateUserRole request) {
        log.info("updateUserRole()");

        log.info("updateUserRoleRequest: {}", request.getRole());

        UserResponse userInfo = userService.updateRole(userId, request);

        return ResponseEntity.ok(userInfo);
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasAnyRole('L1', 'L2', 'ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        log.info("deleteUser()");

        userService.delete(userId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
