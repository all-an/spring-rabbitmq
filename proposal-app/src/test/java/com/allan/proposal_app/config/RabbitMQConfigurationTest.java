package com.allan.proposal_app.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

public class RabbitMQConfigurationTest {

    private RabbitMQConfiguration configuration;
    private ConnectionFactory connectionFactory;

    @BeforeEach
    void setUp() {
        connectionFactory = mock(ConnectionFactory.class);
        configuration = new RabbitMQConfiguration(connectionFactory);
    }

    @Test
    void shouldCreateRabbitAdmin() {
        RabbitAdmin rabbitAdmin = configuration.createRabbitAdmin(connectionFactory);
        assertThat(rabbitAdmin).isNotNull();
    }

    @Test
    void shouldInitializeRabbitAdminOnApplicationReadyEvent() {
        RabbitAdmin rabbitAdmin = mock(RabbitAdmin.class);
        ApplicationListener<ApplicationReadyEvent> listener = configuration.initializeAdmin(rabbitAdmin);

        listener.onApplicationEvent(mock(ApplicationReadyEvent.class));
        Mockito.verify(rabbitAdmin).initialize();
    }

    @Test
    void shouldCreatePendingProposalQueue() {
        Queue queue = configuration.createPendingProposalQueue();
        assertThat(queue.getName()).isEqualTo("pending-proposal.ms-credit-analysis");
    }

    @Test
    void shouldCreateNotificationQueue() {
        Queue queue = configuration.createNotificationQueue();
        assertThat(queue.getName()).isEqualTo("pending-proposal.ms-notification");
    }

    @Test
    void shouldCreateFinishedProposalQueue() {
        Queue queue = configuration.createFinishedProposalQueue();
        assertThat(queue.getName()).isEqualTo("finished-proposal.ms-proposal");
    }

    @Test
    void shouldCreateFinishedProposalNotificationQueue() {
        Queue queue = configuration.createFinishedProposalNotificationQueue();
        assertThat(queue.getName()).isEqualTo("finished-proposal.ms-notification");
    }

    @Test
    void shouldCreateFanoutExchangePendingProposal() {
        FanoutExchange exchange = configuration.createFanoutExchangePendingProposal();
        assertThat(exchange.getName()).isEqualTo("pending-proposal.ex");
    }

    @Test
    void shouldCreateBindingPendingProposalCreditAnalysis() {
        Binding binding = configuration.createBindingPendingProposalCreditAnalysis();
        assertThat(binding.getDestination()).isEqualTo("pending-proposal.ms-credit-analysis");
        assertThat(binding.getExchange()).isEqualTo("pending-proposal.ex");
    }

    @Test
    void shouldCreateBindingPendingProposalNotification() {
        Binding binding = configuration.createBindingPendingProposalNotification();
        assertThat(binding.getDestination()).isEqualTo("pending-proposal.ms-notification");
        assertThat(binding.getExchange()).isEqualTo("pending-proposal.ex");
    }
}