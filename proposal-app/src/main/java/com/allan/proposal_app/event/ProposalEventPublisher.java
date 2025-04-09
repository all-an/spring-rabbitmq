package com.allan.proposal_app.event;

import com.allan.proposal_app.config.EventExchangeConfig;
import com.allan.proposal_app.entity.ProposalEntity;
import com.allan.proposal_app.response.ApiStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProposalEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public ProposalEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishProposalCreated(ProposalEntity proposal) {
        ProposalCreatedEvent event = new ProposalCreatedEvent(proposal.getId(), proposal.getAccountEntity().getId(),
                ApiStatus.CREATED.getMessage(), proposal.getAccountEntity().getName(), proposal.getAccountEntity().getPhone());
        rabbitTemplate.convertAndSend(EventExchangeConfig.EXCHANGE_NAME, EventExchangeConfig.ROUTING_KEY, event);
    }

}
