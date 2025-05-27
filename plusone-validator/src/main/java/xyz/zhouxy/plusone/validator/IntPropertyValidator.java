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

public class IntPropertyValidator<T>
        extends BaseComparablePropertyValidator<T, Integer, IntPropertyValidator<T>> {

    IntPropertyValidator(Function<T, Integer> getter) {
        super(getter);
    }

    // ================================
    // #region - greater than
    // ================================

    public IntPropertyValidator<T> gt(int min) {
        return gt(min, () -> new IllegalArgumentException(
                String.format("The input must be greater than '%d'.", min)));
    }

    public IntPropertyValidator<T> gt(int min, String errMsg) {
        return gt(min, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> IntPropertyValidator<T> gt(
            int min, Supplier<E> exceptionCreator) {
        return gt(min, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> IntPropertyValidator<T> gt(
            int min, Function<Integer, E> exceptionCreator) {
        withRule(value -> (value != null && value > min), exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - greater than
    // ================================

    // ================================
    // #region - greater than or equal to
    // ================================

    public IntPropertyValidator<T> ge(int min) {
        return ge(min, () -> new IllegalArgumentException(
                String.format("The input must be greater than or equal to '%d'.", min)));
    }

    public IntPropertyValidator<T> ge(int min, String errMsg) {
        return ge(min, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> IntPropertyValidator<T> ge(
            int min, Supplier<E> exceptionCreator) {
        return ge(min, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> IntPropertyValidator<T> ge(
            int min, Function<Integer, E> exceptionCreator) {
        withRule(value -> (value != null && value >= min), exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - greater than or equal to
    // ================================

    // ================================
    // #region - less than
    // ================================

    public IntPropertyValidator<T> lt(int max) {
        return lt(max, () -> new IllegalArgumentException(
                String.format("The input must be less than '%d'.", max)));
    }

    public IntPropertyValidator<T> lt(int max, String errMsg) {
        return lt(max, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> IntPropertyValidator<T> lt(
            int max, Supplier<E> exceptionCreator) {
        return lt(max, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> IntPropertyValidator<T> lt(
            int max, Function<Integer, E> exceptionCreator) {
        withRule(value -> (value != null && value < max), exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - less than
    // ================================

    // ================================
    // #region - less than or equal to
    // ================================

    public IntPropertyValidator<T> le(int max) {
        return le(max, () -> new IllegalArgumentException(
                String.format("The input must be less than or equal to '%d'.", max)));
    }

    public IntPropertyValidator<T> le(int max, String errMsg) {
        return le(max, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> IntPropertyValidator<T> le(
            int max, Supplier<E> exceptionCreator) {
        return le(max, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> IntPropertyValidator<T> le(
            int max, Function<Integer, E> exceptionCreator) {
        withRule(value -> (value != null && value <= max), exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - less than or equal to
    // ================================

    @Override
    protected IntPropertyValidator<T> thisObject() {
        return this;
    }
}
