package com.allan.credit_analysis_app.service.strategy.impl;

import com.allan.credit_analysis_app.domain.ProposalEntity;
import com.allan.credit_analysis_app.service.strategy.CreditPointsCalculation;
import org.springframework.stereotype.Component;

import static com.allan.credit_analysis_app.exception.NullVerifier.verifyFieldsNotNull;

@Component
public class IncomeGreaterThanAmountRequested implements CreditPointsCalculation {
    @Override
    public int calculatePoints(ProposalEntity proposalEntity) {
        verifyFieldsNotNull(proposalEntity, "accountEntity", "proposalValue");
        verifyFieldsNotNull(proposalEntity.getAccountEntity(), "income");
        return incomeGreaterThanAmountRequested(proposalEntity) ? 100 : 0;
    }

    protected boolean incomeGreaterThanAmountRequested(ProposalEntity proposal) {
        return proposal.getAccountEntity().getIncome() > proposal.getProposalValue();
    }
}
