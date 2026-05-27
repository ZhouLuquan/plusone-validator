/*
 * Copyright 2025-present ZhouXY
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
