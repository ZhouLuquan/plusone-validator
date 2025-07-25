/*
 * Copyright 2024-2025 the original author or authors.
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

package xyz.zhouxy.plusone.map.validator;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;

import xyz.zhouxy.plusone.ExampleException;
import xyz.zhouxy.plusone.example.Foo;
import xyz.zhouxy.plusone.validator.MapValidator;
import xyz.zhouxy.plusone.validator.ValidationException;

class MapValidatorTests {

    private static final MapValidator<String, Object> validator = ParamsValidator.INSTANCE;

    @Test
    void testValidateAndCopy() {
        Map<String, Object> params = new HashMap<>();
        params.put(ParamsValidator.BOOL_PROPERTY, true);
        params.put(ParamsValidator.INT_PROPERTY, Integer.MAX_VALUE);
        params.put(ParamsValidator.LONG_PROPERTY, Long.MAX_VALUE);
        params.put(ParamsValidator.DOUBLE_PROPERTY, Double.MAX_VALUE);
        params.put(ParamsValidator.STRING_PROPERTY, "Foo");
        params.put(ParamsValidator.STRING_PROPERTY2, "Bar");
        params.put(ParamsValidator.DATE_TIME_PROPERTY, LocalDateTime.of(2008, 8, 8, 20, 8));
        params.put(ParamsValidator.OBJECT_PROPERTY, new Foo(1, "Foo"));
        params.put(ParamsValidator.STRING_LIST_PROPERTY, Collections.emptyList());

        ValidationException e = assertThrows(ValidationException.class, () -> {
            validator.validateAndCopy(params);
        });
        assertEquals("'stringProperty' must be equal to 'stringProperty2'.", e.getMessage());

        params.put(ParamsValidator.STRING_PROPERTY2, "Foo");
        assertAll(() -> {
            Map<String, Object> validatedParams = validator.validateAndCopy(params);
            assertEquals(ImmutableSet.copyOf(ParamsValidator.reservedProperties()), validatedParams.keySet());

            assertEquals(true, validatedParams.get(ParamsValidator.BOOL_PROPERTY));
            assertEquals(Integer.MAX_VALUE, validatedParams.get(ParamsValidator.INT_PROPERTY));
            assertEquals(Long.MAX_VALUE, validatedParams.get(ParamsValidator.LONG_PROPERTY));
            assertEquals(Double.MAX_VALUE, validatedParams.get(ParamsValidator.DOUBLE_PROPERTY));
            assertEquals("Foo", validatedParams.get(ParamsValidator.STRING_PROPERTY));
            assertEquals(LocalDateTime.of(2008, 8, 8, 20, 8), validatedParams.get(ParamsValidator.DATE_TIME_PROPERTY));
            assertEquals(new Foo(1, "Foo"), validatedParams.get(ParamsValidator.OBJECT_PROPERTY));
            assertEquals(Collections.emptyList(), validatedParams.get(ParamsValidator.STRING_LIST_PROPERTY));
        });

        assertAll(() -> {
            Map<String, Object> validatedParams = validator.validateAndCopy(params,
                    ParamsValidator.LONG_PROPERTY, ParamsValidator.STRING_PROPERTY2);
            assertEquals(ImmutableSet.of(ParamsValidator.LONG_PROPERTY, ParamsValidator.STRING_PROPERTY2),
                    validatedParams.keySet());

            assertEquals(Long.MAX_VALUE, validatedParams.get(ParamsValidator.LONG_PROPERTY));
            assertEquals("Foo", validatedParams.get(ParamsValidator.STRING_PROPERTY2));

        });

        assertAll(() -> {
            Set<String> keySet = ImmutableSet.of(ParamsValidator.LONG_PROPERTY, ParamsValidator.STRING_PROPERTY2);
            Map<String, Object> validatedParams = validator.validateAndCopy(params, keySet);
            assertEquals(keySet, validatedParams.keySet());

            assertEquals(Long.MAX_VALUE, validatedParams.get(ParamsValidator.LONG_PROPERTY));
            assertEquals("Foo", validatedParams.get(ParamsValidator.STRING_PROPERTY2));
        });
    }
}

class ParamsValidator extends MapValidator<String, Object> {
    public static final String BOOL_PROPERTY = "boolProperty";
    public static final String INT_PROPERTY = "intProperty";
    public static final String LONG_PROPERTY = "longProperty";
    public static final String DOUBLE_PROPERTY = "doubleProperty";
    public static final String STRING_PROPERTY = "stringProperty";
    public static final String STRING_PROPERTY2 = "stringProperty2";
    public static final String DATE_TIME_PROPERTY = "dateTimeProperty";
    public static final String OBJECT_PROPERTY = "objectProperty";
    public static final String STRING_LIST_PROPERTY = "stringListProperty";

    private static final String[] RESERVED_PROPERTIES = {
            BOOL_PROPERTY, INT_PROPERTY, LONG_PROPERTY, DOUBLE_PROPERTY, STRING_PROPERTY,
            DATE_TIME_PROPERTY, OBJECT_PROPERTY, STRING_LIST_PROPERTY };

    public static final ParamsValidator INSTANCE = new ParamsValidator();

    private ParamsValidator() {
        super(RESERVED_PROPERTIES);
        ruleForBool(BOOL_PROPERTY)
                .notNull();
        ruleForInt(INT_PROPERTY)
                .notNull("The intProperty cannot be null");
        ruleForLong(LONG_PROPERTY)
                .notNull(() -> ExampleException.withMessage("The longProperty cannot be null"));
        ruleForDouble(DOUBLE_PROPERTY)
                .notNull(d -> ExampleException.withMessage("The doubleProperty cannot be null, but it was %s", d));
        ruleForString(STRING_PROPERTY)
                .notNull();
        this.<LocalDateTime>ruleForComparable(DATE_TIME_PROPERTY)
                .notNull("The dateTimeProperty cannot be null");
        ruleFor(OBJECT_PROPERTY)
                .notNull(() -> ExampleException.withMessage("The objectProperty cannot be null"));
        this.<String>ruleForCollection(STRING_LIST_PROPERTY)
                .notNull(d -> ExampleException.withMessage("The stringListProperty cannot be null, but it was %s", d));

        this.<String, String>ruleForPair(STRING_PROPERTY, STRING_PROPERTY2)
                .must((str1, str2) -> str1 != null && str1.equals(str2),
                        "'stringProperty' must be equal to 'stringProperty2'.");
    }

    public static String[] reservedProperties() {
        return Arrays.copyOf(RESERVED_PROPERTIES, RESERVED_PROPERTIES.length);
    }
}
