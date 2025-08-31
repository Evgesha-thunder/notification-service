package com.bulish.strategy;

import com.bulish.dto.UserOperation;
import com.bulish.dto.UserOperationEvent;

public interface NotificationStrategy {
    boolean supports(UserOperation operation);
    String generateMessage(UserOperationEvent event);
}
