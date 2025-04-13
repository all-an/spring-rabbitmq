package com.allan.credit_analysis_app.service.strategy.impl;

import java.util.Random;

import com.allan.credit_analysis_app.constant.CreditDeniedMessage;
import com.allan.credit_analysis_app.domain.ProposalEntity;
import com.allan.credit_analysis_app.exception.StrategyException;
import com.allan.credit_analysis_app.service.strategy.CreditPointsCalculation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.allan.credit_analysis_app.exception.NullVerifier.verifyFieldsNotNull;

@Order(1)
@Component
public class DeniedCreditImpl implements CreditPointsCalculation{

    @Override
    public int calculatePoints(ProposalEntity proposalEntity) {
        verifyFieldsNotNull(proposalEntity, "accountEntity");
        if (deniedCredit()) {
            String message = String.format(CreditDeniedMessage.OBLIGATIONS_DENIED_LOG_MESSAGE,
                    proposalEntity.getAccountEntity().getName(),
                    proposalEntity.getAccountEntity().getId());
            throw new StrategyException(message);
        }
        return 100;
    }

    protected boolean deniedCredit() {
        return new Random().nextBoolean();
    }

}
