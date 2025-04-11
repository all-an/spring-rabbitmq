package com.allan.credit_analysis_app.service.strategy.impl;

import com.allan.credit_analysis_app.domain.ProposalEntity;
import com.allan.credit_analysis_app.service.strategy.CreditPointsCalculation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnotherContractsVerifierTest {

    private final ProposalEntity dummyProposal = new ProposalEntity(); // stub or mock if needed

    @Test
    void shouldReturnZeroWhenAnotherContractIsDetected() {
        CreditPointsCalculation verifier = new AnotherContractsVerifier() {
            @Override
            protected boolean anotherContractsDetected() {
                return true; // simulate detection
            }
        };

        int points = verifier.calculatePoints(dummyProposal);
        assertThat(points).isEqualTo(0);
    }

    @Test
    void shouldReturn80WhenNoOtherContractIsDetected() {
        CreditPointsCalculation verifier = new AnotherContractsVerifier() {
            @Override
            protected boolean anotherContractsDetected() {
                return false; // simulate no detection
            }
        };

        int points = verifier.calculatePoints(dummyProposal);
        assertThat(points).isEqualTo(80);
    }
}