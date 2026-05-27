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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import xyz.zhouxy.plusone.ExampleException;
import xyz.zhouxy.plusone.example.ExampleCommand;
import xyz.zhouxy.plusone.validator.BaseValidator;
import xyz.zhouxy.plusone.validator.IValidator;
import xyz.zhouxy.plusone.validator.ValidationException;

public class ArrayPropertyValidatorTests {

    static final String MESSAGE_NOT_EMPTY = "The stringArrayProperty should not be empty.";
    static final String MESSAGE_EMPTY = "The stringArrayProperty should be empty.";

    // ================================
    // #region - notEmpty
    // ================================

    @Test
    void notEmpty_stringListIsNotEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty).notEmpty();
                ruleForArray(ExampleCommand::getStringArrayProperty).notEmpty(MESSAGE_NOT_EMPTY);
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .notEmpty(() -> ExampleException.withMessage(MESSAGE_NOT_EMPTY));
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .notEmpty(strList -> ExampleException.withMessage(
                                "The stringArrayProperty should not be empty, but it is %s.", Arrays.toString(strList)));
            }
        };

        ExampleCommand command = exampleCommandWithStringArrayProperty(new String[] {"A", "B", "C"});
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void notEmpty_default_stringListIsEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty).notEmpty();
            }
        };

        ExampleCommand command = exampleCommandWithStringArrayProperty(new String[] {});

        ValidationException e = assertThrows(ValidationException.class, () -> validator.validate(command));
        assertEquals("The input must not be empty.", e.getMessage());
    }

    @Test
    void notEmpty_message_stringListIsEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty).notEmpty(MESSAGE_NOT_EMPTY);
            }
        };

        ExampleCommand command = exampleCommandWithStringArrayProperty(new String[] {});

        ValidationException e = assertThrows(ValidationException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_NOT_EMPTY, e.getMessage());
    }

    @Test
    void notEmpty_exceptionSupplier_stringListIsEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .notEmpty(() -> ExampleException.withMessage(MESSAGE_NOT_EMPTY));
            }
        };

        ExampleCommand command = exampleCommandWithStringArrayProperty(new String[] {});

        ExampleException e = assertThrows(ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_NOT_EMPTY, e.getMessage());
    }

    @Test
    void notEmpty_exceptionFunction_stringListIsEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .notEmpty(strList -> ExampleException.withMessage(
                                "The stringArrayProperty should not be empty, but it is %s.", Arrays.toString(strList)));
            }
        };

        ExampleCommand command = exampleCommandWithStringArrayProperty(new String[] {});

        ExampleException e = assertThrows(ExampleException.class, () -> validator.validate(command));
        assertEquals("The stringArrayProperty should not be empty, but it is [].", e.getMessage());
    }

    @Test
    void notEmpty_message_stringListIsNull() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty).notEmpty(MESSAGE_NOT_EMPTY);
            }
        };

        ExampleCommand command = exampleCommandWithStringArrayProperty(null);

        ValidationException e = assertThrows(ValidationException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_NOT_EMPTY, e.getMessage());
    }

    @Test
    void notEmpty_exceptionSupplier_stringListIsNull() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .notEmpty(() -> ExampleException.withMessage(MESSAGE_NOT_EMPTY));
            }
        };

        ExampleCommand command = exampleCommandWithStringArrayProperty(null);

        ExampleException e = assertThrows(ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_NOT_EMPTY, e.getMessage());
    }

    @Test
    void notEmpty_exceptionFunction_stringListIsNull() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .notEmpty(strList -> ExampleException.withMessage(
                                "The stringArrayProperty should not be empty, but it is %s.", Arrays.toString(strList)));
            }
        };

        ExampleCommand command = exampleCommandWithStringArrayProperty(null);

        ExampleException e = assertThrows(ExampleException.class, () -> validator.validate(command));
        assertEquals("The stringArrayProperty should not be empty, but it is null.", e.getMessage());
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
                ruleForArray(ExampleCommand::getStringArrayProperty).isEmpty();
                ruleForArray(ExampleCommand::getStringArrayProperty).isEmpty(MESSAGE_EMPTY);
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .isEmpty(() -> ExampleException.withMessage(MESSAGE_EMPTY));
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .isEmpty(strList -> ExampleException.withMessage(
                                "The stringArrayProperty should be empty, but it is %s.", Arrays.toString(strList)));
            }
        };

        ExampleCommand command = exampleCommandWithStringArrayProperty(new String[] {});
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void isEmpty_stringListIsNull() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty).isEmpty();
                ruleForArray(ExampleCommand::getStringArrayProperty).isEmpty(MESSAGE_EMPTY);
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .isEmpty(() -> ExampleException.withMessage(MESSAGE_EMPTY));
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .isEmpty(strList -> ExampleException.withMessage(
                                "The stringArrayProperty should be empty, but it is %s.", Arrays.toString(strList)));
            }
        };

        ExampleCommand command = exampleCommandWithStringArrayProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void isEmpty_default_stringListIsNotEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty).isEmpty();
            }
        };

        ExampleCommand command = exampleCommandWithStringArrayProperty(new String[] { "A", "B", "C" });

        ValidationException e = assertThrows(ValidationException.class, () -> validator.validate(command));
        assertEquals("The input must be empty.", e.getMessage());
    }

    @Test
    void isEmpty_message_stringListIsNotEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty).isEmpty(MESSAGE_EMPTY);
            }
        };

        ExampleCommand command = exampleCommandWithStringArrayProperty(new String[] { "A", "B", "C" });

        ValidationException e = assertThrows(ValidationException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_EMPTY, e.getMessage());
    }

    @Test
    void isEmpty_exceptionSupplier_stringListIsNotEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .isEmpty(() -> ExampleException.withMessage(MESSAGE_EMPTY));
            }
        };

        ExampleCommand command = exampleCommandWithStringArrayProperty(new String[] { "A", "B", "C" });

        ExampleException e = assertThrows(ExampleException.class, () -> validator.validate(command));
        assertEquals(MESSAGE_EMPTY, e.getMessage());
    }

    @Test
    void isEmpty_exceptionFunction_stringListIsNotEmpty() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .isEmpty(strList -> ExampleException.withMessage(
                                "The stringArrayProperty should be empty, but it is %s.", Arrays.toString(strList)));
            }
        };

        ExampleCommand command = exampleCommandWithStringArrayProperty(new String[] { "A", "B", "C" });

        ExampleException e = assertThrows(ExampleException.class, () -> validator.validate(command));
        assertEquals("The stringArrayProperty should be empty, but it is [A, B, C].", e.getMessage());
    }

    // ================================
    // #endregion - isEmpty
    // ================================

    // ================================
    // #region - allMatch
    // ================================

    static boolean checkStringLength(String str, int min, int max) {
        return str != null && (str.length() >= min && str.length() <= max);
    }

    @Test
    void allMatch_validInput() {

        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                    .allMatch(str -> checkStringLength(str, 4, 6))
                    .allMatch(str -> checkStringLength(str, 4, 6),
                        "String length must in the interval [4,6].")
                    .allMatch(str -> checkStringLength(str, 4, 6),
                        () -> ExampleException.withMessage("String length must in the interval [4,6]."))
                    .allMatch(str -> checkStringLength(str, 4, 6),
                        str -> ExampleException.withMessage("Validation failed: '%s'.", str));
            }
        };

        {
            ExampleCommand command = exampleCommandWithStringArrayProperty(new String[] { "1234", "12345", "123456" });
            assertDoesNotThrow(() -> validator.validate(command));
        }
        {
            ExampleCommand command = exampleCommandWithStringArrayProperty(new String[0]);
            assertDoesNotThrow(() -> validator.validate(command));
        }
        {
            ExampleCommand command = exampleCommandWithStringArrayProperty(null);
            assertDoesNotThrow(() -> validator.validate(command));
        }
    }

    @Test
    void allMatch_default_invalidInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                    .allMatch(str -> checkStringLength(str, 4, 6));
            }
        };

        ExampleCommand command = exampleCommandWithStringArrayProperty(new String[] { null, "1234", "12345", "123456" });
        ValidationException e = assertThrows(ValidationException.class, () -> validator.validate(command));
        assertEquals("All elements must match the condition.", e.getMessage());
    }

    @Test
    void allMatch_specifiedMessage_invalidInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                    .allMatch(str -> checkStringLength(str, 4, 6),
                        "String length must in the interval [4,6].");
            }
        };

        ExampleCommand command = exampleCommandWithStringArrayProperty(new String[] { "1234", "", "12345", "123456" });
        ValidationException e = assertThrows(ValidationException.class, () -> validator.validate(command));
        assertEquals("String length must in the interval [4,6].", e.getMessage());
    }

    @Test
    void allMatch_specifiedExceptionSupplier_invalidInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                    .allMatch(str -> checkStringLength(str, 4, 6),
                        () -> ExampleException.withMessage("String length must in the interval [4,6]."));
            }
        };

        ExampleCommand command = exampleCommandWithStringArrayProperty(new String[] { "1234", "12345", "123", "123456" });
        ExampleException e = assertThrows(ExampleException.class, () -> validator.validate(command));
        assertEquals("String length must in the interval [4,6].", e.getMessage());
    }

    @Test
    void allMatch_specifiedExceptionFunction_invalidInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                    .allMatch(str -> checkStringLength(str, 4, 6),
                        str -> ExampleException.withMessage("Validation failed: '%s'.", str));
            }
        };

        ExampleCommand command = exampleCommandWithStringArrayProperty(new String[] { "1234", "12345", "123456", "1234567" });
        ExampleException e = assertThrows(ExampleException.class, () -> validator.validate(command));
        assertEquals("Validation failed: '1234567'.", e.getMessage());
    }

    // ================================
    // #endregion - allMatch
    // ================================

    // ================================
    // #region - length
    // ================================

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 8;

    @Test
    void length_specifiedLength_validLength() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .length(MIN_LENGTH, "The length of the array must be 6")
                        .length(MIN_LENGTH, () -> ExampleException.withMessage("The length of the array must be 6"))
                        .length(MIN_LENGTH, arr -> ExampleException.withMessage("The length of the array must be 6, but it was %d", arr.length));
            }
        };
        ExampleCommand validCommand = exampleCommandWithStringArrayProperty(6);
        assertDoesNotThrow(() -> validator.validate(validCommand));

        ExampleCommand commandWithNullStringProperty = exampleCommandWithStringArrayProperty(null);
        assertDoesNotThrow(() -> validator.validate(commandWithNullStringProperty));
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 7, 8, 9, 10 })
    void length_specifiedLength_invalidLength(int length) {
        ExampleCommand command = exampleCommandWithStringArrayProperty(length);

        IValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .length(MIN_LENGTH, "The length of the array must be 6");
            }
        };
        ValidationException eWithSpecifiedMessage = assertThrows(
                ValidationException.class,
                () -> ruleWithMessage.validate(command));
        assertEquals("The length of the array must be 6", eWithSpecifiedMessage.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .length(MIN_LENGTH, () -> ExampleException.withMessage("The length of the array must be 6"));
            }
        };
        ExampleException specifiedException = assertThrows(
                ExampleException.class,
                () -> ruleWithExceptionSupplier.validate(command));
        assertEquals("The length of the array must be 6", specifiedException.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .length(MIN_LENGTH, arr -> ExampleException.withMessage("The length of the array must be 6, but it was %d", arr.length));
            }
        };
        ExampleException specifiedException2 = assertThrows(
                ExampleException.class,
                () -> ruleWithExceptionFunction.validate(command));
        assertEquals(
                String.format("The length of the array must be 6, but it was %d", length),
                specifiedException2.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = { 6, 7, 8 })
    void length_specifiedMinLengthAndMaxLength_validLength(int length) {
        ExampleCommand command = exampleCommandWithStringArrayProperty(length);
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .length(MIN_LENGTH, MAX_LENGTH, String.format("Min length is %d, max length is %d", MIN_LENGTH, MAX_LENGTH))
                        .length(MIN_LENGTH, MAX_LENGTH, () -> ExampleException.withMessage("Min length is %d, max length is %d", MIN_LENGTH, MAX_LENGTH))
                        .length(MIN_LENGTH, MAX_LENGTH, arr -> ExampleException.withMessage("Length of stringArrayProperty is %d, min length is %d, max length is %d", arr.length, MIN_LENGTH, MAX_LENGTH));
            }
        };
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void length_specifiedMinLengthAndMaxLength_null() {
        ExampleCommand command = exampleCommandWithStringArrayProperty(null);
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .length(MIN_LENGTH, MAX_LENGTH, String.format("Min length is %d, max length is %d", MIN_LENGTH, MAX_LENGTH))
                        .length(MIN_LENGTH, MAX_LENGTH, () -> ExampleException.withMessage("Min length is %d, max length is %d", MIN_LENGTH, MAX_LENGTH))
                        .length(MIN_LENGTH, MAX_LENGTH, arr -> ExampleException.withMessage("Length of stringArrayProperty is %d, min length is %d, max length is %d", arr.length, MIN_LENGTH, MAX_LENGTH));
            }
        };
        assertDoesNotThrow(() -> validator.validate(command));
    }


    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 9, 10, 11, 12 })
    void length_specifiedMinLengthAndMaxLength_invalidLength(int length) {
        ExampleCommand command = exampleCommandWithStringArrayProperty(length);

        IValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .length(MIN_LENGTH, MAX_LENGTH, String.format("Min length is %d, max length is %d", MIN_LENGTH, MAX_LENGTH));
            }
        };
        ValidationException eWithSpecifiedMessage = assertThrows(
                ValidationException.class,
                () -> ruleWithMessage.validate(command));
        assertEquals("Min length is 6, max length is 8", eWithSpecifiedMessage.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .length(MIN_LENGTH, MAX_LENGTH, () -> ExampleException.withMessage("Min length is %d, max length is %d", MIN_LENGTH, MAX_LENGTH));
            }
        };
        ExampleException specifiedException = assertThrows(
            ExampleException.class,
                () -> ruleWithExceptionSupplier.validate(command));
        assertEquals("Min length is 6, max length is 8", specifiedException.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                ruleForArray(ExampleCommand::getStringArrayProperty)
                        .length(MIN_LENGTH, MAX_LENGTH, arr -> ExampleException.withMessage("Length of stringArrayProperty is %d, min length is %d, max length is %d", arr.length, MIN_LENGTH, MAX_LENGTH));
            }
        };
        ExampleException specifiedException2 = assertThrows(
            ExampleException.class,
                () -> ruleWithExceptionFunction.validate(command));
        assertEquals(
                String.format("Length of stringArrayProperty is %d, min length is %d, max length is %d", length, MIN_LENGTH, MAX_LENGTH),
                specifiedException2.getMessage());
    }

    // ================================
    // #endregion - length
    // ================================

    static ExampleCommand exampleCommandWithStringArrayProperty(String[] property) {
        ExampleCommand exampleCommand = new ExampleCommand();
        exampleCommand.setStringArrayProperty(property);
        return exampleCommand;
    }

    static ExampleCommand exampleCommandWithStringArrayProperty(int specifiedLength) {
        ExampleCommand exampleCommand = new ExampleCommand();
        String[] arr = new String[specifiedLength];
        Arrays.fill(arr, "a");
        exampleCommand.setStringArrayProperty(arr);
        return exampleCommand;
    }
}
