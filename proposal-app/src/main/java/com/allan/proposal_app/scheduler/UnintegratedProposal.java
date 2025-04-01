package com.allan.proposal_app.scheduler;

import com.allan.proposal_app.entity.ProposalEntity;
import com.allan.proposal_app.repository.ProposalRepository;
import com.allan.proposal_app.service.RabbitMQNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class UnintegratedProposal {

    private final ProposalRepository proposalRepository;

    private final RabbitMQNotificationService rabbitMQNotificationService;

    private final String exchange;

    private final Logger logger = LoggerFactory.getLogger(UnintegratedProposal.class);

    public UnintegratedProposal(ProposalRepository proposalRepository,
                                RabbitMQNotificationService rabbitMQNotificationService,
                                @Value("${rabbitmq.pendingproposal.exchange}") String exchange) {
        this.proposalRepository = proposalRepository;
        this.rabbitMQNotificationService = rabbitMQNotificationService;
        this.exchange = exchange;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void updateUnintegratedProposal() {
        proposalRepository.findAllByIntegratedIsFalse().forEach(proposal -> {
            try {
                rabbitMQNotificationService.notify(proposal, exchange);
                updateProposal(proposal);
            } catch (RuntimeException ex) {
                logger.error(ex.getMessage());
            }
        });
    }

    private void updateProposal(ProposalEntity proposal) {
        proposal.setIntegrated(true);
        proposalRepository.save(proposal);
    }

}
