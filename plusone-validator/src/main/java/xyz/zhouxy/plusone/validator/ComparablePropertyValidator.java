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

public abstract
class ComparablePropertyValidator<TObj,
                                  TProperty extends Comparable<TProperty>,
                                  TPropertyValidator extends ComparablePropertyValidator<TObj, TProperty, TPropertyValidator>>
    extends BasePropertyValidator<TObj, TProperty, TPropertyValidator> {

    ComparablePropertyValidator(Function<TObj, ? extends TProperty> getter) {
        super(getter);
    }

    public TPropertyValidator between(Range<TProperty> range) {
        withRule(range::contains, convertExceptionCreator("The value is not in " + range.toString()));
        return thisObject();
    }

    public TPropertyValidator between(Range<TProperty> range, String errMsg) {
        withRule(range::contains, convertExceptionCreator(errMsg));
        return thisObject();
    }

    public <E extends RuntimeException> TPropertyValidator between(
            Range<TProperty> range,
            Supplier<E> exceptionCreator) {
        withRule(range::contains, exceptionCreator);
        return thisObject();
    }

    public <E extends RuntimeException> TPropertyValidator between(
            Range<TProperty> range,
            Function<TProperty, E> exceptionCreator) {
        withRule(range::contains, exceptionCreator);
        return thisObject();
    }

    static <V> Function<V, IllegalArgumentException> convertExceptionCreator(String errMsg) {
        return value -> new IllegalArgumentException(errMsg);
    }

    static <V, E extends RuntimeException> Function<V, E> convertExceptionCreator(Supplier<E> exceptionSupplier) {
        return value -> exceptionSupplier.get();
    }
}
