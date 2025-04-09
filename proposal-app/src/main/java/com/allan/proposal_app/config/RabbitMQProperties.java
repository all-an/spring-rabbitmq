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

    @Getter
    @Setter
    public static class PendingProposal {
        private String exchange;
        private String toMsCreditAnalysis;
        private String toMsNotification;
    }

    @Getter
    @Setter
    public static class FinishedProposal {
        private String exchange;
        private String toMsProposal;
        private String toMsNotification;
    }

}
