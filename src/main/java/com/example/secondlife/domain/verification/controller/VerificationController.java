package com.example.secondlife.domain.verification.controller;

import com.example.secondlife.domain.verification.dto.VerifyRequest;
import com.example.secondlife.domain.verification.service.VerificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class VerificationController {

    private final VerificationService verificationService;

    @PostMapping("/verification")
    @Operation(summary = "이메일 인증 코드 발송",
            description =
                    "User Table 에 입력받은 이메일의 인증 내역이 없다면, "
                            + "인증 코드 만들어 입력받은 이메일 주소로 전송하고 "
                            + "Verification Table 에 해당 유저의 PK, 인증 코드, 만료일을 저장합니다.")
    public ResponseEntity<?> sendEmailWithCode(@RequestParam("email") String email,
                                               @AuthenticationPrincipal(expression = "userId") Long userId) throws Exception {
        String code = verificationService.sendSimpleMessage(userId, email);

        log.info("인증코드 : " + code);

        return ResponseEntity.ok(code);
    }

    @PatchMapping("/verification/{userId}")
    @Operation(summary = "인증 코드 확인", description = "입력받은 인증 코드가 유효한지 확인하고, 유효하다면 이메일 인증을 활성화합니다")
    public ResponseEntity<?> validateCodeAndActivateEmail(@PathVariable Long userId,
                                                          @RequestBody VerifyRequest verifyRequest) {
        verificationService.validateAndActivateEmailVerification(userId, verifyRequest);

        return ResponseEntity.ok().build();
    }

}
