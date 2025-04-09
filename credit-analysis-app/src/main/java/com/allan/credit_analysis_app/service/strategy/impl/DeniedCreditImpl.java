package com.allan.credit_analysis_app.service.strategy.impl;

import java.util.Random;

import com.allan.credit_analysis_app.domain.ProposalEntity;
import com.allan.credit_analysis_app.service.strategy.CreditPointsCalculation;

public class DeniedCreditImpl implements CreditPointsCalculation{

    @Override
    public int calculatePoints(ProposalEntity proposalEntity) {
        if (deniedCredit()) {
            throw new RuntimeException("Denied credit");
        }
        return 100;
    }

    private boolean deniedCredit() {
        return new Random().nextBoolean();
    }

}
