/*
 * Copyright 2023-2025 the original author or authors.
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

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import xyz.zhouxy.plusone.ExampleException;
import xyz.zhouxy.plusone.example.ExampleCommand;
import xyz.zhouxy.plusone.validator.BaseValidator;
import xyz.zhouxy.plusone.validator.IValidator;
import xyz.zhouxy.plusone.validator.ValidationException;

class BaseValidatorTest {

    @Test
    void withRule_validInput() {
        ExampleCommand exampleCommand = new ExampleCommand();
        exampleCommand.setStringProperty("Foo");

        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                withRule(command -> Objects.equals(command.getStringProperty(), "Foo"),
                        "The stringProperty must be equal to 'Foo'");
                withRule(command -> Objects.equals(command.getStringProperty(), "Foo"),
                        () -> ExampleException.withMessage("The stringProperty must be equal to 'Foo'"));
                withRule(command -> Objects.equals(command.getStringProperty(), "Foo"),
                        str -> ExampleException.withMessage("The stringProperty must be equal to 'Foo', but is was '%s'.", str));
                withRule(command -> {
                    final String stringProperty = command.getStringProperty();
                    if (!Objects.equals(stringProperty, "Foo")) {
                        throw ExampleException.withMessage("");
                    }
                });
            }
        };
        assertDoesNotThrow(() -> validator.validate(exampleCommand));
    }

    @Test
    void withRule_invalidInput() {
        ExampleCommand command = new ExampleCommand();

        IValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                withRule(command -> Objects.equals(command.getStringProperty(), "Foo"),
                        "The stringProperty must be equal to 'Foo'");
            }
        };
        ValidationException eWithSpecifiedMessage = assertThrows(
                ValidationException.class, () -> ruleWithMessage.validate(command));
        assertEquals("The stringProperty must be equal to 'Foo'", eWithSpecifiedMessage.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                withRule(command -> Objects.equals(command.getStringProperty(), "Foo"),
                        () -> ExampleException.withMessage("The stringProperty must be equal to 'Foo'"));
            }
        };
        ExampleException specifiedException = assertThrows(
                ExampleException.class,
                () -> ruleWithExceptionSupplier.validate(command));
        assertEquals("The stringProperty must be equal to 'Foo'", specifiedException.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                withRule(command -> Objects.equals(command.getStringProperty(), "Foo"), command -> ExampleException
                        .withMessage("The stringProperty must be equal to 'Foo', but is was '%s'.", command.getStringProperty()));
            }
        };
        ExampleException specifiedException2 = assertThrows(
                ExampleException.class,
                () -> ruleWithExceptionFunction.validate(command));
        assertEquals("The stringProperty must be equal to 'Foo', but is was 'null'.", specifiedException2.getMessage());

        IValidator<ExampleCommand> rule = new BaseValidator<ExampleCommand>() {
            {
                withRule(command -> {
                    final String stringProperty = command.getStringProperty();
                    if (!Objects.equals(stringProperty, "Foo")) {
                        throw ExampleException.withMessage("");
                    }
                });
            }
        };
        ExampleException e = assertThrows(ExampleException.class, () -> rule.validate(command));
        assertEquals("", e.getMessage());
    }
}
