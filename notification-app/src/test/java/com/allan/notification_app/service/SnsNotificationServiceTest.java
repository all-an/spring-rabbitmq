package com.allan.notification_app.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SnsNotificationServiceTest {

    @Mock
    private AmazonSNS amazonSNS;

    @InjectMocks
    private SnsNotificationService snsNotificationService;

    @Captor
    private ArgumentCaptor<PublishRequest> publishRequestCaptor;

    private static final String VALID_PHONE = "+12345678900";
    private static final String VALID_MESSAGE = "Test message";

    @Test
    void testNotify_ValidInputs_ShouldPublishMessage() {
        // Arrange
        when(amazonSNS.publish(any(PublishRequest.class))).thenReturn(new PublishResult());

        // Act
        snsNotificationService.notify(VALID_PHONE, VALID_MESSAGE);

        // Assert
        verify(amazonSNS).publish(publishRequestCaptor.capture());
        PublishRequest capturedRequest = publishRequestCaptor.getValue();

        assertEquals(VALID_PHONE, capturedRequest.getPhoneNumber());
        assertEquals(VALID_MESSAGE, capturedRequest.getMessage());
    }

    @Test
    void testNotify_NullPhone_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> snsNotificationService.notify(null, VALID_MESSAGE)
        );

        assertEquals("Phone number cannot be null or empty", exception.getMessage());
        verify(amazonSNS, never()).publish(any(PublishRequest.class));
    }

    @Test
    void testNotify_EmptyPhone_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> snsNotificationService.notify("  ", VALID_MESSAGE)
        );

        assertEquals("Phone number cannot be null or empty", exception.getMessage());
        verify(amazonSNS, never()).publish(any(PublishRequest.class));
    }

    @Test
    void testNotify_NullMessage_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> snsNotificationService.notify(VALID_PHONE, null)
        );

        assertEquals("Message content cannot be null or empty", exception.getMessage());
        verify(amazonSNS, never()).publish(any(PublishRequest.class));
    }

    @Test
    void testNotify_EmptyMessage_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> snsNotificationService.notify(VALID_PHONE, "")
        );

        assertEquals("Message content cannot be null or empty", exception.getMessage());
        verify(amazonSNS, never()).publish(any(PublishRequest.class));
    }
}