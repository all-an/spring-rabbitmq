package com.allan.lessboilerplate_notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void notifyUser(Long accountId, String message) {
        logger.atLevel(Level.INFO).log("Sending notification to account " + accountId + ": " + message);
    }

}
