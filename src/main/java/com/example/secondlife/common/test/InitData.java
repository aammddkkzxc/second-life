package com.example.secondlife.common.test;

import com.example.secondlife.domain.user.dto.JoinRequest;
import com.example.secondlife.domain.user.enumType.Region;
import com.example.secondlife.domain.user.service.UserService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class InitData {
    private final UserService userService;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("init()");

        JoinRequest joinRequest = new JoinRequest(
                "test",
                "test",
                "nickname1",
                null,
                Region.GANGWON,
                LocalDate.now(),
                "안녕"
        );

        userService.save(joinRequest);
    }
}
