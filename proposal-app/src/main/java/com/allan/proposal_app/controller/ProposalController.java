package com.allan.proposal_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.allan.proposal_app.dto.ProposalRequestDto;
import com.allan.proposal_app.dto.ProposalResponseDto;
import com.allan.proposal_app.service.ProposalService;

@RestController
@RequestMapping("/proposal")
public class ProposalController {
	
	private ProposalService proposalService;
	
	@PostMapping
	public ResponseEntity<ProposalResponseDto> create(@RequestBody ProposalRequestDto requestDto) {
		ProposalResponseDto responseDto = proposalService.create(requestDto);
		return ResponseEntity.ok(responseDto);
	}

}
