package com.allan.credit_analysis_app.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProposalEntity {

    private Long id;

    private Double proposalValue;

    private int paymentLimitInMonths;

    private Boolean wasApproved;

    private boolean integrated;

    private String observation;

    private AccountEntity accountEntity;

}
