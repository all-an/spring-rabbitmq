package com.allan.proposal_app.service;

import com.allan.proposal_app.entity.ProposalEntity;
import com.allan.proposal_app.converter.ProposalConverter;
import com.allan.proposal_app.repository.ProposalRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allan.proposal_app.dto.ProposalRequestDto;
import com.allan.proposal_app.dto.ProposalResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProposalService {

	private ProposalRepository proposalRepository;

	private NotificationService notificationService;

	@Autowired
	private ProposalConverter proposalConverter;
	
	public ProposalResponseDto create(ProposalRequestDto proposalRequestDto) {
		if (proposalRequestDto == null) {
			throw new RuntimeException("proposalRequestDto is required");
		}
		ProposalEntity proposalEntityResult = proposalConverter.
				convertProposalRequestDtoToProposalEntity(proposalRequestDto);
		proposalRepository.save(proposalEntityResult);

		ProposalResponseDto proposalResponseDto = proposalConverter.convertProposalEntityToProposalResponseDto(proposalEntityResult);

		notificationService.notify(proposalResponseDto, "pending-proposal.ex");

		return proposalResponseDto;
	}

	public List<ProposalResponseDto> getProposals(int page, int size) {
		int offset = page * size; // Calculate offset based on page number and size
		List<ProposalEntity> proposals = proposalRepository.findProposalsWithPagination(offset, size);

		return proposals.stream()
				.map(proposalConverter::convertProposalEntityToProposalResponseDto)
				.collect(Collectors.toList());
	}

}
