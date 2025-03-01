package com.allan.proposal_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProposalResponseDto {
	
	private Long id;
	
	private String name;
	
	private String surName;
	
	private Double proposalValue;
	
	private int paymentLimitInMonths;
	
	private Boolean wasApproved;
	
	private String observation;

}
