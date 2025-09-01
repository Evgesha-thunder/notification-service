package com.bulish.service;

import com.bulish.dto.UserOperationEvent;
import com.bulish.strategy.NotificationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserNotificationServiceImpl implements UserNotificationService {
    private final EmailService emailService;
    private final List<NotificationStrategy> strategies;

    @Override
    @Async
    public void sendNotification(UserOperationEvent userOperationEvent) {
        log.info("sendNotification for event {}", userOperationEvent);

        String msg = strategies.stream()
                .filter(str -> str.supports(userOperationEvent.getUserOperation()))
                .findFirst()
                .map(str -> str.generateMessage(userOperationEvent))
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unsupported operation " + userOperationEvent.getUserOperation()));

        emailService.sendEmail(userOperationEvent.getEmail(), "User operation notification", msg);
    }
}
