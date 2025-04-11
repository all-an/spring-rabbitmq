package com.allan.credit_analysis_app.service.strategy.impl;

import com.allan.credit_analysis_app.domain.ProposalEntity;
import com.allan.credit_analysis_app.service.strategy.CreditPointsCalculation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeniedCreditImplTest {

    private final ProposalEntity dummyProposal = new ProposalEntity(); // mock data if needed

    @Test
    void shouldReturn100WhenCreditIsNotDenied() {
        // Subclass to override randomness
        CreditPointsCalculation calculation = new DeniedCreditImpl() {
            @Override
            protected boolean deniedCredit() {
                return false; // force "not denied"
            }
        };

        int points = calculation.calculatePoints(dummyProposal);
        assertThat(points).isEqualTo(100);
    }

    @Test
    void shouldThrowExceptionWhenCreditIsDenied() {
        CreditPointsCalculation calculation = new DeniedCreditImpl() {
            @Override
            protected boolean deniedCredit() {
                return true; // force "denied"
            }
        };

        assertThrows(RuntimeException.class, () -> calculation.calculatePoints(dummyProposal));
    }
}