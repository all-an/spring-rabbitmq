package com.allan.proposal_app.controller;

import com.allan.proposal_app.response.ApiResponse;
import com.allan.proposal_app.response.ApiStatus;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.allan.proposal_app.dto.ProposalRequestDto;
import com.allan.proposal_app.dto.ProposalResponseDto;
import com.allan.proposal_app.service.ProposalService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/proposal")
public class ProposalController {

	private ProposalService proposalService;

	@PostMapping
	public ApiResponse<ProposalResponseDto> create(@RequestBody ProposalRequestDto requestDto) {
		ProposalResponseDto responseDto = proposalService.create(requestDto);
		return new ApiResponse<>(ApiStatus.CREATED, responseDto);
	}

	@GetMapping // example: GET /proposal?page=0&size=10
	public ApiResponse<List<ProposalResponseDto>> getProposals(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size
	) {
		List<ProposalResponseDto> responseDtos = proposalService.getProposals(page, size);
		return new ApiResponse<>(ApiStatus.SUCCESS, responseDtos);
	}


}
