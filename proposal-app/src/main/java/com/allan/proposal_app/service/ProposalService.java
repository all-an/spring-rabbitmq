package com.allan.proposal_app.service;

import com.allan.proposal_app.entity.ProposalEntity;
import com.allan.proposal_app.converter.ProposalConverter;
import com.allan.proposal_app.event.ProposalEventPublisher;
import com.allan.proposal_app.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.allan.proposal_app.dto.ProposalRequestDto;
import com.allan.proposal_app.dto.ProposalResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProposalService {

	private ProposalRepository proposalRepository;

	private RabbitMQNotificationService rabbitMQNotificationService;

	private final ProposalEventPublisher eventPublisher;

	@Autowired
	private ProposalConverter proposalConverter;

	private String exchange;

	public ProposalService(ProposalRepository proposalRepository,
						   RabbitMQNotificationService rabbitMQNotificationService,
						   ProposalConverter proposalConverter,
						   @Value("${rabbitmq.pendingproposal.exchange}") String exchange,
						   ProposalEventPublisher eventPublisher) {
		this.proposalRepository = proposalRepository;
		this.rabbitMQNotificationService = rabbitMQNotificationService;
		this.proposalConverter = proposalConverter;
		this.exchange = exchange;
		this.eventPublisher = eventPublisher;
	}

	public ProposalResponseDto create(ProposalRequestDto proposalRequestDto) {
		if (proposalRequestDto == null) {
			throw new RuntimeException("proposalRequestDto is required");
		}
		ProposalEntity proposalEntityResult = proposalConverter.
				convertProposalRequestDtoToProposalEntity(proposalRequestDto);
		proposalRepository.save(proposalEntityResult);

		notifyRabbitMQ(proposalEntityResult);

		eventPublisher.publishProposalCreated(proposalEntityResult);

        return proposalConverter.convertProposalEntityToProposalResponseDto(proposalEntityResult);
	}

	public List<ProposalResponseDto> getProposals(int page, int size) {
		int offset = page * size; // Calculate offset based on page number and size
		List<ProposalEntity> proposals = proposalRepository.findProposalsWithPagination(offset, size);

		return proposals.stream()
				.map(proposalConverter::convertProposalEntityToProposalResponseDto)
				.collect(Collectors.toList());
	}

	private void notifyRabbitMQ(ProposalEntity proposalEntity) {
		try {
			rabbitMQNotificationService.notify(proposalEntity, exchange);
		} catch (RuntimeException e) {
			proposalEntity.setIntegrated(false);
			proposalRepository.save(proposalEntity);
			throw new RuntimeException("Proposal not integrated.");
		}
	}

}
