/*
 * Copyright 2023-2025 the original author or authors.
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

public class LongPropertyValidator<T>
        extends BaseComparablePropertyValidator<T, Long, LongPropertyValidator<T>> {

    LongPropertyValidator(Function<T, Long> getter) {
        super(getter);
    }

    // ================================
    // #region - greater than
    // ================================

    public LongPropertyValidator<T> gt(long min) {
        return gt(min, String.format("The value should be greater than %d", min));
    }

    public LongPropertyValidator<T> gt(long min, String errMsg) {
        return gt(min, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> LongPropertyValidator<T> gt(
            long min, Supplier<E> exceptionCreator) {
        return gt(min, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> LongPropertyValidator<T> gt(
            long min, Function<Long, E> exceptionCreator) {
        withRule(value -> (value != null && value > min), exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - greater than
    // ================================

    // ================================
    // #region - greater than or equal to
    // ================================

    public LongPropertyValidator<T> ge(long min) {
        return ge(min, String.format("The value should be greater than or equal to %d", min));
    }

    public LongPropertyValidator<T> ge(long min, String errMsg) {
        return ge(min, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> LongPropertyValidator<T> ge(
            long min, Supplier<E> exceptionCreator) {
        return ge(min, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> LongPropertyValidator<T> ge(
            long min, Function<Long, E> exceptionCreator) {
        withRule(value -> (value != null && value >= min), exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - greater than or equal to
    // ================================

    // ================================
    // #region - less than
    // ================================

    public LongPropertyValidator<T> lt(long max) {
        return lt(max, String.format("The value should be less than %d", max));
    }

    public LongPropertyValidator<T> lt(long max, String errMsg) {
        return lt(max, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> LongPropertyValidator<T> lt(
            long max, Supplier<E> exceptionCreator) {
        return lt(max, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> LongPropertyValidator<T> lt(
            long max, Function<Long, E> exceptionCreator) {
        withRule(value -> (value != null && value < max), exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - less than
    // ================================

    // ================================
    // #region - less than or equal to
    // ================================

    public LongPropertyValidator<T> le(long max) {
        return le(max, String.format("The value should be less than or equal to %d", max));
    }

    public LongPropertyValidator<T> le(long max, String errMsg) {
        return le(max, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> LongPropertyValidator<T> le(
            long max, Supplier<E> exceptionCreator) {
        return le(max, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> LongPropertyValidator<T> le(
            long max, Function<Long, E> exceptionCreator) {
        withRule(value -> (value != null && value <= max), exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - less than or equal to
    // ================================

    @Override
    protected LongPropertyValidator<T> thisObject() {
        return this;
    }
}
