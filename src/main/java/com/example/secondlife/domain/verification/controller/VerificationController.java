package com.example.secondlife.domain.verification.controller;

import com.example.secondlife.domain.verification.dto.VerifyEmail;
import com.example.secondlife.domain.verification.service.VerificationService;
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
    public ResponseEntity<?> mailConfirm(@RequestParam("email") String email,
                                         @AuthenticationPrincipal(expression = "userId") Long userId) throws Exception {
        log.info("mailConfirm()");

        String code = verificationService.sendSimpleMessage(userId, email);

        log.info("인증코드 : " + code);

        return ResponseEntity.ok(code);
    }

    @PatchMapping("/verification/{userId}")
    public ResponseEntity<?> verifyEmail(@PathVariable Long userId,
                                         @RequestBody VerifyEmail verifyEmail) {
        log.info("verifyEmail()");

        verificationService.verifyEmailAndCode(userId, verifyEmail);

        return ResponseEntity.ok().build();
    }
}
