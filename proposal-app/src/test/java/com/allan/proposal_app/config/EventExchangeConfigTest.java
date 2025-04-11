package com.allan.proposal_app.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Import(EventExchangeConfig.class)
public class EventExchangeConfigTest {

    @Autowired
    private DirectExchange eventExchange;

    @Autowired
    private Queue eventQueue;

    @Autowired
    private Binding eventBinding;

    @Test
    void shouldCreateDirectExchange() {
        assertThat(eventExchange).isNotNull();
        assertThat(eventExchange.getName()).isEqualTo(EventExchangeConfig.EXCHANGE_NAME);
        assertThat(eventExchange.isDurable()).isTrue();
    }

    @Test
    void shouldCreateQueue() {
        assertThat(eventQueue).isNotNull();
        assertThat(eventQueue.getName()).isEqualTo(EventExchangeConfig.QUEUE_NAME);
        assertThat(eventQueue.isDurable()).isTrue();
    }

    @Test
    void shouldCreateBinding() {
        assertThat(eventBinding).isNotNull();
        assertThat(eventBinding.getExchange()).isEqualTo(EventExchangeConfig.EXCHANGE_NAME);
        assertThat(eventBinding.getRoutingKey()).isEqualTo(EventExchangeConfig.ROUTING_KEY);
        assertThat(eventBinding.getDestination()).isEqualTo(EventExchangeConfig.QUEUE_NAME);
    }
}