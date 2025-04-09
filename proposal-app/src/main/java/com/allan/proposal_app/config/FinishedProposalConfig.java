package com.allan.proposal_app.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FinishedProposalConfig {

    private final RabbitMQProperties properties;

    public FinishedProposalConfig(RabbitMQProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Queue finishedProposalProposalQueue() {
        return QueueBuilder.durable(properties.getFinishedproposal().getToMsProposal()).build();
    }

    @Bean
    public Queue finishedProposalNotificationQueue() {
        return QueueBuilder.durable(properties.getFinishedproposal().getToMsNotification()).build();
    }

    @Bean
    public FanoutExchange finishedProposalExchange() {
        return ExchangeBuilder.fanoutExchange(properties.getFinishedproposal().getExchange()).build();
    }

    @Bean
    public Binding bindingFinishedProposalToProposalApp() {
        return BindingBuilder.bind(finishedProposalProposalQueue())
                .to(finishedProposalExchange());
    }

    @Bean
    public Binding bindingFinishedProposalToNotificationApp() {
        return BindingBuilder.bind(finishedProposalNotificationQueue())
                .to(finishedProposalExchange());
    }

}
