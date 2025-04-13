package com.allan.proposal_app.listener;

import com.allan.proposal_app.entity.ProposalEntity;
import com.allan.proposal_app.repository.ProposalRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FinishedProposalListener {

    private final ProposalRepository proposalRepository;

    public FinishedProposalListener(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }

    @RabbitListener(queues = "${rabbitmq.finishedproposal.queueToMsProposal}")
    public void finishedProposal(ProposalEntity proposal) {
        proposalRepository.save(proposal);
    }

}
