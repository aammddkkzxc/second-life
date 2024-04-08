package com.example.secondlife.domain.verification.scheduling;

import com.example.secondlife.domain.verification.repository.VerificationRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class VerificationCleanupTask {

    private final VerificationRepository verificationRepository;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    @Transactional
    public void deleteExpiredVerifications() {
        LocalDateTime nowMinusOneMinute = LocalDateTime.now().minusMinutes(1);
        Date date = Date.from(nowMinusOneMinute.atZone(ZoneId.systemDefault()).toInstant());

        verificationRepository.deleteAllByExpiryDateBefore(date);
    }
}
