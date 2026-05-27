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

package xyz.zhouxy.plusone.example.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import xyz.zhouxy.plusone.ExampleException;
import xyz.zhouxy.plusone.example.ExampleCommand;
import xyz.zhouxy.plusone.validator.BaseValidator;
import xyz.zhouxy.plusone.validator.IValidator;
import xyz.zhouxy.plusone.validator.ValidationException;

public class DoublePropertyValidatorTests {

    static final double MIN = 1.0;
    static final double MAX = 5.0;

    static final String MESSAGE_GT = "The input must be greater than " + MIN;
    static final String MESSAGE_GE = "The input must be greater than or equal to " + MIN;

    static final String MESSAGE_LT = "The input must be less than " + MAX;
    static final String MESSAGE_LE = "The input must be less than or equal to " + MAX;

    // ================================
    // #region - gt_validValue
    // ================================

    @ParameterizedTest
    @ValueSource(doubles = { MIN + 0.000000000000001, Double.MAX_VALUE })
    void gt_all_validValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .gt(MIN);
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .gt(MIN, MESSAGE_GT);
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .gt(MIN, () -> ExampleException.withMessage(MESSAGE_GT));
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .gt(MIN, property -> ExampleException.withMessage(
                                "The doubleProperty should be greater than %s, but it is %s", MIN, property));
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);
        assertDoesNotThrow(() -> validator.validate(command));

        assertDoesNotThrow(() -> validator.validate(new ExampleCommand()));
    }

    // ================================
    // #endregion - gt_validValue
    // ================================

    // ================================
    // #region - gt_invalidValue
    // ================================

    @ParameterizedTest
    @ValueSource(doubles = { MIN, MIN - 0.000000000000001, Double.MIN_VALUE })
    void gt_default_invalidValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .gt(MIN);
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);

        ValidationException e = assertThrows(
                ValidationException.class, () -> validator.validate(command));
        assertEquals(String.format("The input must be greater than '%f'.", MIN), e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = { MIN, MIN - 0.000000000000001, Double.MIN_VALUE })
    void gt_message_invalidValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .gt(MIN, MESSAGE_GT);
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);

        ValidationException e = assertThrows(
                ValidationException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_GT, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = { MIN, MIN - 0.000000000000001, Double.MIN_VALUE })
    void gt_exceptionSupplier_invalidValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .gt(MIN, () -> ExampleException.withMessage(MESSAGE_GT));
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_GT, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = { MIN, MIN - 0.000000000000001, Double.MIN_VALUE })
    void gt_exceptionFunction_invalidValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .gt(MIN, property -> ExampleException.withMessage(
                                "The doubleProperty should be greater than %s, but it is %s", MIN, property));
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        final String expected = String.format("The doubleProperty should be greater than %s, but it is %s", MIN, value);
        assertEquals(expected, e.getMessage());
    }

    // ================================
    // #endregion - gt_invalidValue
    // ================================

    // ================================
    // #region - ge_validValue
    // ================================

    @ParameterizedTest
    @ValueSource(doubles = { MIN, MIN + 0.000000000000001, Double.MAX_VALUE })
    void ge_all_validValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .ge(MIN);
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .ge(MIN, MESSAGE_GE);
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .ge(MIN, () -> ExampleException.withMessage(MESSAGE_GE));
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .ge(MIN, property -> ExampleException.withMessage(
                                "The doubleProperty should be greater than or equal to %s, but it is %s", MIN, property));
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);
        assertDoesNotThrow(() -> validator.validate(command));

        assertDoesNotThrow(() -> validator.validate(new ExampleCommand()));
    }

    // ================================
    // #endregion - ge_validValue
    // ================================

    // ================================
    // #region - ge_invalidValue
    // ================================

    @ParameterizedTest
    @ValueSource(doubles = { MIN - 0.000000000000001, Double.MIN_VALUE })
    void ge_default_invalidValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .ge(MIN);
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);

        ValidationException e = assertThrows(
                ValidationException.class, () -> validator.validate(command));
        assertEquals(String.format("The input must be greater than or equal to '%f'.", MIN), e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = { MIN - 0.000000000000001, Double.MIN_VALUE })
    void ge_message_invalidValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .ge(MIN, MESSAGE_GE);
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);

        ValidationException e = assertThrows(
                ValidationException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_GE, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = { MIN - 0.000000000000001, Double.MIN_VALUE })
    void ge_exceptionSupplier_invalidValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .ge(MIN, () -> ExampleException.withMessage(MESSAGE_GE));
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_GE, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = { MIN - 0.000000000000001, Double.MIN_VALUE })
    void ge_exceptionFunction_invalidValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .ge(MIN, property -> ExampleException.withMessage(
                                "The doubleProperty should be greater than or equal to %s, but it is %s", MIN, property));
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        final String expected = String.format("The doubleProperty should be greater than or equal to %s, but it is %s", MIN, value);
        assertEquals(expected, e.getMessage());
    }

    // ================================
    // #endregion - ge_invalidValue
    // ================================

    // ================================
    // #region - lt_validValue
    // ================================

    @ParameterizedTest
    @ValueSource(doubles = { MAX - 0.000000000000001, Double.MIN_VALUE })
    void lt_all_validValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .lt(MAX);
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .lt(MAX, MESSAGE_LT);
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .lt(MAX, () -> ExampleException.withMessage(MESSAGE_LT));
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .lt(MAX, property -> ExampleException.withMessage(
                                "The doubleProperty should be less than %s, but it is %s", MAX, property));
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);
        assertDoesNotThrow(() -> validator.validate(command));

        assertDoesNotThrow(() -> validator.validate(new ExampleCommand()));
    }

    // ================================
    // #endregion - lt_validValue
    // ================================

    // ================================
    // #region - lt_invalidValue
    // ================================

    @ParameterizedTest
    @ValueSource(doubles = { MAX, MAX + 0.000000000000001, Double.MAX_VALUE })
    void lt_default_invalidValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .lt(MAX);
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);

        ValidationException e = assertThrows(
                ValidationException.class, () -> validator.validate(command));
        assertEquals(String.format("The input must be less than '%f'.", MAX), e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = { MAX, MAX + 0.000000000000001, Double.MAX_VALUE })
    void lt_message_invalidValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .lt(MAX, MESSAGE_LT);
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);

        ValidationException e = assertThrows(
                ValidationException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_LT, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = { MAX, MAX + 0.000000000000001, Double.MAX_VALUE })
    void lt_exceptionSupplier_invalidValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .lt(MAX, () -> ExampleException.withMessage(MESSAGE_LT));
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_LT, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = { MAX, MAX + 0.000000000000001, Double.MAX_VALUE })
    void lt_exceptionFunction_invalidValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .lt(MAX, property -> ExampleException.withMessage(
                                "The doubleProperty should be less than %s, but it is %s", MAX, property));
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        final String expected = String.format("The doubleProperty should be less than %s, but it is %s", MAX, value);
        assertEquals(expected, e.getMessage());
    }

    // ================================
    // #endregion - lt_invalidValue
    // ================================

    // ================================
    // #region - le_validValue
    // ================================

    @ParameterizedTest
    @ValueSource(doubles = { MAX, MAX - 0.000000000000001, Double.MIN_VALUE })
    void le_all_validValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .le(MAX);
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .le(MAX, MESSAGE_LE);
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .le(MAX, () -> ExampleException.withMessage(MESSAGE_LE));
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .le(MAX, property -> ExampleException.withMessage(
                                "The doubleProperty should be less than or equal to %s, but it is %s", MAX, property));
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);
        assertDoesNotThrow(() -> validator.validate(command));

        assertDoesNotThrow(() -> validator.validate(new ExampleCommand()));
    }

    // ================================
    // #endregion - le_validValue
    // ================================

    // ================================
    // #region - le_invalidValue
    // ================================

    @ParameterizedTest
    @ValueSource(doubles = { MAX + 0.000000000000001, Double.MAX_VALUE })
    void le_default_invalidValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .le(MAX);
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);

        ValidationException e = assertThrows(
                ValidationException.class, () -> validator.validate(command));
        assertEquals(String.format("The input must be less than or equal to '%f'.", MAX), e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = { MAX + 0.000000000000001, Double.MAX_VALUE })
    void le_message_invalidValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .le(MAX, MESSAGE_LE);
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);

        ValidationException e = assertThrows(
                ValidationException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_LE, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = { MAX + 0.000000000000001, Double.MAX_VALUE })
    void le_exceptionSupplier_invalidValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .le(MAX, () -> ExampleException.withMessage(MESSAGE_LE));
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_LE, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = { MAX + 0.000000000000001, Double.MAX_VALUE })
    void le_exceptionFunction_invalidValue(double value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .le(MAX, property -> ExampleException.withMessage(
                                "The doubleProperty should be less than or equal to %s, but it is %s", MAX, property));
            }
        };

        ExampleCommand command = exampleCommandWithDoubleProperty(value);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        final String expected = String.format("The doubleProperty should be less than or equal to %s, but it is %s", MAX, value);
        assertEquals(expected, e.getMessage());
    }

    // ================================
    // #endregion - le_invalidValue
    // ================================

    static ExampleCommand exampleCommandWithDoubleProperty(Double doubleProperty) {
        ExampleCommand exampleCommand = new ExampleCommand();
        exampleCommand.setDoubleProperty(doubleProperty);
        return exampleCommand;
    }
}
