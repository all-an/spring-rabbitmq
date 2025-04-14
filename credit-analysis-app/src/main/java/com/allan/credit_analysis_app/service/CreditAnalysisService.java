package com.allan.credit_analysis_app.service;

import com.allan.credit_analysis_app.constant.ApprovalMessage;
import com.allan.credit_analysis_app.domain.ProposalEntity;
import com.allan.credit_analysis_app.exception.StrategyException;
import com.allan.credit_analysis_app.service.strategy.CreditPointsCalculation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CreditAnalysisService {

    private List<CreditPointsCalculation> creditPointsCalculations;

    @Autowired
    private RabbitNotificationService rabbitNotificationService;

    @Value("${rabbitmq.finishedproposal.exchange}")
    private String exchangeFinishedProposal;

    private final static int APPROVAL_SCORE = 350;

    public CreditAnalysisService(List<CreditPointsCalculation> creditPointsCalculations) {
        this.creditPointsCalculations = creditPointsCalculations;
    }

    public void analyze(ProposalEntity proposal) {
        if (!Objects.isNull(proposal)) {
            try {
                int customerScore = creditPointsCalculations.stream().mapToInt(impl ->
                        impl.calculatePoints(proposal)).sum();
                boolean approved =  customerScore > APPROVAL_SCORE;
                proposal.setWasApproved(approved);
                String statusMessage = approved ? ApprovalMessage.PROPOSAL_APPROVED : ApprovalMessage.PROPOSAL_NOT_APPROVED;
                proposal.setStatusMessage(statusMessage);
            } catch (StrategyException strategyException) {
                proposal.setWasApproved(false);
                proposal.setStatusMessage(strategyException.getMessage());
            }
            rabbitNotificationService.notify(proposal, exchangeFinishedProposal);
        }
    }
}
