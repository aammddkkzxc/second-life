package com.example.secondlife.domain.verification.scheduling;

import com.example.secondlife.domain.verification.repository.VerificationRepository;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class VerificationCleanupTask {

    private final VerificationRepository verificationRepository;

    @Scheduled(fixedRate = 300000) // 매 5분마다 실행
    @Transactional
    public void deleteExpiredVerifications() {
        Date now = new Date();
        verificationRepository.deleteAllByExpiryDateBefore(now);
    }
}
