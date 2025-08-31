package com.bulish.service;

import com.bulish.dto.UserOperationEvent;

public interface UserNotificationService {

    void sendNotification(UserOperationEvent userOperationEvent);
}
