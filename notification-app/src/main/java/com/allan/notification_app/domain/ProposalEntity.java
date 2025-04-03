package com.allan.notification_app.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
