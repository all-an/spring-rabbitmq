package com.allan.credit_analysis_app.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NullVerifierTest {

    static class Dummy {
        String field1;
        Integer field2;
        Dummy nested;
    }

    @Test
    void testVerifyFieldsNotNull_success() {
        Dummy dummy = new Dummy();
        dummy.field1 = "value";
        dummy.field2 = 123;
        assertDoesNotThrow(() -> NullVerifier.verifyFieldsNotNull(dummy, "field1", "field2"));
    }

    @Test
    void testVerifyFieldsNotNull_missingField() {
        Dummy dummy = new Dummy();
        dummy.field1 = null;
        dummy.field2 = 123;
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                NullVerifier.verifyFieldsNotNull(dummy, "field1", "field2"));
        assertTrue(exception.getMessage().contains("field1"));
    }

    @Test
    void testVerifyFieldsNotNull_invalidFieldName() {
        Dummy dummy = new Dummy();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                NullVerifier.verifyFieldsNotNull(dummy, "nonexistent"));
        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    void testVerifyFieldsNotNull_nullObject() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                NullVerifier.verifyFieldsNotNull(null, "field1"));
        assertEquals("Target object is null", exception.getMessage());
    }
}
