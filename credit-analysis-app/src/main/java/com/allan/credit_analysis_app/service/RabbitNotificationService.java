package com.allan.credit_analysis_app.service;

import com.allan.credit_analysis_app.domain.ProposalEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitNotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void notify(ProposalEntity proposalEntity, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", proposalEntity);
    }
}
