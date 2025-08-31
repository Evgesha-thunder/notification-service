package com.bulish.strategy;

import com.bulish.dto.UserOperation;
import com.bulish.dto.UserOperationEvent;
import com.bulish.properties.NotificationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteNotificationStrategy implements NotificationStrategy {
    private final NotificationProperties notificationProperties;

    @Override
    public boolean supports(UserOperation operation) {
        return UserOperation.DELETE.equals(operation);
    }

    @Override
    public String generateMessage(UserOperationEvent event) {
        return notificationProperties.getMessages().getDelete();
    }
}
