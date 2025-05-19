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

import com.google.common.collect.Lists;

import xyz.zhouxy.plusone.commons.util.StringTools;
import xyz.zhouxy.plusone.example.ExampleCommand;
import xyz.zhouxy.plusone.validator.BaseValidator;

public class StringPropertyValidatorTests {

    private static final String MESSAGE_SHOULD_MATCH = "Input should match pattern";

    // ================================
    // #region - matches
    // ================================

    @Test
    void matches_InputMatchesPattern() {
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
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
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matches(Pattern.compile("\\w{3,6}"), MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("a");
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matches_exceptionSupplier_InputDoesNotMatchPattern() {
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
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
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
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
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matches(Pattern.compile("\\w{3,6}"), MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matches_exceptionSupplier_InputIsNull() {
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matches(Pattern.compile("\\w{3,6}"), () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matches_exceptionFunction_InputIsNull() {
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matches(Pattern.compile("\\w{3,6}"), str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals("Input should match pattern, but it is null", e.getMessage());
    }

    // ================================
    // #endregion - matches
    // ================================

    // ================================
    // #region - matchesOne
    // ================================

    @Test
    void matchesOne_patternArray_InputMatchesPattern() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}")
        };
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesOne(patterns, MESSAGE_SHOULD_MATCH)
                        .matchesOne(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH))
                        .matchesOne(patterns, str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("123456");
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matchesOne_patternArray_message_InputDoesNotMatchPattern() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}")
        };
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesOne(patterns, MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("1234567");
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesOne_patternArray_exceptionSupplier_InputDoesNotMatchPattern() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}")
        };
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesOne(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("1234567");
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesOne_patternArray_exceptionFunction_InputDoesNotMatchPattern() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}")
        };
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesOne(patterns, str -> ExampleException.withMessage(
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
    void matchesOne_patternArray_message_InputIsNull() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}")
        };
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesOne(patterns, MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesOne_patternArray_exceptionSupplier_InputIsNull() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}")
        };
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesOne(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesOne_patternArray_exceptionFunction_InputIsNull() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}")
        };
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesOne(patterns, str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals("Input should match pattern, but it is null", e.getMessage());
    }

    @Test
    void matchesOne_patternList_InputMatchesPattern() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}"));
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesOne(patterns, MESSAGE_SHOULD_MATCH)
                        .matchesOne(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH))
                        .matchesOne(patterns, str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("abcd");
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void matchesOne_patternList_message_InputDoesNotMatchPattern() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}"));
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesOne(patterns, MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("1234567");
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesOne_patternList_exceptionSupplier_InputDoesNotMatchPattern() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}"));
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesOne(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("1234567");
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesOne_patternList_exceptionFunction_InputDoesNotMatchPattern() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}"));
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesOne(patterns, str -> ExampleException.withMessage(
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
    void matchesOne_patternList_message_InputIsNull() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}"));
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesOne(patterns, MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesOne_patternList_exceptionSupplier_InputIsNull() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}"));
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesOne(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesOne_patternList_exceptionFunction_InputIsNull() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{1,3}"),
                Pattern.compile("\\w{4,6}"));
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesOne(patterns, str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals("Input should match pattern, but it is null", e.getMessage());
    }

    // ================================
    // #endregion - matchesOne
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
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
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
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("1234567");
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesAll_patternArray_exceptionSupplier_InputDoesNotMatchPattern() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}")
        };
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
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
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
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
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesAll_patternArray_exceptionSupplier_InputIsNull() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}")
        };
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesAll_patternArray_exceptionFunction_InputIsNull() {
        final Pattern[] patterns = {
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}")
        };
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals("Input should match pattern, but it is null", e.getMessage());
    }

    @Test
    void matchesAll_patternList_InputMatchesPattern() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}"));
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
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
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty("1234567");
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesAll_patternList_exceptionSupplier_InputDoesNotMatchPattern() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}"));
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
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
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
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
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, MESSAGE_SHOULD_MATCH);
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesAll_patternList_exceptionSupplier_InputIsNull() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}"));
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, () -> ExampleException.withMessage(MESSAGE_SHOULD_MATCH));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals(MESSAGE_SHOULD_MATCH, e.getMessage());
    }

    @Test
    void matchesAll_patternList_exceptionFunction_InputIsNull() {
        final List<Pattern> patterns = Lists.newArrayList(
                Pattern.compile("\\w{4,6}"),
                Pattern.compile("\\w{5,7}"));
        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                ruleForString(ExampleCommand::getStringProperty)
                        .matchesAll(patterns, str -> ExampleException.withMessage(
                                "Input should match pattern, but it is %s", StringTools.toQuotedString(str)));
            }
        };

        ExampleCommand command = exampleCommandWithStringProperty(null);
        ExampleException e = assertThrows(
                ExampleException.class,
                () -> validator.validate(command));
        assertEquals("Input should match pattern, but it is null", e.getMessage());
    }

    // ================================
    // #endregion - matchesAll
    // ================================

    // ================================
    // #region - notBlank
    // ================================

    // TODO

    // ================================
    // #endregion - notBlank
    // ================================

    // ================================
    // #region - email
    // ================================

    // TODO

    // ================================
    // #endregion - email
    // ================================

    // ================================
    // #region - notEmpty
    // ================================

    // TODO

    // ================================
    // #endregion - notEmpty
    // ================================

    // ================================
    // #region - isNullOrEmpty
    // ================================

    // TODO

    // ================================
    // #endregion - isNullOrEmpty
    // ================================

    // ================================
    // #region - length
    // ================================

    // TODO

    // ================================
    // #endregion - length
    // ================================

    static ExampleCommand exampleCommandWithStringProperty(String property) {
        ExampleCommand exampleCommand = new ExampleCommand();
        exampleCommand.setStringProperty(property);
        return exampleCommand;
    }
}
