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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Range;

import xyz.zhouxy.plusone.example.ExampleCommand;
import xyz.zhouxy.plusone.validator.BaseValidator;

public class ComparablePropertyValidatorTests {

    static final LocalDateTime MIN = LocalDate.of(2025, 5, 1).atStartOfDay();
    static final LocalDateTime MAX = LocalDate.of(2025, 5, 31).atStartOfDay();
    static final Range<ChronoLocalDateTime<?>> DATE_TIME_RANGE = Range.closedOpen(MIN, MAX);

    static final String MESSAGE = String.format("The value should in the interval [%s,%s)", MIN, MAX);

    // ================================
    // #region - in the interval
    // ================================

    @Test
    void inRange_valueIsInTheInterval() {
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForComparable(ExampleCommand::getDateTimeProperty)
                        .inRange(DATE_TIME_RANGE);
                ruleForComparable(ExampleCommand::getDateTimeProperty)
                        .inRange(DATE_TIME_RANGE, MESSAGE);
                ruleForComparable(ExampleCommand::getDateTimeProperty)
                        .inRange(DATE_TIME_RANGE, () -> ExampleException.withMessage(MESSAGE));
                ruleForComparable(ExampleCommand::getDateTimeProperty)
                        .inRange(DATE_TIME_RANGE, property -> ExampleException.withMessage(
                                "The dateTimeProperty should in the interval [%s,%s), but it is %s", MIN, MAX, property));

                ruleForInt(ExampleCommand::getIntProperty)
                        .inRange(Range.closed(18, 60));
                ruleForLong(ExampleCommand::getLongProperty)
                        .inRange(Range.closed(10000000000L, 20000000000L));
            }
        };

        ExampleCommand command = exampleCommandWithComparableProperty(18, 10000000000L, MIN);

        assertDoesNotThrow(() -> validator.validate(command));
    }

    // ================================
    // #endregion - in the interval
    // ================================

    // ================================
    // #region - not in the interval
    // ================================

    @Test
    void inRange_default_valueIsNotInTheInterval() {
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForComparable(ExampleCommand::getDateTimeProperty)
                        .inRange(DATE_TIME_RANGE);
            }
        };

        ExampleCommand command = exampleCommandWithComparableProperty(18, 10000000000L, MAX);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));

        final String expected = String.format("The value is not in the interval %s", DATE_TIME_RANGE);
        assertEquals(expected, e.getMessage());
    }

    @Test
    void inRange_message_valueIsNotInTheInterval() {
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForComparable(ExampleCommand::getDateTimeProperty)
                        .inRange(DATE_TIME_RANGE, MESSAGE);
            }
        };

        ExampleCommand command = exampleCommandWithComparableProperty(18, 10000000000L, MAX);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));

        assertEquals(MESSAGE, e.getMessage());
    }

    @Test
    void inRange_exceptionSupplier_valueIsNotInTheInterval() {
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForComparable(ExampleCommand::getDateTimeProperty)
                        .inRange(DATE_TIME_RANGE, () -> ExampleException.withMessage(MESSAGE));
            }
        };

        ExampleCommand command = exampleCommandWithComparableProperty(18, 10000000000L, MAX);

        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));

        assertEquals(MESSAGE, e.getMessage());
    }

    @Test
    void inRange_exceptionFunction_valueIsNotInTheInterval() {
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForComparable(ExampleCommand::getDateTimeProperty)
                        .inRange(DATE_TIME_RANGE, property -> ExampleException.withMessage(
                                "The dateTimeProperty should in the interval [%s,%s), but it is %s", MIN, MAX, property));
            }
        };

        ExampleCommand command = exampleCommandWithComparableProperty(18, 10000000000L, MAX);

        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));

        final String expected = String.format("The dateTimeProperty should in the interval [%s,%s), but it is %s", MIN, MAX, MAX);
        assertEquals(expected, e.getMessage());
    }

    // ================================
    // #endregion - not in the interval
    // ================================

    // ================================
    // #region - null
    // ================================

    @Test
    void inRange_default_valueIsNull() {
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForComparable(ExampleCommand::getDateTimeProperty)
                        .inRange(DATE_TIME_RANGE);
            }
        };

        ExampleCommand command = exampleCommandWithComparableProperty(null, null, null);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));

        final String expected = String.format("The value is not in the interval %s", DATE_TIME_RANGE);
        assertEquals(expected, e.getMessage());
    }

    @Test
    void inRange_message_valueIsNull() {
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForComparable(ExampleCommand::getDateTimeProperty)
                        .inRange(DATE_TIME_RANGE, MESSAGE);
            }
        };

        ExampleCommand command = exampleCommandWithComparableProperty(null, null, null);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));

        assertEquals(MESSAGE, e.getMessage());
    }

    @Test
    void inRange_exceptionSupplier_valueIsNull() {
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForComparable(ExampleCommand::getDateTimeProperty)
                        .inRange(DATE_TIME_RANGE, () -> ExampleException.withMessage(MESSAGE));
            }
        };

        ExampleCommand command = exampleCommandWithComparableProperty(null, null, null);

        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));

        assertEquals(MESSAGE, e.getMessage());
    }

    @Test
    void inRange_exceptionFunction_valueIsNull() {
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForComparable(ExampleCommand::getDateTimeProperty)
                        .inRange(DATE_TIME_RANGE, property -> ExampleException.withMessage(
                                "The dateTimeProperty should in the interval [%s,%s), but it is %s", MIN, MAX, property));
            }
        };

        ExampleCommand command = exampleCommandWithComparableProperty(null, null, null);

        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));

        final String expected = String.format("The dateTimeProperty should in the interval [%s,%s), but it is null", MIN, MAX);
        assertEquals(expected, e.getMessage());
    }

    // ================================
    // #endregion - null
    // ================================

    static ExampleCommand exampleCommandWithComparableProperty(
            Integer intProperty,
            Long longProperty,
            LocalDateTime dateTimeProperty) {
        ExampleCommand exampleCommand = new ExampleCommand();
        exampleCommand.setIntProperty(intProperty);
        exampleCommand.setLongProperty(longProperty);
        exampleCommand.setDateTimeProperty(dateTimeProperty);
        return exampleCommand;
    }
}
