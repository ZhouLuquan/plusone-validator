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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import xyz.zhouxy.plusone.ExampleException;
import xyz.zhouxy.plusone.example.ExampleCommand;
import xyz.zhouxy.plusone.validator.BaseValidator;
import xyz.zhouxy.plusone.validator.IValidator;

public class LongPropertyValidatorTests {

    static final long MIN = 10000000000L;
    static final long MAX = 10000000008L;


    static final String MESSAGE_GT = "The value should be greater than " + MIN;
    static final String MESSAGE_GE = "The value should be greater than or equal to " + MIN;

    static final String MESSAGE_LT = "The value should be less than " + MAX;
    static final String MESSAGE_LE = "The value should be less than or equal to " + MAX;

    // ================================
    // #region - gt_validValue
    // ================================

    @ParameterizedTest
    @ValueSource(longs = { MIN + 1, Long.MAX_VALUE })
    void gt_all_validValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .gt(MIN);
                ruleForLong(ExampleCommand::getLongProperty)
                        .gt(MIN, MESSAGE_GT);
                ruleForLong(ExampleCommand::getLongProperty)
                        .gt(MIN, () -> ExampleException.withMessage(MESSAGE_GT));
                ruleForLong(ExampleCommand::getLongProperty)
                        .gt(MIN, property -> ExampleException.withMessage(
                                "The longProperty should be greater than %d, but it is %d", MIN, property));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        assertDoesNotThrow(() -> validator.validate(command));
    }

    // ================================
    // #endregion - gt_validValue
    // ================================

    // ================================
    // #region - gt_invalidValue
    // ================================

    @ParameterizedTest
    @ValueSource(longs = { MIN, MIN - 1, Long.MIN_VALUE })
    void gt_default_invalidValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .gt(MIN);
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(String.format("The input must be greater than '%d'.", MIN), e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = { MIN, MIN - 1, Long.MIN_VALUE })
    void gt_message_invalidValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .gt(MIN, MESSAGE_GT);
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_GT, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = { MIN, MIN - 1, Long.MIN_VALUE })
    void gt_exceptionSupplier_invalidValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .gt(MIN, () -> ExampleException.withMessage(MESSAGE_GT));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_GT, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = { MIN, MIN - 1, Long.MIN_VALUE })
    void gt_exceptionFunction_invalidValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .gt(MIN, property -> ExampleException.withMessage(
                                "The longProperty should be greater than %d, but it is %d", MIN, property));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        final String expected = String.format("The longProperty should be greater than %d, but it is %d", MIN, value);
        assertEquals(expected, e.getMessage());
    }

    // ================================
    // #endregion - gt_invalidValue
    // ================================

    // ================================
    // #region - gt_null
    // ================================

    @Test
    void gt_default_null() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .gt(MIN);
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(null);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(String.format("The input must be greater than '%d'.", MIN), e.getMessage());
    }

    @Test
    void gt_message_null() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .gt(MIN, MESSAGE_GT);
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(null);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_GT, e.getMessage());
    }

    @Test
    void gt_exceptionSupplier_null() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .gt(MIN, () -> ExampleException.withMessage(MESSAGE_GT));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(null);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_GT, e.getMessage());
    }

    @Test
    void gt_exceptionFunction_null() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .gt(MIN, property -> ExampleException.withMessage(
                                "The longProperty should be greater than %d, but it is %d", MIN, property));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(null);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        final String expected = String.format("The longProperty should be greater than %d, but it is null", MIN);
        assertEquals(expected, e.getMessage());
    }

    // ================================
    // #endregion - gt_null
    // ================================

    // ================================
    // #region - ge_validValue
    // ================================

    @ParameterizedTest
    @ValueSource(longs = { MIN, MIN + 1, Long.MAX_VALUE })
    void ge_all_validValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .ge(MIN);
                ruleForLong(ExampleCommand::getLongProperty)
                        .ge(MIN, MESSAGE_GE);
                ruleForLong(ExampleCommand::getLongProperty)
                        .ge(MIN, () -> ExampleException.withMessage(MESSAGE_GE));
                ruleForLong(ExampleCommand::getLongProperty)
                        .ge(MIN, property -> ExampleException.withMessage(
                                "The longProperty should be greater than or equal to %d, but it is %d", MIN, property));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        assertDoesNotThrow(() -> validator.validate(command));
    }

    // ================================
    // #endregion - ge_validValue
    // ================================

    // ================================
    // #region - ge_invalidValue
    // ================================

    @ParameterizedTest
    @ValueSource(longs = { MIN - 1, Long.MIN_VALUE })
    void ge_default_invalidValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .ge(MIN);
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(String.format("The input must be greater than or equal to '%d'.", MIN), e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = { MIN - 1, Long.MIN_VALUE })
    void ge_message_invalidValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .ge(MIN, MESSAGE_GE);
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_GE, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = { MIN - 1, Long.MIN_VALUE })
    void ge_exceptionSupplier_invalidValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .ge(MIN, () -> ExampleException.withMessage(MESSAGE_GE));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_GE, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = { MIN - 1, Long.MIN_VALUE })
    void ge_exceptionFunction_invalidValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .ge(MIN, property -> ExampleException.withMessage(
                                "The longProperty should be greater than or equal to %d, but it is %d", MIN, property));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        final String expected = String.format("The longProperty should be greater than or equal to %d, but it is %d", MIN, value);
        assertEquals(expected, e.getMessage());
    }

    // ================================
    // #endregion - ge_invalidValue
    // ================================

    // ================================
    // #region - ge_null
    // ================================

    @Test
    void ge_default_null() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .ge(MIN);
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(null);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(String.format("The input must be greater than or equal to '%d'.", MIN), e.getMessage());
    }

    @Test
    void ge_message_null() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .ge(MIN, MESSAGE_GE);
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(null);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_GE, e.getMessage());
    }

    @Test
    void ge_exceptionSupplier_null() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .ge(MIN, () -> ExampleException.withMessage(MESSAGE_GE));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(null);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_GE, e.getMessage());
    }

    @Test
    void ge_exceptionFunction_null() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .ge(MIN, property -> ExampleException.withMessage(
                                "The longProperty should be greater than or equal to %d, but it is %d", MIN, property));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(null);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        final String expected = String.format("The longProperty should be greater than or equal to %d, but it is null", MIN);
        assertEquals(expected, e.getMessage());
    }

    // ================================
    // #endregion - ge_null
    // ================================

    // ================================
    // #region - lt_validValue
    // ================================

    @ParameterizedTest
    @ValueSource(longs = { MAX - 1, Long.MIN_VALUE })
    void lt_all_validValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .lt(MAX);
                ruleForLong(ExampleCommand::getLongProperty)
                        .lt(MAX, MESSAGE_LT);
                ruleForLong(ExampleCommand::getLongProperty)
                        .lt(MAX, () -> ExampleException.withMessage(MESSAGE_LT));
                ruleForLong(ExampleCommand::getLongProperty)
                        .lt(MAX, property -> ExampleException.withMessage(
                                "The longProperty should be less than %d, but it is %d", MAX, property));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        assertDoesNotThrow(() -> validator.validate(command));
    }

    // ================================
    // #endregion - lt_validValue
    // ================================

    // ================================
    // #region - lt_invalidValue
    // ================================

    @ParameterizedTest
    @ValueSource(longs = { MAX, MAX + 1, Long.MAX_VALUE })
    void lt_default_invalidValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .lt(MAX);
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(String.format("The input must be less than '%d'.", MAX), e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = { MAX, MAX + 1, Long.MAX_VALUE })
    void lt_message_invalidValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .lt(MAX, MESSAGE_LT);
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_LT, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = { MAX, MAX + 1, Long.MAX_VALUE })
    void lt_exceptionSupplier_invalidValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .lt(MAX, () -> ExampleException.withMessage(MESSAGE_LT));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_LT, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = { MAX, MAX + 1, Long.MAX_VALUE })
    void lt_exceptionFunction_invalidValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .lt(MAX, property -> ExampleException.withMessage(
                                "The longProperty should be less than %d, but it is %d", MAX, property));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        final String expected = String.format("The longProperty should be less than %d, but it is %d", MAX, value);
        assertEquals(expected, e.getMessage());
    }

    // ================================
    // #endregion - lt_invalidValue
    // ================================

    // ================================
    // #region - lt_null
    // ================================

    @Test
    void lt_default_null() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .lt(MAX);
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(null);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(String.format("The input must be less than '%d'.", MAX), e.getMessage());
    }

    @Test
    void lt_message_null() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .lt(MAX, MESSAGE_LT);
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(null);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_LT, e.getMessage());
    }

    @Test
    void lt_exceptionSupplier_null() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .lt(MAX, () -> ExampleException.withMessage(MESSAGE_LT));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(null);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_LT, e.getMessage());
    }

    @Test
    void lt_exceptionFunction_null() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .lt(MAX, property -> ExampleException.withMessage(
                                "The longProperty should be less than %d, but it is %d", MAX, property));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(null);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        final String expected = String.format("The longProperty should be less than %d, but it is null", MAX);
        assertEquals(expected, e.getMessage());
    }

    // ================================
    // #endregion - lt_null
    // ================================

    // ================================
    // #region - le_validValue
    // ================================

    @ParameterizedTest
    @ValueSource(longs = { MAX, MAX - 1, Long.MIN_VALUE })
    void le_all_validValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .le(MAX);
                ruleForLong(ExampleCommand::getLongProperty)
                        .le(MAX, MESSAGE_LE);
                ruleForLong(ExampleCommand::getLongProperty)
                        .le(MAX, () -> ExampleException.withMessage(MESSAGE_LE));
                ruleForLong(ExampleCommand::getLongProperty)
                        .le(MAX, property -> ExampleException.withMessage(
                                "The longProperty should be less than or equal to %d, but it is %d", MAX, property));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        assertDoesNotThrow(() -> validator.validate(command));
    }

    // ================================
    // #endregion - le_validValue
    // ================================

    // ================================
    // #region - le_invalidValue
    // ================================

    @ParameterizedTest
    @ValueSource(longs = { MAX + 1, Long.MAX_VALUE })
    void le_default_invalidValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .le(MAX);
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(String.format("The input must be less than or equal to '%d'.", MAX), e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = { MAX + 1, Long.MAX_VALUE })
    void le_message_invalidValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .le(MAX, MESSAGE_LE);
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_LE, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = { MAX + 1, Long.MAX_VALUE })
    void le_exceptionSupplier_invalidValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .le(MAX, () -> ExampleException.withMessage(MESSAGE_LE));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_LE, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = { MAX + 1, Long.MAX_VALUE })
    void le_exceptionFunction_invalidValue(long value) {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .le(MAX, property -> ExampleException.withMessage(
                                "The longProperty should be less than or equal to %d, but it is %d", MAX, property));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(value);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        final String expected = String.format("The longProperty should be less than or equal to %d, but it is %d", MAX, value);
        assertEquals(expected, e.getMessage());
    }

    // ================================
    // #endregion - le_invalidValue
    // ================================

    // ================================
    // #region - le_null
    // ================================

    @Test
    void le_default_null() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .le(MAX);
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(null);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(String.format("The input must be less than or equal to '%d'.", MAX), e.getMessage());
    }

    @Test
    void le_message_null() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .le(MAX, MESSAGE_LE);
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(null);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_LE, e.getMessage());
    }

    @Test
    void le_exceptionSupplier_null() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .le(MAX, () -> ExampleException.withMessage(MESSAGE_LE));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(null);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_LE, e.getMessage());
    }

    @Test
    void le_exceptionFunction_null() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForLong(ExampleCommand::getLongProperty)
                        .le(MAX, property -> ExampleException.withMessage(
                                "The longProperty should be less than or equal to %d, but it is %d", MAX, property));
            }
        };

        ExampleCommand command = exampleCommandWithLongProperty(null);

        ExampleException e = assertThrows(
                ExampleException.class, () -> validator.validate(command));
        final String expected = String.format("The longProperty should be less than or equal to %d, but it is null", MAX);
        assertEquals(expected, e.getMessage());
    }

    // ================================
    // #endregion - le_null
    // ================================

    static ExampleCommand exampleCommandWithLongProperty(Long longProperty) {
        ExampleCommand exampleCommand = new ExampleCommand();
        exampleCommand.setLongProperty(longProperty);
        return exampleCommand;
    }
}
