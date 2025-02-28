package com.allan.proposal_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_proposal")
public class ProposalEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "proposalvalue")
	private Double proposalValue;
	
	private int paymentLimitInMonths;
	
	private Boolean wasApproved;
	
	private boolean integrated;
	
	private String observation;
	
	@OneToOne
	@JoinColumn(name = "id_account")
	private AccountEntity accountEntity;

}
