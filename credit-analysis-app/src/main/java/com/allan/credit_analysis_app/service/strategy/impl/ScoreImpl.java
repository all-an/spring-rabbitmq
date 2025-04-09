package com.allan.credit_analysis_app.service.strategy.impl;

import java.util.Random;

import com.allan.credit_analysis_app.domain.ProposalEntity;
import com.allan.credit_analysis_app.service.strategy.CreditPointsCalculation;

public class ScoreImpl implements CreditPointsCalculation{

    @Override
    public int calculatePoints(ProposalEntity proposalEntity) {
        int score = getScore();
        if (score <= 200) {
            throw new RuntimeException("Score denied");
        }
        if (score <= 400) {
            return 150;
        }
        if (score <= 600) {
            return 180;
        }
        return 220;
    }

    private int getScore() {
        return new Random().nextInt(0, 1000);
    }

}
