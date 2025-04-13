package com.allan.credit_analysis_app.listener;

import com.allan.credit_analysis_app.service.CreditAnalysisService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import com.allan.credit_analysis_app.domain.ProposalEntity;

@Configuration
public class ProposalInAnalysisListener {

    private final CreditAnalysisService creditAnalysisService;

    public ProposalInAnalysisListener(CreditAnalysisService creditAnalysisService) {
        this.creditAnalysisService = creditAnalysisService;
    }

    @RabbitListener(queues = "${rabbitmq.pendingproposal.queueToMsCreditAnalysis}")
    public void proposalInAnalysis(ProposalEntity proposalEntity) {
        creditAnalysisService.analyze(proposalEntity);
    }
}
