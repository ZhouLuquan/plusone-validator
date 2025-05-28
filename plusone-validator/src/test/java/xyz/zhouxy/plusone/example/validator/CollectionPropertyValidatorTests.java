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

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

import xyz.zhouxy.plusone.ExampleException;
import xyz.zhouxy.plusone.example.ExampleCommand;
import xyz.zhouxy.plusone.validator.BaseValidator;
import xyz.zhouxy.plusone.validator.IValidator;

public class CollectionPropertyValidatorTests {

    static final String MESSAGE_NOT_EMPTY = "The stringListProperty should not be empty.";
    static final String MESSAGE_EMPTY = "The stringListProperty should be empty.";

    // ================================
    // #region - notEmpty
    // ================================

    @Test
    void notEmpty_stringListIsNotEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty).notEmpty();
                ruleForCollection(ExampleCommand::getStringListProperty).notEmpty(MESSAGE_NOT_EMPTY);
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .notEmpty(() -> ExampleException.withMessage(MESSAGE_NOT_EMPTY));
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .notEmpty(strList -> ExampleException.withMessage(
                                "The stringListProperty should not be empty, but it is %s.", strList));
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(Lists.newArrayList("A", "B", "C"));
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void notEmpty_default_stringListIsEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty).notEmpty();
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(Collections.emptyList());

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals("The input must not be empty.", e.getMessage());
    }

    @Test
    void notEmpty_message_stringListIsEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty).notEmpty(MESSAGE_NOT_EMPTY);
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(Collections.emptyList());

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_NOT_EMPTY, e.getMessage());
    }

    @Test
    void notEmpty_exceptionSupplier_stringListIsEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .notEmpty(() -> ExampleException.withMessage(MESSAGE_NOT_EMPTY));
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(Collections.emptyList());

        ExampleException e = assertThrows(ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_NOT_EMPTY, e.getMessage());
    }

    @Test
    void notEmpty_exceptionFunction_stringListIsEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .notEmpty(strList -> ExampleException.withMessage(
                                "The stringListProperty should not be empty, but it is %s.", strList));
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(Collections.emptyList());

        ExampleException e = assertThrows(ExampleException.class, () -> validator.validate(command));
        assertEquals("The stringListProperty should not be empty, but it is [].", e.getMessage());
    }

    @Test
    void notEmpty_message_stringListIsNull() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty).notEmpty(MESSAGE_NOT_EMPTY);
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_NOT_EMPTY, e.getMessage());
    }

    @Test
    void notEmpty_exceptionSupplier_stringListIsNull() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .notEmpty(() -> ExampleException.withMessage(MESSAGE_NOT_EMPTY));
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(null);

        ExampleException e = assertThrows(ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_NOT_EMPTY, e.getMessage());
    }

    @Test
    void notEmpty_exceptionFunction_stringListIsNull() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .notEmpty(strList -> ExampleException.withMessage(
                                "The stringListProperty should not be empty, but it is %s.", strList));
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(null);

        ExampleException e = assertThrows(ExampleException.class, () -> validator.validate(command));
        assertEquals("The stringListProperty should not be empty, but it is null.", e.getMessage());
    }

    // ================================
    // #endregion - notEmpty
    // ================================

    // ================================
    // #region - isEmpty
    // ================================

    @Test
    void isEmpty_stringListIsEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty).isEmpty();
                ruleForCollection(ExampleCommand::getStringListProperty).isEmpty(MESSAGE_EMPTY);
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .isEmpty(() -> ExampleException.withMessage(MESSAGE_EMPTY));
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .isEmpty(strList -> ExampleException.withMessage(
                                "The stringListProperty should be empty, but it is %s.", strList));
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(Collections.emptyList());
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void isEmpty_stringListIsNull() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty).isEmpty();
                ruleForCollection(ExampleCommand::getStringListProperty).isEmpty(MESSAGE_EMPTY);
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .isEmpty(() -> ExampleException.withMessage(MESSAGE_EMPTY));
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .isEmpty(strList -> ExampleException.withMessage(
                                "The stringListProperty should be empty, but it is %s.", strList));
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void isEmpty_default_stringListIsNotEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty).isEmpty();
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(Lists.newArrayList("A", "B", "C"));

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals("The input must be empty.", e.getMessage());
    }

    @Test
    void isEmpty_message_stringListIsNotEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty).isEmpty(MESSAGE_EMPTY);
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(Lists.newArrayList("A", "B", "C"));

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_EMPTY, e.getMessage());
    }

    @Test
    void isEmpty_exceptionSupplier_stringListIsNotEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .isEmpty(() -> ExampleException.withMessage(MESSAGE_EMPTY));
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(Lists.newArrayList("A", "B", "C"));

        ExampleException e = assertThrows(ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_EMPTY, e.getMessage());
    }

    @Test
    void isEmpty_exceptionFunction_stringListIsNotEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .isEmpty(strList -> ExampleException.withMessage(
                                "The stringListProperty should be empty, but it is %s.", strList));
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(Lists.newArrayList("A", "B", "C"));

        ExampleException e = assertThrows(ExampleException.class, () -> validator.validate(command));
        assertEquals("The stringListProperty should be empty, but it is [A, B, C].", e.getMessage());
    }

    // ================================
    // #endregion - isEmpty
    // ================================

    static ExampleCommand exampleCommandWithStringListProperty(List<String> property) {
        ExampleCommand exampleCommand = new ExampleCommand();
        exampleCommand.setStringListProperty(property);
        return exampleCommand;
    }
}
