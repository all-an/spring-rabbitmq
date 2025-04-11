package com.allan.credit_analysis_app.service.strategy.impl;

import com.allan.credit_analysis_app.domain.ProposalEntity;
import com.allan.credit_analysis_app.service.strategy.CreditPointsCalculation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreImplTest {

    private final ProposalEntity dummyProposal = new ProposalEntity(); // replace with actual test data if needed

    @Test
    void shouldThrowExceptionWhenScoreIs200OrLess() {
        CreditPointsCalculation calculation = new ScoreImpl() {
            @Override
            protected int getScore() {
                return 200;
            }
        };

        assertThrows(RuntimeException.class, () -> calculation.calculatePoints(dummyProposal));
    }

    @Test
    void shouldReturn150WhenScoreIsBetween201And400() {
        CreditPointsCalculation calculation = new ScoreImpl() {
            @Override
            protected int getScore() {
                return 300;
            }
        };

        int points = calculation.calculatePoints(dummyProposal);
        assertThat(points).isEqualTo(150);
    }

    @Test
    void shouldReturn180WhenScoreIsBetween401And600() {
        CreditPointsCalculation calculation = new ScoreImpl() {
            @Override
            protected int getScore() {
                return 500;
            }
        };

        int points = calculation.calculatePoints(dummyProposal);
        assertThat(points).isEqualTo(180);
    }

    @Test
    void shouldReturn220WhenScoreIsAbove600() {
        CreditPointsCalculation calculation = new ScoreImpl() {
            @Override
            protected int getScore() {
                return 700;
            }
        };

        int points = calculation.calculatePoints(dummyProposal);
        assertThat(points).isEqualTo(220);
    }
}