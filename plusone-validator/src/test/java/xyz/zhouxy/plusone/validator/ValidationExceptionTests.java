package xyz.zhouxy.plusone.validator;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ValidationExceptionTests {

    @Test
    void withoutMessage() {
        ValidationException ex = ValidationException.withDefaultMessage();
        assertNotNull(ex);
        assertEquals(ValidationException.DEFAULT_MESSAGE, ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void withMessage_String() {
        String message = "Validation failed";
        ValidationException ex = ValidationException.withMessage(message);
        assertNotNull(ex);
        assertEquals(message, ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void withMessage_TemplateAndArgs() {
        String template = "Field %s is invalid: %s";
        Object[] args = {"username", "too short"};
        ValidationException ex = ValidationException.withMessage(template, args);
        assertNotNull(ex);
        assertEquals("Field username is invalid: too short", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void withCause() {
        Throwable cause = new IOException("IO error");
        ValidationException ex = ValidationException.withCause(cause);
        assertNotNull(ex);
        assertEquals(cause, ex.getCause());
        assertEquals(cause.getClass().getName() + ": " + cause.getMessage(), ex.getMessage());
    }

    @Test
    void withMessageAndCause() {
        String message = "Validation failed";
        Throwable cause = new IllegalArgumentException("Invalid argument");
        ValidationException ex = ValidationException.withMessageAndCause(message, cause);
        assertNotNull(ex);
        assertEquals(message, ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}
