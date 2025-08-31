package com.bulish.listener;

import com.bulish.dto.UserOperationEvent;
import com.bulish.service.UserNotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserOperationEventListener {

    private final ObjectMapper objectMapper;
    private final UserNotificationService notificationService;

    @KafkaListener(
            topics = "${kafka.topic.user-operations:user-events}",
            groupId = "${kafka.consumer.group-id:user-group}")
    public void userOperationListener(@Payload String message) {
        try {
            System.out.println(message);
            UserOperationEvent event = objectMapper.readValue(message, UserOperationEvent.class);
            log.info("Received event: {}", event);
            notificationService.sendNotification(event);
        } catch (Exception e) {
            log.error("Error processing message: {}", message, e);
        }
    }
}