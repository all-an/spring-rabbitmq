package com.allan.proposal_app.service;

import com.allan.proposal_app.entity.ProposalEntity;
import com.allan.proposal_app.repository.ProposalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.allan.proposal_app.dto.ProposalRequestDto;
import com.allan.proposal_app.dto.ProposalResponseDto;

@AllArgsConstructor
@Service
public class ProposalService {

	private ProposalRepository proposalRepository;
	
	public ProposalResponseDto create(ProposalRequestDto proposalRequestDto) {
		proposalRepository.save(new ProposalEntity());
		return null;
	}

}
