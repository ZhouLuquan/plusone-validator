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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import xyz.zhouxy.plusone.ExampleException;
import xyz.zhouxy.plusone.commons.util.StringTools;
import xyz.zhouxy.plusone.example.ExampleCommand;
import xyz.zhouxy.plusone.example.Foo;
import xyz.zhouxy.plusone.validator.BaseValidator;
import xyz.zhouxy.plusone.validator.IValidator;
import xyz.zhouxy.plusone.validator.ValidationException;

public class ObjectPropertyValidatorTests {

    // ================================
    // #region - notNull
    // ================================

    @Test
    void notNull_validInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleFor(ExampleCommand::getBoolProperty)
                        .notNull();
                ruleFor(ExampleCommand::getIntProperty)
                        .notNull("The intProperty cannot be null");
                ruleFor(ExampleCommand::getLongProperty)
                        .notNull(() -> ExampleException.withMessage("The longProperty cannot be null"));
                ruleFor(ExampleCommand::getDoubleProperty)
                        .notNull(d -> ExampleException.withMessage("The doubleProperty cannot be null, but it was %s", d));
                ruleFor(ExampleCommand::getStringProperty)
                        .notNull();
                ruleFor(ExampleCommand::getDateTimeProperty)
                        .notNull("The dateTimeProperty cannot be null");
                ruleFor(ExampleCommand::getObjectProperty)
                        .notNull(() -> ExampleException.withMessage("The objectProperty cannot be null"));
                ruleFor(ExampleCommand::getStringListProperty)
                        .notNull(d -> ExampleException.withMessage("The stringListProperty cannot be null, but it was %s", d));
                ruleFor(ExampleCommand::getStringArrayProperty)
                        .notNull(d -> ExampleException.withMessage("The stringListProperty cannot be null, but it was %s", Arrays.toString(d)));
            }
        };
        ExampleCommand command = new ExampleCommand(
                true,
                Integer.MAX_VALUE,
                Long.MAX_VALUE,
                Double.MAX_VALUE,
                "StringValue",
                LocalDateTime.now().plusDays(1),
                new Foo(Integer.MAX_VALUE, "StringValue"),
                Lists.newArrayList("ABC", "DEF"),
                new String[] { "ABC", "DEF" });

        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void notNull_invalidInput() {
        ExampleCommand command = new ExampleCommand();

        IValidator<ExampleCommand> defaultRule = new BaseValidator<ExampleCommand>() {
            {
                ruleFor(ExampleCommand::getObjectProperty)
                        .notNull();
            }
        };
        ValidationException eWithDefaultMessage = assertThrows(
                ValidationException.class, () -> defaultRule.validate(command));
        assertEquals("The input must not be null.", eWithDefaultMessage.getMessage());

        IValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleFor(ExampleCommand::getObjectProperty)
                        .notNull("The objectProperty could not be null.");
            }
        };
        ValidationException eWithSpecifiedMessage = assertThrows(
                ValidationException.class, () -> ruleWithMessage.validate(command));
        assertEquals("The objectProperty could not be null.", eWithSpecifiedMessage.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleFor(ExampleCommand::getObjectProperty)
                        .notNull(() -> ExampleException.withMessage("The objectProperty could not be null."));
            }
        };
        ExampleException specifiedException = assertThrows(
                ExampleException.class, () -> ruleWithExceptionSupplier.validate(command));
        assertEquals("The objectProperty could not be null.", specifiedException.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                ruleFor(ExampleCommand::getObjectProperty)
                        .notNull(str -> ExampleException.withMessage("The objectProperty could not be null, but is was " + str));
            }
        };
        ExampleException specifiedException2 = assertThrows(
                ExampleException.class, () -> ruleWithExceptionFunction.validate(command));
        assertEquals("The objectProperty could not be null, but is was null", specifiedException2.getMessage());
    }

    // ================================
    // #endregion - notNull
    // ================================

    // ================================
    // #region - isNull
    // ================================

    @Test
    void isNull_validInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleFor(ExampleCommand::getBoolProperty)
                        .isNull();
                ruleFor(ExampleCommand::getIntProperty)
                        .isNull("The intProperty should be null");
                ruleFor(ExampleCommand::getLongProperty)
                        .isNull(() -> ExampleException.withMessage("The longProperty should be null"));
                ruleFor(ExampleCommand::getDoubleProperty)
                        .isNull(d -> ExampleException.withMessage("The doubleProperty should be null, but it was %s", d));
                ruleFor(ExampleCommand::getStringProperty)
                        .isNull();
                ruleFor(ExampleCommand::getDateTimeProperty)
                        .isNull("The dateTimeProperty should be null");
                ruleFor(ExampleCommand::getObjectProperty)
                        .isNull(() -> ExampleException.withMessage("The objectProperty should be null"));
                ruleFor(ExampleCommand::getStringListProperty)
                        .isNull(d -> ExampleException.withMessage("The stringListProperty should be null, but it was %s", d));
                ruleFor(ExampleCommand::getStringArrayProperty)
                        .isNull(d -> ExampleException.withMessage("The stringListProperty should be null, but it was %s", Arrays.toString(d)));
            }
        };
        ExampleCommand command = new ExampleCommand();

        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void isNull_invalidInput() {
        ExampleCommand command = new ExampleCommand();
        command.setObjectProperty(new Foo(Integer.MAX_VALUE, "StringValue"));

        IValidator<ExampleCommand> ruleWithDefaultMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleFor(ExampleCommand::getObjectProperty)
                        .isNull();
            }
        };
        ValidationException eWithDefaultMessage = assertThrows(
                ValidationException.class, () -> ruleWithDefaultMessage.validate(command));
        assertEquals("The input must be null.", eWithDefaultMessage.getMessage());

        IValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleFor(ExampleCommand::getObjectProperty)
                        .isNull("The objectProperty should be null.");
            }
        };
        ValidationException eWithSpecifiedMessage = assertThrows(
                ValidationException.class, () -> ruleWithMessage.validate(command));
        assertEquals("The objectProperty should be null.", eWithSpecifiedMessage.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleFor(ExampleCommand::getObjectProperty)
                        .isNull(() -> ExampleException.withMessage("The objectProperty should be null."));
            }
        };
        ExampleException specifiedException = assertThrows(
                ExampleException.class, () -> ruleWithExceptionSupplier.validate(command));
        assertEquals("The objectProperty should be null.", specifiedException.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                ruleFor(ExampleCommand::getObjectProperty)
                        .isNull(str -> ExampleException.withMessage("The objectProperty should be null, but is was " + str));
            }
        };
        ExampleException specifiedException2 = assertThrows(
                ExampleException.class, () -> ruleWithExceptionFunction.validate(command));
        assertEquals("The objectProperty should be null, but is was " + command.getObjectProperty(), specifiedException2.getMessage());
    }

    // ================================
    // #endregion - isNull
    // ================================

    // ================================
    // #region - equalTo
    // ================================

    @Test
    void equalTo_validInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .equalTo("Foo")
                        .equalTo("Foo", "The stringProperty should be equal to 'Foo'.")
                        .equalTo("Foo", () ->
                                ExampleException.withMessage("The stringProperty should be equal to 'Foo'."))
                        .equalTo("Foo", str ->
                                ExampleException.withMessage("The stringProperty should be equal to 'Foo', but is was '%s'.", str));
            }
        };
        ExampleCommand command = new ExampleCommand();

        command.setStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));

        command.setStringProperty("Foo");
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void equalTo_invalidInput() {
        ExampleCommand command = new ExampleCommand();
        command.setStringProperty("Bar");

        IValidator<ExampleCommand> defaultRule = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty).equalTo("Foo");
            }
        };
        ValidationException eWithDefaultMessage = assertThrows(
                ValidationException.class, () -> defaultRule.validate(command));
        assertEquals("The input must be equal to 'Foo'.", eWithDefaultMessage.getMessage());

        IValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty).equalTo("Foo",
                        "The stringProperty should be equal to 'Foo'.");
            }
        };
        ValidationException eWithSpecifiedMessage = assertThrows(
                ValidationException.class, () -> ruleWithMessage.validate(command));
        assertEquals("The stringProperty should be equal to 'Foo'.", eWithSpecifiedMessage.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty).equalTo("Foo",
                        () -> ExampleException.withMessage("The stringProperty should be equal to 'Foo'."));
            }
        };
        ExampleException specifiedException = assertThrows(
                ExampleException.class, () -> ruleWithExceptionSupplier.validate(command));
        assertEquals("The stringProperty should be equal to 'Foo'.", specifiedException.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty).equalTo("Foo",
                        str -> ExampleException.withMessage("The stringProperty should be equal to 'Foo', but is was '%s'.", str));
            }
        };
        ExampleException specifiedException2 = assertThrows(
                ExampleException.class, () -> ruleWithExceptionFunction.validate(command));
        assertEquals("The stringProperty should be equal to 'Foo', but is was 'Bar'.", specifiedException2.getMessage());
    }

    // ================================
    // #endregion - equalTo
    // ================================

    // ================================
    // #region - notEqual
    // ================================

    @Test
    void notEqual_validInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .notEqual("Foo")
                        .notEqual("Foo", "The stringProperty should not equal 'Foo'.")
                        .notEqual("Foo", () ->
                                ExampleException.withMessage("The stringProperty should not equal 'Foo'."))
                        .notEqual("Foo", str ->
                                ExampleException.withMessage("The stringProperty should not equal 'Foo', but is was '%s'.", str));
            }
        };
        ExampleCommand command = new ExampleCommand();

        command.setStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));

        command.setStringProperty("Bar");
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void notEqual_invalidInput() {
        ExampleCommand command = new ExampleCommand();
        command.setStringProperty("Foo");

        IValidator<ExampleCommand> defaultRule = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty).notEqual("Foo");
            }
        };
        ValidationException eWithDefaultMessage = assertThrows(
                ValidationException.class, () -> defaultRule.validate(command));
        assertEquals("The input must not equal 'Foo'.", eWithDefaultMessage.getMessage());

        IValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty).notEqual("Foo",
                        "The stringProperty should not equal 'Foo'.");
            }
        };
        ValidationException eWithSpecifiedMessage = assertThrows(
                ValidationException.class, () -> ruleWithMessage.validate(command));
        assertEquals("The stringProperty should not equal 'Foo'.", eWithSpecifiedMessage.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty).notEqual("Foo",
                        () -> ExampleException.withMessage("The stringProperty should not equal 'Foo'."));
            }
        };
        ExampleException specifiedException = assertThrows(
                ExampleException.class, () -> ruleWithExceptionSupplier.validate(command));
        assertEquals("The stringProperty should not equal 'Foo'.", specifiedException.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty).notEqual("Foo",
                        str -> ExampleException.withMessage("The stringProperty should not equal 'Foo', but is was '%s'.", str));
            }
        };
        ExampleException specifiedException2 = assertThrows(
                ExampleException.class, () -> ruleWithExceptionFunction.validate(command));
        assertEquals("The stringProperty should not equal 'Foo', but is was 'Foo'.", specifiedException2.getMessage());
    }

    // ================================
    // #endregion - notEqual
    // ================================

    // ================================
    // #region - must
    // ================================

    @Test
    void must_oneCondition_valid() {
        ExampleCommand command = new ExampleCommand();
        command.setStringProperty("Foo");

        IValidator<ExampleCommand> ruleWithDefaultMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .must(str -> Objects.equals(str, "Foo"))
                        .must(str -> Objects.equals(str, "Foo"), "The stringProperty must be equal to 'Foo'.")
                        .must(str -> Objects.equals(str, "Foo"), () -> ExampleException.withMessage("The stringProperty must be equal to 'Foo'."))
                        .must(str -> Objects.equals(str, "Foo"), str -> ExampleException.withMessage("The stringProperty must be equal to 'Foo', but is was '%s'.", str));
            }
        };
        assertDoesNotThrow(() -> ruleWithDefaultMessage.validate(command));
    }

    @Test
    void must_oneCondition_invalid() {
        ExampleCommand command = new ExampleCommand();

        IValidator<ExampleCommand> ruleWithDefaultMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .must(str -> Objects.equals(str, "Foo"));
            }
        };
        ValidationException  eWithDefaultMessage = assertThrows(
                ValidationException.class, () -> ruleWithDefaultMessage.validate(command));
        assertEquals("The specified condition was not met for the input.", eWithDefaultMessage.getMessage());

        IValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .must(str -> Objects.equals(str, "Foo"),
                                "The stringProperty must be equal to 'Foo'.");
            }
        };
        ValidationException  eWithSpecifiedMessage = assertThrows(
                ValidationException.class, () -> ruleWithMessage.validate(command));
        assertEquals("The stringProperty must be equal to 'Foo'.", eWithSpecifiedMessage.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .must(str -> Objects.equals(str, "Foo"),
                                () -> ExampleException.withMessage("The stringProperty must be equal to 'Foo'."));
            }
        };
        ExampleException specifiedException = assertThrows(
                ExampleException.class, () -> ruleWithExceptionSupplier.validate(command));
        assertEquals("The stringProperty must be equal to 'Foo'.", specifiedException.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .must(str -> Objects.equals(str, "Foo"),
                                str -> ExampleException.withMessage("The stringProperty must be equal to 'Foo', but is was '%s'.", str));
            }
        };
        ExampleException specifiedException2 = assertThrows(
                ExampleException.class, () -> ruleWithExceptionFunction.validate(command));
        assertEquals("The stringProperty must be equal to 'Foo', but is was 'null'.", specifiedException2.getMessage());
    }

    @Test
    void must_multipleConditions_valid() {
        ExampleCommand command = new ExampleCommand();
        command.setStringProperty("Foo");

        IValidator<ExampleCommand> ruleWithDefaultMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .must(ImmutableList.of(StringTools::isNotEmpty, str -> Objects.equals(str, "Foo")))
                        .must(ImmutableList.of(StringTools::isNotEmpty, str -> Objects.equals(str, "Foo")), "The stringProperty must be equal to 'Foo'.")
                        .must(ImmutableList.of(StringTools::isNotEmpty, str -> Objects.equals(str, "Foo")), () -> ExampleException.withMessage("The stringProperty must be equal to 'Foo'."))
                        .must(ImmutableList.of(StringTools::isNotEmpty, str -> Objects.equals(str, "Foo")), str -> ExampleException.withMessage("The stringProperty must be equal to 'Foo', but is was '%s'.", str));
            }
        };
        assertDoesNotThrow(() -> ruleWithDefaultMessage.validate(command));
    }

    @Test
    void must_multipleConditions_invalid() {
        ExampleCommand command = new ExampleCommand();
        command.setStringProperty("Bar");

        IValidator<ExampleCommand> ruleWithDefaultMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .must(ImmutableList.of(StringTools::isNotEmpty, str -> Objects.equals(str, "Foo")));
            }
        };
        ValidationException  eWithDefaultMessage = assertThrows(
                ValidationException.class, () -> ruleWithDefaultMessage.validate(command));
        assertEquals("The specified conditions were not met for the input.", eWithDefaultMessage.getMessage());

        IValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .must(ImmutableList.of(StringTools::isNotEmpty, str -> Objects.equals(str, "Foo")),
                                "The stringProperty must be equal to 'Foo'.");
            }
        };
        ValidationException  eWithSpecifiedMessage = assertThrows(
                ValidationException.class, () -> ruleWithMessage.validate(command));
        assertEquals("The stringProperty must be equal to 'Foo'.", eWithSpecifiedMessage.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .must(ImmutableList.of(StringTools::isNotEmpty, str -> Objects.equals(str, "Foo")),
                                () -> ExampleException.withMessage("The stringProperty must be equal to 'Foo'."));
            }
        };
        ExampleException specifiedException = assertThrows(
                ExampleException.class, () -> ruleWithExceptionSupplier.validate(command));
        assertEquals("The stringProperty must be equal to 'Foo'.", specifiedException.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .must(ImmutableList.of(StringTools::isNotEmpty, str -> Objects.equals(str, "Foo")),
                                str -> ExampleException.withMessage("The stringProperty must be equal to 'Foo', but is was '%s'.", str));
            }
        };
        ExampleException specifiedException2 = assertThrows(
                ExampleException.class, () -> ruleWithExceptionFunction.validate(command));
        assertEquals("The stringProperty must be equal to 'Foo', but is was 'Bar'.", specifiedException2.getMessage());
    }

    // ================================
    // #endregion - must
    // ================================
}
