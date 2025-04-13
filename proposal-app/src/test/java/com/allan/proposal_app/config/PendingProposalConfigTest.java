package com.allan.proposal_app.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PendingProposalConfigTest {

    private RabbitMQProperties mockProperties;
    private RabbitMQProperties.PendingProposal pendingProposal;

    private PendingProposalConfig config;

    @BeforeEach
    void setUp() {
        // Create mock instances
        mockProperties = mock(RabbitMQProperties.class);
        pendingProposal = mock(RabbitMQProperties.PendingProposal.class);

        // Setup mock return values
        when(mockProperties.getPendingproposal()).thenReturn(pendingProposal);
        when(pendingProposal.getQueueToMsCreditAnalysis()).thenReturn("queue.pending.credit.analysis");
        when(pendingProposal.getQueueToMsNotification()).thenReturn("queue.pending.notification");
        when(pendingProposal.getExchange()).thenReturn("exchange.pending.proposal");

        // Initialize config with mocked properties
        config = new PendingProposalConfig(mockProperties);
    }

    @Test
    void shouldCreatePendingProposalCreditAnalysisQueue() {
        Queue queue = config.pendingProposalCreditAnalysisQueue();

        assertThat(queue).isNotNull();
        assertThat(queue.getName()).isEqualTo("queue.pending.credit.analysis");
        assertThat(queue.isDurable()).isTrue();
    }

    @Test
    void shouldCreatePendingProposalNotificationQueue() {
        Queue queue = config.pendingProposalNotificationQueue();

        assertThat(queue).isNotNull();
        assertThat(queue.getName()).isEqualTo("queue.pending.notification");
        assertThat(queue.isDurable()).isTrue();
    }

    @Test
    void shouldCreatePendingProposalExchange() {
        FanoutExchange exchange = config.pendingProposalExchange();

        assertThat(exchange).isNotNull();
        assertThat(exchange.getName()).isEqualTo("exchange.pending.proposal");
    }

    @Test
    void shouldBindCreditAnalysisQueueToExchange() {
        Binding binding = config.bindingPendingProposalCreditAnalysis();

        assertThat(binding).isNotNull();
        assertThat(binding.getDestination()).isEqualTo("queue.pending.credit.analysis");
        assertThat(binding.getExchange()).isEqualTo("exchange.pending.proposal");
    }

    @Test
    void shouldBindNotificationQueueToExchange() {
        Binding binding = config.bindingPendingProposalNotification();

        assertThat(binding).isNotNull();
        assertThat(binding.getDestination()).isEqualTo("queue.pending.notification");
        assertThat(binding.getExchange()).isEqualTo("exchange.pending.proposal");
    }
}