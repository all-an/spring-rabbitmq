package com.allan.proposal_app.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.pendingproposal.exchange}")
    private String exchangePendingProposal;

    @Value("${rabbitmq.finishedproposal.exchange}")
    private String exchangeFinishedProposal;

    @Value("${queue.pending-proposal.to-ms-credit-analysis}")
    private String queuePendingProposalToCreditAnalysisApp;

    @Value("${queue.pending-proposal.to-ms-notification}")
    private String queuePendingProposalToNotificationApp;

    @Value("${queue.finished-proposal.to-ms-proposal}")
    private String queueFinishedProposalToProposalApp;

    @Value("${queue.finished-proposal.to-ms-notification}")
    private String queueFinishedProposalToNotificationApp;

    private ConnectionFactory connectionFactory;

    public static final String EXCHANGE_NAME = "proposal.event.exchange";
    public static final String QUEUE_NAME = "proposal.event.queue";
    public static final String ROUTING_KEY = "proposal.event.created";

    public RabbitMQConfiguration(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public DirectExchange exchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_NAME).build();
    }

    @Bean
    public Queue eventQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    @Bean
    public Binding createEventBinding() {
        return BindingBuilder.bind(eventQueue()).to(exchange()).with(ROUTING_KEY);
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
    public Queue createCreditAnalysisQueueToCreditAnalysisApp() {
        return QueueBuilder.durable(queuePendingProposalToCreditAnalysisApp).build();
    }

    @Bean
    public Queue createNotificationQueue() {
        return QueueBuilder.durable(queuePendingProposalToNotificationApp).build();
    }

    @Bean
    public Queue createFinishedProposalQueueToProposalApp() {
        return QueueBuilder.durable(queueFinishedProposalToProposalApp).build();
    }

    @Bean
    public Queue createFinishedProposalQueueToNotificationApp() {
        return QueueBuilder.durable(queueFinishedProposalToNotificationApp).build();
    }

    @Bean
    public FanoutExchange createFanoutExchangePendingProposal() {
        return ExchangeBuilder.fanoutExchange(exchangePendingProposal).build();
    }

    @Bean
    public Binding createBindingPendingProposalCreditAnalysis() {
        return BindingBuilder.bind(createCreditAnalysisQueueToCreditAnalysisApp())
                .to(createFanoutExchangePendingProposal());
    }

    @Bean
    public Binding createBindingPendingProposalNotification() {
        return BindingBuilder.bind(createNotificationQueue())
                .to(createFanoutExchangePendingProposal());
    }

    @Bean
    public FanoutExchange createFanoutExchangeFinishedProposal() {
        return ExchangeBuilder.fanoutExchange(exchangeFinishedProposal).build();
    }

    /**
     * Returns the Binding between 'Queue Finished Proposal - Proposal App' and
     * 'Finished Proposal Exchange'
     *
     * @return Binding between the Queue and Exchange described
     */
    @Bean
    public Binding createBindingFinishedProposalProposalApp() {
        return BindingBuilder.bind(createFinishedProposalQueueToProposalApp())
                .to(createFanoutExchangeFinishedProposal());
    }

    /**
     * Returns the Binding between 'Queue Finished Proposal - Notification App' and
     * 'Finished Proposal Exchange'
     *
     * @return Binding between the Queue and Exchange described
     */
    @Bean
    public Binding createBindingFinishedProposalNotificationApp() {
        return BindingBuilder.bind(createFinishedProposalQueueToNotificationApp())
                .to(createFanoutExchangeFinishedProposal());
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
