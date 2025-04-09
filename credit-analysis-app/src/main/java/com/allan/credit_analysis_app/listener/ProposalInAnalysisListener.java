package com.allan.credit_analysis_app.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import com.allan.credit_analysis_app.domain.ProposalEntity;

@Configuration
public class ProposalInAnalysisListener {

    @RabbitListener(queues = "${queue.pending-proposal.to-ms-credit-analysis}")
    public void proposalInAnalysis(ProposalEntity proposalEntity) {

    }
}
