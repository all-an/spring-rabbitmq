package com.allan.proposal_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProposalRequestDto {
	
	private String name;
	
	private String surName;
	
	private String phone;
	
	private String cpf;
	
	private Double income;
	
	private Double proposalValue;
	
	private int paymentLimitInMonths;

}
