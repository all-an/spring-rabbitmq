package com.allan.proposal_app.event;

import com.allan.proposal_app.response.ApiStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProposalCreatedEvent {

    private Long proposalId;
    private Long accountId;
    private String status;
    private String accountUserName;
    private String phone;

}
