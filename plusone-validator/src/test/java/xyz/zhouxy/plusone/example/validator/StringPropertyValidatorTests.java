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

import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.google.common.collect.Lists;

import xyz.zhouxy.plusone.ExampleException;
import xyz.zhouxy.plusone.commons.util.StringTools;
import xyz.zhouxy.plusone.example.ExampleCommand;
import xyz.zhouxy.plusone.validator.BaseValidator;
import xyz.zhouxy.plusone.validator.IValidator;
import xyz.zhouxy.plusone.validator.ValidationException;

public class StringPropertyValidatorTests {

    private static final String MESSAGE_SHOULD_MATCH = "Input should match pattern";
    private static final String MESSAGE_NOT_BLANK = "Input cannot be blank";
    private static final String MESSAGE_NOT_EMPTY = "Input cannot be empty";
    private static final String MESSAGE_NOT_EMAIL = "Input should be an email address";

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 8;

    // ================================
    // #region - matches
    // ================================

    @Test
    void matches_InputMatchesPattern() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matches(Pattern.compile("\\w{3,6}"), MESSAGE_SHOULD_MATCH)
                        .matches(Pattern.compile("\\w{3,6}"), () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH))
                        .matches(Pattern.compile("\\w{3,6}"), str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("abcd");
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matches_message_InputDoesNotMatchPattern() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matches(Pattern.compile("\\w{3,6}"), MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("a");
        ValidationException e = assertThrows(
                ValidationException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matches_exceptionSupplier_InputDoesNotMatchPattern() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matches(Pattern.compile("\\w{3,6}"), () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("a");
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matches_exceptionFunction_InputDoesNotMatchPattern() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matches(Pattern.compile("\\w{3,6}"), str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("a");
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals("Input should match pattern, but it is \"a\"", e.getMessage());
    }

    @Test
    void matches_message_InputIsNull() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matches(Pattern.compile("\\w{3,6}"), MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matches_exceptionSupplier_InputIsNull() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matches(Pattern.compile("\\w{3,6}"), () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matches_exceptionFunction_InputIsNull() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matches(Pattern.compile("\\w{3,6}"), str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));
    }

    // ================================
    // #endregion - matches
    // ================================

    // ================================
    // #region - matchesAny
    // ================================

    @Test
    void matchesAny_patternArray_InputMatchesPattern() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}")
        };
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAny(patterns, MESSAGE_SHOULD_MATCH)
                        .matchesAny(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH))
                        .matchesAny(patterns, str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("123456");
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matchesAny_patternArray_message_InputDoesNotMatchPattern() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}")
        };
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAny(patterns, MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("1234567");
        ValidationException e = assertThrows(
                ValidationException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesAny_patternArray_exceptionSupplier_InputDoesNotMatchPattern() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}")
        };
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAny(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("1234567");
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesAny_patternArray_exceptionFunction_InputDoesNotMatchPattern() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}")
        };
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAny(patterns, str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("1234567");
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals("Input should match pattern, but it is \"1234567\"", e.getMessage());
    }

    @Test
    void matchesAny_patternArray_message_InputIsNull() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}")
        };
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAny(patterns, MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matchesAny_patternArray_exceptionSupplier_InputIsNull() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}")
        };
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAny(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matchesAny_patternArray_exceptionFunction_InputIsNull() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}")
        };
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAny(patterns, str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matchesAny_patternList_InputMatchesPattern() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}"));
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAny(patterns, MESSAGE_SHOULD_MATCH)
                        .matchesAny(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH))
                        .matchesAny(patterns, str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("abcd");
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matchesAny_patternList_message_InputDoesNotMatchPattern() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}"));
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAny(patterns, MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("1234567");
        ValidationException e = assertThrows(
                ValidationException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesAny_patternList_exceptionSupplier_InputDoesNotMatchPattern() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}"));
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAny(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("1234567");
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesAny_patternList_exceptionFunction_InputDoesNotMatchPattern() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}"));
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAny(patterns, str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("1234567");
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals("Input should match pattern, but it is \"1234567\"", e.getMessage());
    }

    @Test
    void matchesAny_patternList_message_InputIsNull() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}"));
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAny(patterns, MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matchesAny_patternList_exceptionSupplier_InputIsNull() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}"));
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAny(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matchesAny_patternList_exceptionFunction_InputIsNull() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}"));
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAny(patterns, str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));
    }

    // ================================
    // #endregion - matchesAny
    // ================================

    // ================================
    // #region - matchesAll
    // ================================

    @Test
    void matchesAll_patternArray_InputMatchesPattern() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}")
        };
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, MESSAGE_SHOULD_MATCH)
                        .matchesAll(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH))
                        .matchesAll(patterns, str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("123456");
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matchesAll_patternArray_message_InputDoesNotMatchPattern() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}")
        };
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("1234567");
        ValidationException e = assertThrows(
                ValidationException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesAll_patternArray_exceptionSupplier_InputDoesNotMatchPattern() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}")
        };
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("1234567");
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesAll_patternArray_exceptionFunction_InputDoesNotMatchPattern() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}")
        };
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("1234567");
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals("Input should match pattern, but it is \"1234567\"", e.getMessage());
    }

    @Test
    void matchesAll_patternArray_message_InputIsNull() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}")
        };
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matchesAll_patternArray_exceptionSupplier_InputIsNull() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}")
        };
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matchesAll_patternArray_exceptionFunction_InputIsNull() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}")
        };
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matchesAll_patternList_InputMatchesPattern() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}"));
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, MESSAGE_SHOULD_MATCH)
                        .matchesAll(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH))
                        .matchesAll(patterns, str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("123456");
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matchesAll_patternList_message_InputDoesNotMatchPattern() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}"));
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("1234567");
        ValidationException e = assertThrows(
                ValidationException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesAll_patternList_exceptionSupplier_InputDoesNotMatchPattern() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}"));
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("1234567");
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesAll_patternList_exceptionFunction_InputDoesNotMatchPattern() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}"));
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("1234567");
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals("Input should match pattern, but it is \"1234567\"", e.getMessage());
    }

    @Test
    void matchesAll_patternList_message_InputIsNull() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}"));
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matchesAll_patternList_exceptionSupplier_InputIsNull() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}"));
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matchesAll_patternList_exceptionFunction_InputIsNull() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}"));
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(command));
    }

    // ================================
    // #endregion - matchesAll
    // ================================

    // ================================
    // #region - notBlank
    // ================================

    @Test
    void notBlank_all_validInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .notBlank()
                        .notBlank(MESSAGE_NOT_BLANK)
                        .notBlank(() -> ExampleException.withMessage(MESSAGE_NOT_BLANK))
                        .notBlank(str -> ExampleException.withMessage("The stringProperty cannot be blank, but is was %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("abcd");
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @ParameterizedTest
    @ValueSource(strings = { "", " ", "  ", "\t", "\n" })
    void notBlank_invalidInput(String value) {
        ExampleCommand command = exampleCommandWithStringProperty(value);

        IValidator<ExampleCommand> defaultRule = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .notBlank();
            }
        };
        ValidationException eWithDefaultMessage = assertThrows(
                ValidationException.class,
                () -> defaultRule.validate(command));
        assertEquals("The input must not be blank.", eWithDefaultMessage.getMessage());

        IValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .notBlank(MESSAGE_NOT_BLANK);
            }
        };
        ValidationException eWithSpecifiedMessage = assertThrows(
                ValidationException.class,
                () -> ruleWithMessage.validate(command));
        assertEquals(MESSAGE_NOT_BLANK, eWithSpecifiedMessage.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .notBlank(() -> ExampleException.withMessage(MESSAGE_NOT_BLANK));
            }
        };
        ExampleException specifiedException = assertThrows(
            ExampleException.class,
                () -> ruleWithExceptionSupplier.validate(command));
        assertEquals(MESSAGE_NOT_BLANK, specifiedException.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                .notBlank(str -> ExampleException.withMessage("The stringProperty cannot be blank, but is was %s", StringTools.toQuotedString(str)));
            }
        };
        ExampleException specifiedException2 = assertThrows(
            ExampleException.class,
                () -> ruleWithExceptionFunction.validate(command));
        assertEquals("The stringProperty cannot be blank, but is was " + StringTools.toQuotedString(value),
                specifiedException2.getMessage());
    }

    @Test
    void notBlank_nullInput() {
        ExampleCommand command = exampleCommandWithStringProperty(null);

        IValidator<ExampleCommand> defaultRule = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .notBlank();
            }
        };
        ValidationException eWithDefaultMessage = assertThrows(
                ValidationException.class,
                () -> defaultRule.validate(command));
        assertEquals("The input must not be blank.", eWithDefaultMessage.getMessage());

        IValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .notBlank(MESSAGE_NOT_BLANK);
            }
        };
        ValidationException eWithSpecifiedMessage = assertThrows(
                ValidationException.class,
                () -> ruleWithMessage.validate(command));
        assertEquals(MESSAGE_NOT_BLANK, eWithSpecifiedMessage.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .notBlank(() -> ExampleException.withMessage(MESSAGE_NOT_BLANK));
            }
        };
        ExampleException specifiedException = assertThrows(
            ExampleException.class,
                () -> ruleWithExceptionSupplier.validate(command));
        assertEquals(MESSAGE_NOT_BLANK, specifiedException.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                .notBlank(str -> ExampleException.withMessage("The stringProperty cannot be blank, but is was %s", StringTools.toQuotedString(str)));
            }
        };
        ExampleException specifiedException2 = assertThrows(
            ExampleException.class,
                () -> ruleWithExceptionFunction.validate(command));
        assertEquals("The stringProperty cannot be blank, but is was null",
                specifiedException2.getMessage());
    }

    // ================================
    // #endregion - notBlank
    // ================================

    // ================================
    // #region - emailAddress
    // ================================

    @Test
    void emailAddress_validInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .emailAddress()
                        .emailAddress(MESSAGE_NOT_EMAIL)
                        .emailAddress(() -> ExampleException.withMessage(MESSAGE_NOT_EMAIL))
                        .emailAddress(str -> ExampleException.withMessage("Input should be an email address, but it was \"%s\"", str));
            }
        };
        ExampleCommand validCommand = exampleCommandWithStringProperty("abc@example.com");
        assertDoesNotThrow(() -> validator.validate(validCommand));

        ExampleCommand commandWithNullStringProperty = exampleCommandWithStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(commandWithNullStringProperty));
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "abc", "abc@def@example.com" })
    void emailAddress_invalidInput(String value) {
        ExampleCommand command = exampleCommandWithStringProperty(value);

        IValidator<ExampleCommand> defaultRule = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .emailAddress();
            }
        };
        ValidationException eWithDefaultMessage = assertThrows(
                ValidationException.class,
                () -> defaultRule.validate(command));
        assertEquals("The input is not a valid email address.", eWithDefaultMessage.getMessage());

        IValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .emailAddress(MESSAGE_NOT_EMAIL);
            }
        };
        ValidationException eWithSpecifiedMessage = assertThrows(
                ValidationException.class,
                () -> ruleWithMessage.validate(command));
        assertEquals(MESSAGE_NOT_EMAIL, eWithSpecifiedMessage.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .emailAddress(() -> ExampleException.withMessage(MESSAGE_NOT_EMAIL));
            }
        };
        ExampleException specifiedException = assertThrows(
            ExampleException.class,
                () -> ruleWithExceptionSupplier.validate(command));
        assertEquals(MESSAGE_NOT_EMAIL, specifiedException.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .emailAddress(str -> ExampleException.withMessage("Input should be an email address, but it was \"%s\"", str));
            }
        };
        ExampleException specifiedException2 = assertThrows(
            ExampleException.class,
                () -> ruleWithExceptionFunction.validate(command));
        assertEquals(String.format("Input should be an email address, but it was \"%s\"", value), specifiedException2.getMessage());
    }

    // ================================
    // #endregion - emailAddress
    // ================================

    // ================================
    // #region - notEmpty
    // ================================

    @ParameterizedTest
    @ValueSource(strings = { "abcd", " ", "  ", "\t", "\n" })
    void notEmpty_all_validInput() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .notEmpty()
                        .notEmpty(MESSAGE_NOT_EMPTY)
                        .notEmpty(() -> ExampleException.withMessage(MESSAGE_NOT_EMPTY))
                        .notEmpty(str -> ExampleException.withMessage("The stringProperty cannot be empty, but is was %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("abcd");
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void notEmpty_invalidInput() {
        final String value = "";
        ExampleCommand command = exampleCommandWithStringProperty(value);

        IValidator<ExampleCommand> defaultRule = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .notEmpty();
            }
        };
        ValidationException eWithDefaultMessage = assertThrows(
                ValidationException.class,
                () -> defaultRule.validate(command));
        assertEquals("The input must not be empty.", eWithDefaultMessage.getMessage());

        IValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .notEmpty(MESSAGE_NOT_EMPTY);
            }
        };
        ValidationException eWithSpecifiedMessage = assertThrows(
                ValidationException.class,
                () -> ruleWithMessage.validate(command));
        assertEquals(MESSAGE_NOT_EMPTY, eWithSpecifiedMessage.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .notEmpty(() -> ExampleException.withMessage(MESSAGE_NOT_EMPTY));
            }
        };
        ExampleException specifiedException = assertThrows(
            ExampleException.class,
                () -> ruleWithExceptionSupplier.validate(command));
        assertEquals(MESSAGE_NOT_EMPTY, specifiedException.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                .notEmpty(str -> ExampleException.withMessage("The stringProperty cannot be empty, but is was %s", StringTools.toQuotedString(str)));
            }
        };
        ExampleException specifiedException2 = assertThrows(
            ExampleException.class,
                () -> ruleWithExceptionFunction.validate(command));
        assertEquals("The stringProperty cannot be empty, but is was " + StringTools.toQuotedString(value),
                specifiedException2.getMessage());
    }

    @Test
    void notEmpty_nullInput() {
        ExampleCommand command = exampleCommandWithStringProperty(null);

        IValidator<ExampleCommand> defaultRule = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .notEmpty();
            }
        };
        ValidationException eWithDefaultMessage = assertThrows(
                ValidationException.class,
                () -> defaultRule.validate(command));
        assertEquals("The input must not be empty.", eWithDefaultMessage.getMessage());

        IValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .notEmpty(MESSAGE_NOT_EMPTY);
            }
        };
        ValidationException eWithSpecifiedMessage = assertThrows(
                ValidationException.class,
                () -> ruleWithMessage.validate(command));
        assertEquals(MESSAGE_NOT_EMPTY, eWithSpecifiedMessage.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .notEmpty(() -> ExampleException.withMessage(MESSAGE_NOT_EMPTY));
            }
        };
        ExampleException specifiedException = assertThrows(
            ExampleException.class,
                () -> ruleWithExceptionSupplier.validate(command));
        assertEquals(MESSAGE_NOT_EMPTY, specifiedException.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .notEmpty(str -> ExampleException.withMessage("The stringProperty cannot be empty, but is was %s", StringTools.toQuotedString(str)));
            }
        };
        ExampleException specifiedException2 = assertThrows(
            ExampleException.class,
                () -> ruleWithExceptionFunction.validate(command));
        assertEquals("The stringProperty cannot be empty, but is was null",
                specifiedException2.getMessage());
    }

    // ================================
    // #endregion - notEmpty
    // ================================

    // ================================
    // #region - length
    // ================================

    @Test
    void length_specifiedLength_validLength() {
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .length(MIN_LENGTH, "The length of the string must be 6")
                        .length(MIN_LENGTH, () -> ExampleException.withMessage("The length of the string must be 6"))
                        .length(MIN_LENGTH, str -> ExampleException.withMessage("The length of the string must be 6, but it was %d", str.length()));
            }
        };
        ExampleCommand validCommand = exampleCommandWithStringProperty("123456");
        assertDoesNotThrow(() -> validator.validate(validCommand));

        ExampleCommand commandWithNullStringProperty = exampleCommandWithStringProperty(null);
        assertDoesNotThrow(() -> validator.validate(commandWithNullStringProperty));
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "12345", "1234567" })
    void length_specifiedLength_invalidLength(String value) {
        ExampleCommand command = exampleCommandWithStringProperty(value);

        IValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .length(MIN_LENGTH, "The length of the string must be 6");
            }
        };
        ValidationException eWithSpecifiedMessage = assertThrows(
                ValidationException.class,
                () -> ruleWithMessage.validate(command));
        assertEquals("The length of the string must be 6", eWithSpecifiedMessage.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .length(MIN_LENGTH, () -> ExampleException.withMessage("The length of the string must be 6"));
            }
        };
        ExampleException specifiedException = assertThrows(
                ExampleException.class,
                () -> ruleWithExceptionSupplier.validate(command));
        assertEquals("The length of the string must be 6", specifiedException.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .length(MIN_LENGTH, str -> ExampleException.withMessage("The length of the string must be 6, but it was %d", str.length()));
            }
        };
        ExampleException specifiedException2 = assertThrows(
                ExampleException.class,
                () -> ruleWithExceptionFunction.validate(command));
        assertEquals(
                String.format("The length of the string must be 6, but it was %d", value.length()),
                specifiedException2.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { "123456", "1234567", "12345678" })
    void length_specifiedMinLengthAndMaxLength_validLength(String value) {
        ExampleCommand command = exampleCommandWithStringProperty(value);
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .length(MIN_LENGTH, MAX_LENGTH, String.format("Min length is %d, max length is %d", MIN_LENGTH, MAX_LENGTH))
                        .length(MIN_LENGTH, MAX_LENGTH, () -> ExampleException.withMessage("Min length is %d, max length is %d", MIN_LENGTH, MAX_LENGTH))
                        .length(MIN_LENGTH, MAX_LENGTH, str -> ExampleException.withMessage("Length of StringProperty is %d, min length is %d, max length is %d", str.length(), MIN_LENGTH, MAX_LENGTH));
            }
        };
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void length_specifiedMinLengthAndMaxLength_null() {
        ExampleCommand command = exampleCommandWithStringProperty(null);
        IValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .length(MIN_LENGTH, MAX_LENGTH, String.format("Min length is %d, max length is %d", MIN_LENGTH, MAX_LENGTH))
                        .length(MIN_LENGTH, MAX_LENGTH, () -> ExampleException.withMessage("Min length is %d, max length is %d", MIN_LENGTH, MAX_LENGTH))
                        .length(MIN_LENGTH, MAX_LENGTH, str -> ExampleException.withMessage("Length of StringProperty is %d, min length is %d, max length is %d", str.length(), MIN_LENGTH, MAX_LENGTH));
            }
        };
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "12345", "123456789" })
    void length_specifiedMinLengthAndMaxLength_invalidLength(String value) {
        ExampleCommand command = exampleCommandWithStringProperty(value);

        IValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .length(MIN_LENGTH, MAX_LENGTH, String.format("Min length is %d, max length is %d", MIN_LENGTH, MAX_LENGTH));
            }
        };
        ValidationException eWithSpecifiedMessage = assertThrows(
                ValidationException.class,
                () -> ruleWithMessage.validate(command));
        assertEquals("Min length is 6, max length is 8", eWithSpecifiedMessage.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .length(MIN_LENGTH, MAX_LENGTH, () -> ExampleException.withMessage("Min length is %d, max length is %d", MIN_LENGTH, MAX_LENGTH));
            }
        };
        ExampleException specifiedException = assertThrows(
            ExampleException.class,
                () -> ruleWithExceptionSupplier.validate(command));
        assertEquals("Min length is 6, max length is 8", specifiedException.getMessage());

        IValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .length(MIN_LENGTH, MAX_LENGTH, str -> ExampleException.withMessage("Length of StringProperty is %d, min length is %d, max length is %d", str.length(), MIN_LENGTH, MAX_LENGTH));
            }
        };
        ExampleException specifiedException2 = assertThrows(
            ExampleException.class,
                () -> ruleWithExceptionFunction.validate(command));
        assertEquals(
                String.format("Length of StringProperty is %d, min length is %d, max length is %d", value.length(), MIN_LENGTH, MAX_LENGTH),
                specifiedException2.getMessage());
    }

    // ================================
    // #endregion - length
    // ================================

    static ExampleCommand exampleCommandWithStringProperty(String property) {
        ExampleCommand exampleCommand = new ExampleCommand();
        exampleCommand.setStringProperty(property);
        return exampleCommand;
    }
}
