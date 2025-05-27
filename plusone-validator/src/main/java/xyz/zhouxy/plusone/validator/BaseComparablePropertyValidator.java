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

package xyz.zhouxy.plusone.validator;

import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.collect.Range;

public abstract class BaseComparablePropertyValidator<T, TProperty extends Comparable<TProperty>, TPropertyValidator extends BaseComparablePropertyValidator<T, TProperty, TPropertyValidator>>
        extends BasePropertyValidator<T, TProperty, TPropertyValidator> {

    BaseComparablePropertyValidator(Function<T, ? extends TProperty> getter) {
        super(getter);
    }

    public TPropertyValidator inRange(Range<TProperty> range) {
        withRule(value -> value != null && range.contains(value),
                convertExceptionCreator("The value is not in the interval " + range.toString()));
        return thisObject();
    }

    public TPropertyValidator inRange(Range<TProperty> range, String errMsg) {
        withRule(value -> value != null && range.contains(value), convertExceptionCreator(errMsg));
        return thisObject();
    }

    public <E extends RuntimeException> TPropertyValidator inRange(
            Range<TProperty> range,
            Supplier<E> exceptionCreator) {
        withRule(value -> value != null && range.contains(value), exceptionCreator);
        return thisObject();
    }

    public <E extends RuntimeException> TPropertyValidator inRange(
            Range<TProperty> range,
            Function<TProperty, E> exceptionCreator) {
        withRule(value -> value != null && range.contains(value), exceptionCreator);
        return thisObject();
    }

    static <V> Function<V, IllegalArgumentException> convertExceptionCreator(String errMsg) {
        return value -> new IllegalArgumentException(errMsg);
    }

    static <V, E extends RuntimeException> Function<V, E> convertExceptionCreator(Supplier<E> exceptionSupplier) {
        return value -> exceptionSupplier.get();
    }
}
