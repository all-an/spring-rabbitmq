package com.allan.proposal_app.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RabbitConnectionConfigTest {

    private RabbitConnectionConfig config;
    private ConnectionFactory mockConnectionFactory;

    @BeforeEach
    void setUp() {
        config = new RabbitConnectionConfig();
        mockConnectionFactory = mock(ConnectionFactory.class);
    }

    @Test
    void shouldCreateRabbitAdmin() {
        RabbitAdmin rabbitAdmin = config.rabbitAdmin(mockConnectionFactory);

        assertThat(rabbitAdmin).isNotNull();
        assertThat(rabbitAdmin.getRabbitTemplate().getConnectionFactory()).isEqualTo(mockConnectionFactory);
    }

    @Test
    void shouldCreateJackson2JsonMessageConverter() {
        Jackson2JsonMessageConverter converter = config.jackson2JsonMessageConverter();

        assertThat(converter).isNotNull();
    }

    @Test
    void shouldCreateRabbitTemplateWithConverter() {
        Jackson2JsonMessageConverter converter = config.jackson2JsonMessageConverter();
        RabbitTemplate template = config.rabbitTemplate(mockConnectionFactory, converter);

        assertThat(template).isNotNull();
        assertThat(template.getConnectionFactory()).isEqualTo(mockConnectionFactory);
        assertThat(template.getMessageConverter()).isEqualTo(converter);
    }

    @Test
    void shouldInitializeRabbitAdminOnApplicationReadyEvent() {
        RabbitAdmin mockRabbitAdmin = mock(RabbitAdmin.class);
        ApplicationListener<ApplicationReadyEvent> listener = config.initializeAdmin(mockRabbitAdmin);

        ApplicationReadyEvent mockEvent = mock(ApplicationReadyEvent.class);
        listener.onApplicationEvent(mockEvent);

        verify(mockRabbitAdmin, times(1)).initialize();
    }
}