package com.allan.proposal_app.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    private ConnectionFactory connectionFactory;

    public RabbitMQConfiguration(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public Queue createPendingProposalQueue() {
        return QueueBuilder.durable("pending-proposal.ms-credit-analysis").build();
    }

    @Bean
    public Queue createNotificationQueue() {
        return QueueBuilder.durable("pending-proposal.ms-notification").build();
    }

    @Bean
    public Queue createFinishedProposalQueue() {
        return QueueBuilder.durable("finished-proposal.ms-proposal").build();
    }

    @Bean
    public Queue createFinishedProposalNotificationQueue() {
        return QueueBuilder.durable("finished-proposal.ms-notification").build();
    }

    @Bean
    public FanoutExchange createFanoutExchangePendingProposal() {
        return ExchangeBuilder.fanoutExchange("pending-proposal.ex").build();
    }

    @Bean
    public Binding createBindingPendingProposalCreditAnalysis() {
        return BindingBuilder.bind(createPendingProposalQueue())
                .to(createFanoutExchangePendingProposal());
    }

    @Bean
    public Binding createBindingPendingProposalNotification() {
        return BindingBuilder.bind(createNotificationQueue())
                .to(createFanoutExchangePendingProposal());
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }


}
