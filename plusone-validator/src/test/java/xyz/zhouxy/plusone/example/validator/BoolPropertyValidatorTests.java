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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import xyz.zhouxy.plusone.ExampleException;
import xyz.zhouxy.plusone.example.ExampleCommand;
import xyz.zhouxy.plusone.validator.BaseValidator;
import xyz.zhouxy.plusone.validator.IValidator;

public class BoolPropertyValidatorTests {

    // ================================
    // #region - isTrueValue
    // ================================

    @Test
    void isTrueValue_trueProperty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty).isTrueValue();
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isTrueValue("The boolProperty should be true.");
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isTrueValue(() -> ExampleException.withMessage("The boolProperty should be true."));
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isTrueValue(property -> ExampleException.withMessage(
                                "The boolProperty should be true, but it is `%s`", property));
            }
        };

        ExampleCommand command = exampleCommandWithBoolProperty(true);

        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void isTrueValue_default_falseProperty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty).isTrueValue();
            }
        };

        ExampleCommand command = exampleCommandWithBoolProperty(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));

        assertEquals("The input must be true.", exception.getMessage());
    }

    @Test
    void isTrueValue_message_falseProperty() {
        final String message = "The boolProperty should be true.";
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isTrueValue(message);
            }
        };

        ExampleCommand command = exampleCommandWithBoolProperty(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));

        assertEquals(message, exception.getMessage());
    }

    @Test
    void isTrueValue_exceptionSupplier_falseProperty() {
        final String message = "The boolProperty should be true.";
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isTrueValue(() -> ExampleException.withMessage(message));
            }
        };

        ExampleCommand command = exampleCommandWithBoolProperty(false);

        ExampleException exception = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));

        assertEquals(message, exception.getMessage());
    }

    @Test
    void isTrueValue_exceptionFunction_falseProperty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isTrueValue(property -> ExampleException.withMessage(
                                "The boolProperty should be true, but it is `%s`", property));
            }
        };

        ExampleCommand command = exampleCommandWithBoolProperty(false);

        ExampleException exception = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));

        assertEquals("The boolProperty should be true, but it is `false`", exception.getMessage());
    }

    @Test
    void isTrueValue_default_nullProperty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty).isTrueValue();
            }
        };

        ExampleCommand command = exampleCommandWithBoolProperty(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));

        assertEquals("The input must be true.", exception.getMessage());
    }

    @Test
    void isTrueValue_message_nullProperty() {
        final String message = "The boolProperty should be true.";
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isTrueValue(message);
            }
        };

        ExampleCommand command = exampleCommandWithBoolProperty(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));

        assertEquals(message, exception.getMessage());
    }

    @Test
    void isTrueValue_exceptionSupplier_nullProperty() {
        final String message = "The boolProperty should be true.";
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isTrueValue(() -> ExampleException.withMessage(message));
            }
        };

        ExampleCommand command = exampleCommandWithBoolProperty(null);

        ExampleException exception = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));

        assertEquals(message, exception.getMessage());
    }

    @Test
    void isTrueValue_exceptionFunction_nullProperty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isTrueValue(property -> ExampleException.withMessage(
                                "The boolProperty should be true, but it is `%s`", property));
            }
        };

        ExampleCommand command = exampleCommandWithBoolProperty(null);

        ExampleException exception = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));

        assertEquals("The boolProperty should be true, but it is `null`", exception.getMessage());
    }

    // ================================
    // #endregion - isTrueValue
    // ================================

    // ================================
    // #region - isFalseValue
    // ================================

    @Test
    void isFalseValue_falseProperty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty).isFalseValue();
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isFalseValue("The boolProperty should be false.");
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isFalseValue(() -> ExampleException.withMessage("The boolProperty should be false."));
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isFalseValue(property -> ExampleException.withMessage(
                                "The boolProperty should be false, but it is `%s`", property));
            }
        };

        ExampleCommand command = exampleCommandWithBoolProperty(false);

        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void isFalseValue_default_trueProperty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty).isFalseValue();
            }
        };

        ExampleCommand command = exampleCommandWithBoolProperty(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));

        assertEquals("The input must be false.", exception.getMessage());
    }

    @Test
    void isFalseValue_message_trueProperty() {
        final String message = "The boolProperty should be false.";
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isFalseValue(message);
            }
        };

        ExampleCommand command = exampleCommandWithBoolProperty(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));

        assertEquals(message, exception.getMessage());
    }

    @Test
    void isFalseValue_exceptionSupplier_trueProperty() {
        final String message = "The boolProperty should be false.";
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isFalseValue(() -> ExampleException.withMessage(message));
            }
        };

        ExampleCommand command = exampleCommandWithBoolProperty(true);

        ExampleException exception = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));

        assertEquals(message, exception.getMessage());
    }

    @Test
    void isFalseValue_exceptionFunction_trueProperty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isFalseValue(property -> ExampleException.withMessage(
                                "The boolProperty should be false, but it is `%s`", property));
            }
        };

        ExampleCommand command = exampleCommandWithBoolProperty(true);

        ExampleException exception = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));

        assertEquals("The boolProperty should be false, but it is `true`", exception.getMessage());
    }

    @Test
    void isFalseValue_default_nullProperty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty).isFalseValue();
            }
        };

        ExampleCommand command = exampleCommandWithBoolProperty(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));

        assertEquals("The input must be false.", exception.getMessage());
    }

    @Test
    void isFalseValue_message_nullProperty() {
        final String message = "The boolProperty should be false.";
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isFalseValue(message);
            }
        };

        ExampleCommand command = exampleCommandWithBoolProperty(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));

        assertEquals(message, exception.getMessage());
    }

    @Test
    void isFalseValue_exceptionSupplier_nullProperty() {
        final String message = "The boolProperty should be false.";
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isFalseValue(() -> ExampleException.withMessage(message));
            }
        };

        ExampleCommand command = exampleCommandWithBoolProperty(null);

        ExampleException exception = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));

        assertEquals(message, exception.getMessage());
    }

    @Test
    void isFalseValue_exceptionFunction_nullProperty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isFalseValue(property -> ExampleException.withMessage(
                                "The boolProperty should be false, but it is `%s`", property));
            }
        };

        ExampleCommand command = exampleCommandWithBoolProperty(null);

        ExampleException exception = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));

        assertEquals("The boolProperty should be false, but it is `null`", exception.getMessage());
    }

    // ================================
    // #endregion - isFalseValue
    // ================================

    static ExampleCommand exampleCommandWithBoolProperty(Boolean boolProperty) {
        ExampleCommand exampleCommand = new ExampleCommand();
        exampleCommand.setBoolProperty(boolProperty);
        return exampleCommand;
    }
}
