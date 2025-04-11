package com.allan.credit_analysis_app.service.strategy.impl;

import com.allan.credit_analysis_app.domain.ProposalEntity;
import com.allan.credit_analysis_app.service.strategy.CreditPointsCalculation;

import java.util.Random;

public class AnotherContractsVerifier implements CreditPointsCalculation{

    @Override
    public int calculatePoints(ProposalEntity proposalEntity) {
        return anotherContractsDetected() ? 0 : 80;
    }

    protected boolean anotherContractsDetected() {
        return new Random().nextBoolean();
    }

}
