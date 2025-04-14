package com.allan.proposal_app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_proposal")
public class ProposalEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "proposalvalue")
	private Double proposalValue;
	
	private int paymentLimitInMonths;
	
	private Boolean wasApproved;

	@Column(columnDefinition = "boolean default true")
	private boolean integrated;
	
	private String statusMessage;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_account")
	@JsonManagedReference
	private AccountEntity accountEntity;

}
