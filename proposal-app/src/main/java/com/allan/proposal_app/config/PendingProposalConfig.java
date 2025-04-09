package com.allan.proposal_app.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PendingProposalConfig {

    private final RabbitMQProperties properties;

    public PendingProposalConfig(RabbitMQProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Queue pendingProposalCreditAnalysisQueue() {
        return QueueBuilder.durable(properties.getPendingproposal().getToMsCreditAnalysis()).build();
    }

    @Bean
    public Queue pendingProposalNotificationQueue() {
        return QueueBuilder.durable(properties.getPendingproposal().getToMsNotification()).build();
    }

    @Bean
    public FanoutExchange pendingProposalExchange() {
        return ExchangeBuilder.fanoutExchange(properties.getPendingproposal().getExchange()).build();
    }

    @Bean
    public Binding bindingPendingProposalCreditAnalysis() {
        return BindingBuilder.bind(pendingProposalCreditAnalysisQueue())
                .to(pendingProposalExchange());
    }

    @Bean
    public Binding bindingPendingProposalNotification() {
        return BindingBuilder.bind(pendingProposalNotificationQueue())
                .to(pendingProposalExchange());
    }

}
