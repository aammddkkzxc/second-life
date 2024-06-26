package com.example.secondlife.domain.verification.service;

import com.example.secondlife.common.exception.AuthenticationException;
import com.example.secondlife.common.exception.ExistException;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.service.UserSearchService;
import com.example.secondlife.domain.user.service.UserService;
import com.example.secondlife.domain.verification.dto.VerifyRequest;
import com.example.secondlife.domain.verification.entity.Verification;
import com.example.secondlife.domain.verification.repository.VerificationRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VerificationService {

    private final UserSearchService userSearchService;
    private final UserService userService;
    private final VerificationRepository verificationRepository;
    private final JavaMailSender emailSender;

    public String sendSimpleMessage(Long userId, String email) throws Exception {
        if (userSearchService.existByEmail(email)) {
            throw new ExistException("이미 가입된 이메일입니다.");
        }

        String code = createKey();

        MimeMessage message = createMessage(email, code);
        emailSender.send(message);

        User findUser = userSearchService.findById(userId);

        Verification verification = toVerification(code, findUser);

        verificationRepository.save(verification);

        return code;
    }

    public void validateAndActivateEmailVerification(Long userId, VerifyRequest verifyRequest) {
        Verification verification = findVerificationByCodeAndUserId(verifyRequest.getCode(), userId);

        ensureVerificationNotExpired(verification);

        activateUserEmailVerification(userId, verifyRequest.getEmail());
    }

    private Verification findVerificationByCodeAndUserId(String code, Long userId) {
        return verificationRepository.findByVerificationCodeAndUserId(code, userId)
                .orElseThrow(() -> new AuthenticationException("인증코드가 일치하지 않습니다."));
    }

    private void ensureVerificationNotExpired(Verification verification) {
        if (verification.getExpiryDate().before(new Date())) {
            throw new AuthenticationException("인증코드가 만료되었습니다.");
        }
    }

    private void activateUserEmailVerification(Long userId, String email) {
        userService.updateVerify(userId, email);
    }

    private String createKey() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "").substring(0, 12);
    }

    private Date generateExpiryDate() {
        final int EXPIRY_DURATION_MINUTES = 1;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, EXPIRY_DURATION_MINUTES);

        return calendar.getTime();
    }

    private MimeMessage createMessage(String to, String code) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);
        message.setSubject("Second-Life 회원가입 이메일 인증");

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요</h1>";
        msgg += "<h1> 새로운 인생을 응원하는 Second-Life 입니다</h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<p>항상 당신의 꿈을 응원합니다. 감사합니다!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += code + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");

        message.setFrom(new InternetAddress("tltltlsl11@naver.com", "Second-Life"));

        return message;
    }

    private Verification toVerification(String code, User findUser) {
        Date date = generateExpiryDate();

        return Verification.builder()
                .user(findUser)
                .verificationCode(code)
                .expiryDate(date)
                .build();
    }
}
