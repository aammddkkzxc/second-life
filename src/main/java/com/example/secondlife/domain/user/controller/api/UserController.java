package com.example.secondlife.domain.user.controller.api;

import static org.springframework.http.HttpStatus.CREATED;

import com.example.secondlife.domain.user.dto.JoinRequest;
import com.example.secondlife.domain.user.dto.JoinResponse;
import com.example.secondlife.domain.user.dto.ProfileResponse;
import com.example.secondlife.domain.user.dto.UpdateUserRequest;
import com.example.secondlife.domain.user.dto.UpdateUserRoleRequest;
import com.example.secondlife.domain.user.dto.UserResponse;
import com.example.secondlife.domain.user.service.UserSearchService;
import com.example.secondlife.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UserSearchService userSearchService;

    @PostMapping("/users")
    @Operation(summary = "회원 가입", description = "입력받은 회원 가입 정보로 회원 가입을 진행합니다.")
    public ResponseEntity<JoinResponse> join(@RequestBody JoinRequest request) {
        final JoinResponse joinResponse = userService.save(request);

        return ResponseEntity
                .status(CREATED)
                .body(joinResponse);
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "유저 프로필 조회", description = "유저의 프로필 정보를 조회합니다.")
    public ResponseEntity<ProfileResponse> getUserProfile(@PathVariable Long userId) {
        final ProfileResponse profile = userSearchService.getProfile(userId);

        return ResponseEntity.ok(profile);
    }

    @PatchMapping("/users/{userId}")
    @PreAuthorize("hasAnyRole('L1', 'L2', 'ADMIN')")
    @Operation(summary = "유저 프로필 업데이트", description = "유저의 프로필 정보를 업데이트합니다.")
    public ResponseEntity<UserResponse> updateUserProfile(@PathVariable Long userId, UpdateUserRequest request) {
        final UserResponse userResponse = userService.updateProfile(userId, request);

        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/users/role/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
<<<<<<< HEAD
    public ResponseEntity<UserResponse> updateUserRole(@PathVariable Long userId, @RequestBody UpdateUserRoleRequest request) {
        log.info("updateUserRole()");

        log.info("updateUserRoleRequest: {}", request.getRole());

        UserResponse userInfo = userService.updateRole(userId, request);
=======
    @Operation(summary = "유저 권한 업데이트", description = "유저의 권한을 업데이트합니다.")
    public ResponseEntity<UserResponse> updateUserRole(@PathVariable Long userId, @RequestBody UpdateUserRole request) {
        final UserResponse userInfo = userService.updateRole(userId, request);
>>>>>>> 906ff8918eade233b082b329cc74dc7d6099cb56

        return ResponseEntity.ok(userInfo);
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasAnyRole('L1', 'L2', 'ADMIN')")
    @Operation(summary = "유저 삭제", description = "유저의 isDeleted 값을 true로 변경합니다.")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
