package com.allan.lessboilerplate_notification.event;

import com.allan.lessboilerplate_notification.config.RabbitMQConfig;
import com.allan.lessboilerplate_notification.dto.ProposalCreatedEvent;
import com.allan.lessboilerplate_notification.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProposalEventListener {

    private final NotificationService notificationService;

    public ProposalEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleProposalCreated(ProposalCreatedEvent event) {
        notificationService.notifyUser(event.getAccountId(), event.getAccountUserName() + ", your proposal has been created with status: " + event.getStatus());
    }

}
