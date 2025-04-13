package com.allan.proposal_app.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FinishedProposalConfigTest {

    private RabbitMQProperties mockProperties;
    private RabbitMQProperties.FinishedProposal finishedProposal;

    private FinishedProposalConfig config;

    @BeforeEach
    void setUp() {
        // Mocking the nested properties
        mockProperties = mock(RabbitMQProperties.class);
        finishedProposal = mock(RabbitMQProperties.FinishedProposal.class);

        when(mockProperties.getFinishedproposal()).thenReturn(finishedProposal);
        when(finishedProposal.getQueueToMsProposal()).thenReturn("queue.finished.proposal.app");
        when(finishedProposal.getQueueToMsNotification()).thenReturn("queue.finished.notification.app");
        when(finishedProposal.getExchange()).thenReturn("exchange.finished.proposal");

        config = new FinishedProposalConfig(mockProperties);
    }

    @Test
    void shouldCreateFinishedProposalProposalQueue() {
        Queue queue = config.finishedProposalProposalQueue();

        assertThat(queue).isNotNull();
        assertThat(queue.getName()).isEqualTo("queue.finished.proposal.app");
        assertThat(queue.isDurable()).isTrue();
    }

    @Test
    void shouldCreateFinishedProposalNotificationQueue() {
        Queue queue = config.finishedProposalNotificationQueue();

        assertThat(queue).isNotNull();
        assertThat(queue.getName()).isEqualTo("queue.finished.notification.app");
        assertThat(queue.isDurable()).isTrue();
    }

    @Test
    void shouldCreateFanoutExchange() {
        FanoutExchange exchange = config.finishedProposalExchange();

        assertThat(exchange).isNotNull();
        assertThat(exchange.getName()).isEqualTo("exchange.finished.proposal");
    }

    @Test
    void shouldBindProposalQueueToExchange() {
        Binding binding = config.bindingFinishedProposalToProposalApp();

        assertThat(binding).isNotNull();
        assertThat(binding.getDestination()).isEqualTo("queue.finished.proposal.app");
        assertThat(binding.getExchange()).isEqualTo("exchange.finished.proposal");
    }

    @Test
    void shouldBindNotificationQueueToExchange() {
        Binding binding = config.bindingFinishedProposalToNotificationApp();

        assertThat(binding).isNotNull();
        assertThat(binding.getDestination()).isEqualTo("queue.finished.notification.app");
        assertThat(binding.getExchange()).isEqualTo("exchange.finished.proposal");
    }
}