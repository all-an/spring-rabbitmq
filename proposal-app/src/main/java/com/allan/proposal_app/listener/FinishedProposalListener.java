package com.allan.proposal_app.listener;

import com.allan.proposal_app.converter.ProposalConverter;
import com.allan.proposal_app.dto.ProposalResponseDto;
import com.allan.proposal_app.entity.ProposalEntity;
import com.allan.proposal_app.repository.ProposalRepository;
import com.allan.proposal_app.service.WebSocketService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FinishedProposalListener {

    private final ProposalRepository proposalRepository;

    private WebSocketService webSocketService;

    private ProposalConverter proposalConverter;

    @RabbitListener(queues = "${rabbitmq.finishedproposal.queueToMsProposal}")
    public void finishedProposal(ProposalEntity proposal) {
        ProposalEntity savedProposal = proposalRepository.save(proposal);
        ProposalResponseDto proposalResponseDto = proposalConverter
                .convertProposalEntityToProposalResponseDto(savedProposal);
        webSocketService.notify(proposalResponseDto);
    }

}
