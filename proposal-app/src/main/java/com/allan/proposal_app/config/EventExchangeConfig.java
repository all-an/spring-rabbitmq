package com.allan.proposal_app.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventExchangeConfig {

    public static final String EXCHANGE_NAME = "proposal.event-another-notification.exchange";
    public static final String QUEUE_NAME = "proposal.event-another-notification.queue";
    public static final String ROUTING_KEY = "proposal.event-another-notification.created";

    @Bean
    public DirectExchange eventExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_NAME).build();
    }

    @Bean
    public Queue eventQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    @Bean
    public Binding eventBinding() {
        return BindingBuilder.bind(eventQueue())
                .to(eventExchange())
                .with(ROUTING_KEY);
    }

}
