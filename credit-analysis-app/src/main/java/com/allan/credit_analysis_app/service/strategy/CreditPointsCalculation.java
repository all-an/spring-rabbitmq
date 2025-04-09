package com.allan.credit_analysis_app.service.strategy;

import com.allan.credit_analysis_app.domain.ProposalEntity;

public interface CreditPointsCalculation {

    int calculatePoints(ProposalEntity proposalEntity);

}
