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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.google.common.collect.Lists;

import xyz.zhouxy.plusone.ExampleException;
import xyz.zhouxy.plusone.example.ExampleCommand;
import xyz.zhouxy.plusone.validator.BaseValidator;
import xyz.zhouxy.plusone.validator.IValidator;
import xyz.zhouxy.plusone.validator.ValidationException;

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

        ValidationException e = assertThrows(ValidationException.class, () -> validator.validate(command));
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

        ValidationException e = assertThrows(ValidationException.class, () -> validator.validate(command));
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

        ValidationException e = assertThrows(ValidationException.class, () -> validator.validate(command));
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

        ValidationException e = assertThrows(ValidationException.class, () -> validator.validate(command));
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

        ValidationException e = assertThrows(ValidationException.class, () -> validator.validate(command));
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
                ruleForCollection(ExampleCommand::getStringListProperty)
                    .allMatch(str -> checkStringLength(str, 4, 6))
                    .allMatch(str -> checkStringLength(str, 4, 6),
                        "String length must in the interval [4,6].")
                    .allMatch(str -> checkStringLength(str, 4, 6),
                        () -> ExampleException.withMessage("String length must in the interval [4,6]."))
                    .allMatch(str -> checkStringLength(str, 4, 6),
                        str -> ExampleException.withMessage("Validation failed: '%s'.", str));
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(Lists.newArrayList("1234", "12345", "123456"));
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void allMatch_default_invalidInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                    .allMatch(str -> checkStringLength(str, 4, 6));
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(Lists.newArrayList(null, "1234", "12345", "123456"));
        ValidationException e = assertThrows(ValidationException.class, () -> validator.validate(command));
        assertEquals("All elements must match the condition.", e.getMessage());
    }

    @Test
    void allMatch_specifiedMessage_invalidInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                    .allMatch(str -> checkStringLength(str, 4, 6),
                        "String length must in the interval [4,6].");
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(Lists.newArrayList("1234", "", "12345", "123456"));
        ValidationException e = assertThrows(ValidationException.class, () -> validator.validate(command));
        assertEquals("String length must in the interval [4,6].", e.getMessage());
    }

    @Test
    void allMatch_specifiedExceptionSupplier_invalidInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                    .allMatch(str -> checkStringLength(str, 4, 6),
                        () -> ExampleException.withMessage("String length must in the interval [4,6]."));
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(Lists.newArrayList("1234", "12345", "123", "123456"));
        ExampleException e = assertThrows(ExampleException.class, () -> validator.validate(command));
        assertEquals("String length must in the interval [4,6].", e.getMessage());
    }

    @Test
    void allMatch_specifiedExceptionFunction_invalidInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                    .allMatch(str -> checkStringLength(str, 4, 6),
                        str -> ExampleException.withMessage("Validation failed: '%s'.", str));
            }
        };

        ExampleCommand command = exampleCommandWithStringListProperty(Lists.newArrayList("1234", "12345", "123456", "1234567"));
        ExampleException e = assertThrows(ExampleException.class, () -> validator.validate(command));
        assertEquals("Validation failed: '1234567'.", e.getMessage());
    }

    // ================================
    // #endregion - allMatch
    // ================================

    // ================================
    // #region - size
    // ================================

    private static final int MIN_SIZE = 6;
    private static final int MAX_SIZE = 8;

    @Test
    void size_specifiedSize_validSize() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .size(MIN_SIZE, "The size of the collection must be 6")
                        .size(MIN_SIZE, () -> ExampleException.withMessage("The size of the collection must be 6"))
                        .size(MIN_SIZE, c -> ExampleException.withMessage("The size of the collection must be 6, but it was %d", c.size()));
            }
        };
        ExampleCommand validCommand = exampleCommandWithStringListProperty(6);
        assertDoesNotThrow(() -> validator.validate(validCommand));

        ExampleCommand commandWithNullStringProperty = exampleCommandWithStringListProperty(null);
        assertDoesNotThrow(() -> validator.validate(commandWithNullStringProperty));
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 7, 8, 9, 10 })
    void size_specifiedSize_invalidSize(int size) {
        ExampleCommand command = exampleCommandWithStringListProperty(size);

        IValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .size(MIN_SIZE, "The size of the collection must be 6");
            }
        };
        ValidationException eWithSpecifiedMessage = assertThrows(
                ValidationException.class,
                () -> ruleWithMessage.validate(command));
        assertEquals("The size of the collection must be 6", eWithSpecifiedMessage.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .size(MIN_SIZE, () -> ExampleException.withMessage("The size of the collection must be 6"));
            }
        };
        ExampleException specifiedException = assertThrows(
                ExampleException.class,
                () -> ruleWithExceptionSupplier.validate(command));
        assertEquals("The size of the collection must be 6", specifiedException.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .size(MIN_SIZE, c -> ExampleException.withMessage("The size of the collection must be 6, but it was %d", c.size()));
            }
        };
        ExampleException specifiedException2 = assertThrows(
                ExampleException.class,
                () -> ruleWithExceptionFunction.validate(command));
        assertEquals(
                String.format("The size of the collection must be 6, but it was %d", size),
                specifiedException2.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = { 6, 7, 8 })
    void size_specifiedMinSizeAndMaxSize_validSize(int size) {
        ExampleCommand command = exampleCommandWithStringListProperty(size);
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .size(MIN_SIZE, MAX_SIZE, String.format("Min size is %d, max size is %d", MIN_SIZE, MAX_SIZE))
                        .size(MIN_SIZE, MAX_SIZE, () -> ExampleException.withMessage("Min size is %d, max size is %d", MIN_SIZE, MAX_SIZE))
                        .size(MIN_SIZE, MAX_SIZE, c -> ExampleException.withMessage("Size of stringCollectionProperty is %d, min size is %d, max size is %d", c.size(), MIN_SIZE, MAX_SIZE));
            }
        };
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void size_specifiedMinSizeAndMaxSize_null() {
        ExampleCommand command = exampleCommandWithStringListProperty(null);
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .size(MIN_SIZE, MAX_SIZE, String.format("Min size is %d, max size is %d", MIN_SIZE, MAX_SIZE))
                        .size(MIN_SIZE, MAX_SIZE, () -> ExampleException.withMessage("Min size is %d, max size is %d", MIN_SIZE, MAX_SIZE))
                        .size(MIN_SIZE, MAX_SIZE, c -> ExampleException.withMessage("Size of stringCollectionProperty is %d, min size is %d, max size is %d", c.size(), MIN_SIZE, MAX_SIZE));
            }
        };
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 9, 10, 11, 12 })
    void size_specifiedMinSizeAndMaxSize_invalidSize(int size) {
        ExampleCommand command = exampleCommandWithStringListProperty(size);

        IValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .size(MIN_SIZE, MAX_SIZE, String.format("Min size is %d, max size is %d", MIN_SIZE, MAX_SIZE));
            }
        };
        ValidationException eWithSpecifiedMessage = assertThrows(
                ValidationException.class,
                () -> ruleWithMessage.validate(command));
        assertEquals("Min size is 6, max size is 8", eWithSpecifiedMessage.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .size(MIN_SIZE, MAX_SIZE, () -> ExampleException.withMessage("Min size is %d, max size is %d", MIN_SIZE, MAX_SIZE));
            }
        };
        ExampleException specifiedException = assertThrows(
            ExampleException.class,
                () -> ruleWithExceptionSupplier.validate(command));
        assertEquals("Min size is 6, max size is 8", specifiedException.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .size(MIN_SIZE, MAX_SIZE, c -> ExampleException.withMessage("Size of stringCollectionProperty is %d, min size is %d, max size is %d", c.size(), MIN_SIZE, MAX_SIZE));
            }
        };
        ExampleException specifiedException2 = assertThrows(
            ExampleException.class,
                () -> ruleWithExceptionFunction.validate(command));
        assertEquals(
                String.format("Size of stringCollectionProperty is %d, min size is %d, max size is %d", size, MIN_SIZE, MAX_SIZE),
                specifiedException2.getMessage());
    }

    // ================================
    // #endregion - size
    // ================================

    static ExampleCommand exampleCommandWithStringListProperty(List<String> property) {
        ExampleCommand exampleCommand = new ExampleCommand();
        exampleCommand.setStringListProperty(property);
        return exampleCommand;
    }

    static ExampleCommand exampleCommandWithStringListProperty(int specifiedSize) {
        ExampleCommand exampleCommand = new ExampleCommand();
        String[] arr = new String[specifiedSize];
        Arrays.fill(arr, "a");
        exampleCommand.setStringListProperty(Arrays.asList(arr));
        return exampleCommand;
    }
}
