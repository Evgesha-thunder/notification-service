package com.bulish.strategy;

import com.bulish.dto.UserOperation;
import com.bulish.dto.UserOperationEvent;
import com.bulish.properties.NotificationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateNotificationStrategy implements NotificationStrategy {
    private final NotificationProperties notificationProperties;

    @Override
    public boolean supports(UserOperation operation) {
        return UserOperation.CREATE.equals(operation);
    }

    @Override
    public String generateMessage(UserOperationEvent event) {
        return notificationProperties.getMessages().getCreate();
    }
}
