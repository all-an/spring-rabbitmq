package com.allan.credit_analysis_app.service.strategy.impl;

import com.allan.credit_analysis_app.domain.ProposalEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTermLessThanTenYearsTest {

    private final PaymentTermLessThanTenYears strategy = new PaymentTermLessThanTenYears();

    @Test
    void shouldReturn80WhenPaymentTermIsLessThan120Months() {
        ProposalEntity proposal = new ProposalEntity();
        proposal.setPaymentLimitInMonths(100);

        int result = strategy.calculatePoints(proposal);

        assertEquals(80, result);
    }

    @Test
    void shouldReturn0WhenPaymentTermIs120OrMoreMonths() {
        ProposalEntity proposal = new ProposalEntity();
        proposal.setPaymentLimitInMonths(120);

        int result = strategy.calculatePoints(proposal);

        assertEquals(0, result);
    }
}