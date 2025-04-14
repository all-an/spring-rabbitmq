package com.allan.proposal_app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitMQProperties {

    private PendingProposal pendingproposal;
    private FinishedProposal finishedproposal;
    private DeadLetter deadLetter;

    @Getter
    @Setter
    public static class PendingProposal {
        private String exchange;
        private String queueToMsCreditAnalysis;
        private String queueToMsNotification;
    }

    @Getter
    @Setter
    public static class FinishedProposal {
        private String exchange;
        private String queueToMsProposal;
        private String queueToMsNotification;
    }

    @Getter
    @Setter
    public static class DeadLetter {
        private String exchange;
        private String queuePendingProposalDLQ;
    }

}
