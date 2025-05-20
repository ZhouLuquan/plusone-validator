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

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

import xyz.zhouxy.plusone.commons.collection.CollectionTools;
import xyz.zhouxy.plusone.commons.util.DateTimeTools;
import xyz.zhouxy.plusone.commons.util.StringTools;
import xyz.zhouxy.plusone.example.ExampleCommand;
import xyz.zhouxy.plusone.example.Foo;
import xyz.zhouxy.plusone.validator.BaseValidator;

public class ObjectPropertyValidatorTests {

    // ================================
    // #region - withRule
    // ================================

    @Test
    void withRule() {
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleFor(ExampleCommand::getBoolProperty)
                        .notNull("The boolProperty cannot be null.")
                        .withRule(Boolean.TRUE::equals, "The boolProperty should be true.");

                ruleFor(ExampleCommand::getIntProperty)
                        .withRule(intProperty -> intProperty > 0, "The intProperty should be greater than 0.");

                ruleFor(ExampleCommand::getLongProperty)
                        .withRule(longProperty -> longProperty > 0L,
                                () -> ExampleException.withMessage("The longProperty should be greater than 0."));

                ruleFor(ExampleCommand::getDoubleProperty)
                        .withRule(doubleProperty -> doubleProperty > 0.00,
                                doubleProperty -> ExampleException.withMessage("The doubleProperty should be greater than 0, but it was: %s", doubleProperty));

                ruleFor(ExampleCommand::getStringProperty)
                        .notNull()
                        .withRule(stringProperty -> stringProperty.length() > 2,
                                () -> ExampleException.withMessage("The length of stringProperty should be greater than 2."));

                ruleFor(ExampleCommand::getDateTimeProperty)
                        .withRule(DateTimeTools::isFuture,
                                () -> new DateTimeException("The dateTimeProperty should be a future time."));

                ruleFor(ExampleCommand::getObjectProperty)
                        .notNull("The objectProperty cannot be null.");

                ruleFor(ExampleCommand::getStringListProperty)
                        .withRule(CollectionTools::isNotEmpty, "The stringListProperty cannot be empty.");

                withRule(command -> {
                    Foo objectProperty = command.getObjectProperty();
                    if (!Objects.equals(command.getIntProperty(), objectProperty.getIntProperty())) {
                        throw ExampleException.withMessage("intProperty invalid.");
                    }
                });
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
                Lists.newArrayList("ABC", "DEF"));

        assertDoesNotThrow(() -> validator.validate(command));
    }

    // ================================
    // #endregion - withRule
    // ================================

    // ================================
    // #region - notNull
    // ================================

    @Test
    void notNull_validInput() {
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty)
                        .notNull();
                ruleForInt(ExampleCommand::getIntProperty)
                        .notNull("The intProperty cannot be null");
                ruleForLong(ExampleCommand::getLongProperty)
                        .notNull(() -> ExampleException.withMessage("The longProperty cannot be null"));
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .notNull(d -> ExampleException.withMessage("The doubleProperty cannot be null, but it was %s", d));
                ruleForString(ExampleCommand::getStringProperty)
                        .notNull();
                ruleForComparable(ExampleCommand::getDateTimeProperty)
                        .notNull("The dateTimeProperty cannot be null");
                ruleFor(ExampleCommand::getObjectProperty)
                        .notNull(() -> ExampleException.withMessage("The objectProperty cannot be null"));
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .notNull(d -> ExampleException.withMessage("The stringListProperty cannot be null, but it was %s", d));
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
                Lists.newArrayList("ABC", "DEF"));

        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void notNull_invalidInput() {
        ExampleCommand command = new ExampleCommand();

        BaseValidator<ExampleCommand> defaultRule = new BaseValidator<ExampleCommand>() {
            {
                ruleFor(ExampleCommand::getObjectProperty)
                        .notNull();
            }
        };
        IllegalArgumentException eWithDefaultMessage = assertThrows(
                IllegalArgumentException.class, () -> defaultRule.validate(command));
        assertEquals("Value could not be null.", eWithDefaultMessage.getMessage());

        BaseValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleFor(ExampleCommand::getObjectProperty)
                        .notNull("The objectProperty could not be null.");
            }
        };
        IllegalArgumentException eWithSpecifiedMessage = assertThrows(
                IllegalArgumentException.class, () -> ruleWithMessage.validate(command));
        assertEquals("The objectProperty could not be null.", eWithSpecifiedMessage.getMessage());

        BaseValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleFor(ExampleCommand::getObjectProperty)
                        .notNull(() -> ExampleException.withMessage("The objectProperty could not be null."));
            }
        };
        ExampleException specifiedException = assertThrows(
                ExampleException.class, () -> ruleWithExceptionSupplier.validate(command));
        assertEquals("The objectProperty could not be null.", specifiedException.getMessage());

        BaseValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
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
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForBool(ExampleCommand::getBoolProperty)
                        .isNull("The boolProperty should be null");
                ruleForInt(ExampleCommand::getIntProperty)
                        .isNull("The intProperty should be null");
                ruleForLong(ExampleCommand::getLongProperty)
                        .isNull(() -> ExampleException.withMessage("The longProperty should be null"));
                ruleForDouble(ExampleCommand::getDoubleProperty)
                        .isNull(d -> ExampleException.withMessage("The doubleProperty should be null, but it was %s", d));
                ruleForString(ExampleCommand::getStringProperty)
                        .isNull("The stringProperty should be null");
                ruleForComparable(ExampleCommand::getDateTimeProperty)
                        .isNull("The dateTimeProperty should be null");
                ruleFor(ExampleCommand::getObjectProperty)
                        .isNull(() -> ExampleException.withMessage("The objectProperty should be null"));
                ruleForCollection(ExampleCommand::getStringListProperty)
                        .isNull(d -> ExampleException.withMessage("The stringListProperty should be null, but it was %s", d));
            }
        };
        ExampleCommand command = new ExampleCommand();

        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void isNull_invalidInput() {
        ExampleCommand command = new ExampleCommand();
        command.setObjectProperty(new Foo(Integer.MAX_VALUE, "StringValue"));

        BaseValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleFor(ExampleCommand::getObjectProperty)
                        .isNull("The objectProperty should be null.");
            }
        };
        IllegalArgumentException eWithSpecifiedMessage = assertThrows(
                IllegalArgumentException.class, () -> ruleWithMessage.validate(command));
        assertEquals("The objectProperty should be null.", eWithSpecifiedMessage.getMessage());

        BaseValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleFor(ExampleCommand::getObjectProperty)
                        .isNull(() -> ExampleException.withMessage("The objectProperty should be null."));
            }
        };
        ExampleException specifiedException = assertThrows(
                ExampleException.class, () -> ruleWithExceptionSupplier.validate(command));
        assertEquals("The objectProperty should be null.", specifiedException.getMessage());

        BaseValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
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
    // #region - equalsThat
    // ================================

    @Test
    void equalsThat() {

    }

    @Test
    void equalsThat2() {

    }

    @Test
    void equalsThat3() {

    }

    @Test
    void equalsThat4() {

    }

    // ================================
    // #endregion - equalsThat
    // ================================

    // ================================
    // #region - must
    // ================================

    @Test
    void must() {

    }

    @Test
    void must2() {

    }

    @Test
    void must3() {

    }

    @Test
    void must4() {

    }

    @Test
    void must5() {

    }

    @Test
    void must6() {

    }

    @Test
    void must7() {

    }

    @Test
    void must8() {

    }

    // ================================
    // #endregion - must
    // ================================
}
