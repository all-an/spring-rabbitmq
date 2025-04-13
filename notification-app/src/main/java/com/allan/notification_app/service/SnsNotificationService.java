package com.allan.notification_app.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnsNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(SnsNotificationService.class);

//    @Autowired
//    private AmazonSNS amazonSNS;

    public void notify(String phone, String message) {
        if (phone == null || phone.trim().isEmpty()) {
            logger.error("Phone number cannot be null or empty");
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }

        if (message == null || message.trim().isEmpty()) {
            logger.error("Message content cannot be null or empty");
            throw new IllegalArgumentException("Message content cannot be null or empty");
        }
//        PublishRequest publishRequest = new PublishRequest()
//                .withMessage(message)
//                        .withPhoneNumber(phone);
//        amazonSNS.publish(publishRequest);
        logger.atLevel(Level.INFO).log("Message send: " + message);
    }

}
