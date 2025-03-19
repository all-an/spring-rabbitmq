package com.allan.proposal_app.service;

import com.allan.proposal_app.entity.ProposalEntity;
import com.allan.proposal_app.converter.ProposalConverter;
import com.allan.proposal_app.repository.ProposalRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allan.proposal_app.dto.ProposalRequestDto;
import com.allan.proposal_app.dto.ProposalResponseDto;

@AllArgsConstructor
@Service
public class ProposalService {

	private ProposalRepository proposalRepository;

	@Autowired
	private ProposalConverter proposalConverter;
	
	public ProposalResponseDto create(ProposalRequestDto proposalRequestDto) {
		if (proposalRequestDto == null) {
			throw new RuntimeException("proposalRequestDto is required");
		}
		ProposalEntity proposalEntityResult = proposalConverter.
				convertProposalRequestDtoToProposalEntity(proposalRequestDto);
		proposalRepository.save(proposalEntityResult);
		return proposalConverter.convertProposalEntityToProposalResponseDto(proposalEntityResult);
	}

}
