/*
 * Copyright 2025 the original author or authors.
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

package xyz.zhouxy.plusone.example.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Objects;
import java.util.AbstractMap.SimpleEntry;

import org.junit.jupiter.api.Test;

import xyz.zhouxy.plusone.ExampleException;
import xyz.zhouxy.plusone.example.ExampleCommand;
import xyz.zhouxy.plusone.validator.BaseValidator;
import xyz.zhouxy.plusone.validator.IValidator;
import xyz.zhouxy.plusone.validator.ValidationException;

public class PairPropertyValidatorTests {

    static final String MESSAGE = "Validation failed.";

    // ================================
    // #region - must
    // ================================

    @Test
    void must_validInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForPair((ExampleCommand command) -> new SimpleEntry<String,Integer>(command.getStringProperty(), command.getIntProperty()))
                        .must((str, intValue) -> Objects.equals(str, intValue.toString()))
                        .must((str, intValue) -> Objects.equals(str, intValue.toString()), MESSAGE)
                        .must((str, intValue) -> Objects.equals(str, intValue.toString()), () -> ExampleException.withMessage(MESSAGE))
                        .must((str, intValue) -> Objects.equals(str, intValue.toString()),
                                (str, intValue) -> ExampleException.withMessage("Validation failed: ('%s', %d).", str, intValue));
            }
        };

        ExampleCommand command = exampleCommandWithIntAndStringListProperty(100, "100");
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void must_default_invalidInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForPair((ExampleCommand command) -> new SimpleEntry<String,Integer>(command.getStringProperty(), command.getIntProperty()))
                        .must((str, intValue) -> Objects.equals(str, intValue.toString()));
            }
        };

        ExampleCommand command = exampleCommandWithIntAndStringListProperty(100, "");

        ValidationException e = assertThrows(ValidationException.class, () -> validator.validate(command));
        assertEquals("The specified condition was not met for the input.", e.getMessage());
    }

    @Test
    void must_message_invalidInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForPair((ExampleCommand command) -> new SimpleEntry<String,Integer>(command.getStringProperty(), command.getIntProperty()))
                        .must((str, intValue) -> Objects.equals(str, intValue.toString()), MESSAGE);
            }
        };

        ExampleCommand command = exampleCommandWithIntAndStringListProperty(100, "");
        ValidationException e = assertThrows(ValidationException.class, () -> validator.validate(command));
        assertEquals(MESSAGE, e.getMessage());
    }

    @Test
    void must_exceptionSupplier_invalidInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForPair((ExampleCommand command) -> new SimpleEntry<String,Integer>(command.getStringProperty(), command.getIntProperty()))
                        .must((str, intValue) -> Objects.equals(str, intValue.toString()), () -> ExampleException.withMessage(MESSAGE));
            }
        };

        ExampleCommand command = exampleCommandWithIntAndStringListProperty(100, "");

        ExampleException e = assertThrows(ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE, e.getMessage());
    }

    @Test
    void must_exceptionFunction_invalidInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForPair((ExampleCommand command) -> new SimpleEntry<String,Integer>(command.getStringProperty(), command.getIntProperty()))
                        .must((str, intValue) -> Objects.equals(str, intValue.toString()),
                                (str, intValue) -> ExampleException.withMessage("Validation failed: ('%s', %d).", str, intValue));
            }
        };

        ExampleCommand command = exampleCommandWithIntAndStringListProperty(100, "");

        ExampleException e = assertThrows(ExampleException.class, () -> validator.validate(command));
        assertEquals("Validation failed: ('', 100).", e.getMessage());
    }

    // ================================
    // #endregion - must
    // ================================

    static ExampleCommand exampleCommandWithIntAndStringListProperty(Integer intValue, String str) {
        ExampleCommand exampleCommand = new ExampleCommand();
        exampleCommand.setIntProperty(intValue);
        exampleCommand.setStringProperty(str);
        return exampleCommand;
    }
}
