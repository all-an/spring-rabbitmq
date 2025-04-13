package com.allan.credit_analysis_app.service.strategy.impl;

import java.util.Random;

import com.allan.credit_analysis_app.constant.CreditDeniedMessage;
import com.allan.credit_analysis_app.domain.ProposalEntity;
import com.allan.credit_analysis_app.exception.StrategyException;
import com.allan.credit_analysis_app.service.strategy.CreditPointsCalculation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.allan.credit_analysis_app.exception.NullVerifier.verifyFieldsNotNull;

@Order(2)
@Component
public class ScoreImpl implements CreditPointsCalculation{

    @Override
    public int calculatePoints(ProposalEntity proposalEntity) {
        verifyFieldsNotNull(proposalEntity, "accountEntity");
        int score = getScore();
        if (score <= 200) {
            String message = String.format(CreditDeniedMessage.LOW_SCORE_DENIED,
                    proposalEntity.getAccountEntity().getName(),
                    proposalEntity.getAccountEntity().getId());
            throw new StrategyException(message);
        }
        if (score <= 400) {
            return 150;
        }
        if (score <= 600) {
            return 180;
        }
        return 220;
    }

    protected int getScore() {
        return new Random().nextInt(0, 1000);
    }

}
