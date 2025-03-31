package com.allan.proposal_app.service;

import com.allan.proposal_app.dto.ProposalResponseDto;
import com.allan.proposal_app.entity.ProposalEntity;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {

    private RabbitTemplate rabbitTemplate;

    public void notify(ProposalEntity proposalEntity, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", proposalEntity);
    }

}
