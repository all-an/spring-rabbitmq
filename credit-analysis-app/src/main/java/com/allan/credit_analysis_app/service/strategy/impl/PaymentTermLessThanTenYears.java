package com.allan.credit_analysis_app.service.strategy.impl;

import com.allan.credit_analysis_app.domain.ProposalEntity;
import com.allan.credit_analysis_app.service.strategy.CreditPointsCalculation;
import org.springframework.stereotype.Component;

import static com.allan.credit_analysis_app.exception.NullVerifier.verifyFieldsNotNull;

@Component
public class PaymentTermLessThanTenYears implements CreditPointsCalculation {

    @Override
    public int calculatePoints(ProposalEntity proposalEntity) {
        verifyFieldsNotNull(proposalEntity, "paymentLimitInMonths");
        return proposalEntity.getPaymentLimitInMonths() < 120 ? 80 : 0;
    }
}
