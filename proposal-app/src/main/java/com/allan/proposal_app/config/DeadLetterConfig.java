package com.allan.proposal_app.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeadLetterConfig {

    private final RabbitMQProperties properties;

    public DeadLetterConfig(RabbitMQProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Queue pendingProposalDeadLetterQueue() {
        return QueueBuilder.durable(properties.getDeadLetter()
                .getQueuePendingProposalDLQ()).build();
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        return ExchangeBuilder.fanoutExchange(properties.getDeadLetter()
                .getExchange()).build();
    }

    @Bean
    public Binding createBiding(){
        return BindingBuilder.bind(pendingProposalDeadLetterQueue())
                .to(deadLetterExchange());
    }

}
