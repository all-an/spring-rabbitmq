package com.allan.proposal_app.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.NumberFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class NumberFormatHelperTest {

    @Spy
    private NumberFormatHelper numberFormatHelper;

    @Captor
    private ArgumentCaptor<Double> doubleCaptor;

    @Test
    void shouldFormatNumberCorrectlyAndVerifyCall() {
        // Arrange
        double testValue = 1234.56;

        // Act
        String formattedValue = numberFormatHelper.format(testValue);

        // Assert
        verify(numberFormatHelper).format(doubleCaptor.capture()); // Capture argument
        assertEquals(testValue, doubleCaptor.getValue()); // Verify argument captured
        assertEquals(NumberFormat.getNumberInstance().format(testValue), formattedValue); // Ensure correct formatting
    }
}