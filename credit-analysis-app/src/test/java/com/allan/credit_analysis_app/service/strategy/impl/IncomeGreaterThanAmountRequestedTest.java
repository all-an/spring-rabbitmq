package com.allan.credit_analysis_app.service.strategy.impl;

import com.allan.credit_analysis_app.domain.AccountEntity;
import com.allan.credit_analysis_app.domain.ProposalEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IncomeGreaterThanAmountRequestedTest {

    private final IncomeGreaterThanAmountRequested strategy = new IncomeGreaterThanAmountRequested();

    @Test
    void testCalculatePoints_validProposal_incomeGreater() {
        ProposalEntity proposal = new ProposalEntity();
        AccountEntity account = new AccountEntity();
        account.setIncome(10000.0);
        proposal.setProposalValue(5000.0);
        proposal.setAccountEntity(account);

        int result = strategy.calculatePoints(proposal);
        assertEquals(100, result);
    }

    @Test
    void testCalculatePoints_validProposal_incomeLess() {
        ProposalEntity proposal = new ProposalEntity();
        AccountEntity account = new AccountEntity();
        account.setIncome(4000.0);
        proposal.setProposalValue(5000.0);
        proposal.setAccountEntity(account);

        int result = strategy.calculatePoints(proposal);
        assertEquals(0, result);
    }

    @Test
    void testCalculatePoints_nullAccountEntity() {
        ProposalEntity proposal = new ProposalEntity();
        proposal.setProposalValue(5000.0);
        proposal.setAccountEntity(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                strategy.calculatePoints(proposal));
        assertTrue(exception.getMessage().contains("accountEntity"));
    }

    @Test
    void testCalculatePoints_nullProposalValue() {
        ProposalEntity proposal = new ProposalEntity();
        AccountEntity account = new AccountEntity();
        account.setIncome(5000.0);
        proposal.setAccountEntity(account);
        proposal.setProposalValue(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                strategy.calculatePoints(proposal));
        assertTrue(exception.getMessage().contains("proposalValue"));
    }

    @Test
    void testCalculatePoints_nullIncome() {
        ProposalEntity proposal = new ProposalEntity();
        AccountEntity account = new AccountEntity();
        account.setIncome(null);
        proposal.setProposalValue(5000.0);
        proposal.setAccountEntity(account);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                strategy.calculatePoints(proposal));
        assertTrue(exception.getMessage().contains("income"));
    }
}
