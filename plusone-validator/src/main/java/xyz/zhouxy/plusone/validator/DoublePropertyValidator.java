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

public class DoublePropertyValidator<T>
        extends BaseComparablePropertyValidator<T, Double, DoublePropertyValidator<T>> {

    DoublePropertyValidator(Function<T, Double> getter) {
        super(getter);
    }

    // ================================
    // #region - greater than
    // ================================

    public DoublePropertyValidator<T> gt(double min) {
        return gt(min, () -> new IllegalArgumentException(
                String.format("The input must be greater than '%s'.", min)));
    }

    public DoublePropertyValidator<T> gt(double min, String errMsg) {
        return gt(min, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> DoublePropertyValidator<T> gt(
            double min, Supplier<E> exceptionCreator) {
        return gt(min, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> DoublePropertyValidator<T> gt(
            double min, Function<Double, E> exceptionCreator) {
        withRule(value -> (value != null && value > min), exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - greater than
    // ================================

    // ================================
    // #region - greater than or equal to
    // ================================

    public DoublePropertyValidator<T> ge(double min) {
        return ge(min, () -> new IllegalArgumentException(
                String.format("The input must be greater than or equal to '%s'.", min)));
    }

    public DoublePropertyValidator<T> ge(double min, String errMsg) {
        return ge(min, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> DoublePropertyValidator<T> ge(
            double min, Supplier<E> exceptionCreator) {
        return ge(min, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> DoublePropertyValidator<T> ge(
            double min, Function<Double, E> exceptionCreator) {
        withRule(value -> (value != null && value >= min), exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - greater than or equal to
    // ================================

    // ================================
    // #region - less than
    // ================================

    public DoublePropertyValidator<T> lt(double max) {
        return lt(max, () -> new IllegalArgumentException(
                String.format("The input must be less than '%s'.", max)));
    }

    public DoublePropertyValidator<T> lt(double max, String errMsg) {
        return lt(max, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> DoublePropertyValidator<T> lt(
            double max, Supplier<E> exceptionCreator) {
        return lt(max, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> DoublePropertyValidator<T> lt(
            double max, Function<Double, E> exceptionCreator) {
        withRule(value -> (value != null && value < max), exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - less than
    // ================================

    // ================================
    // #region - less than or equal to
    // ================================

    public DoublePropertyValidator<T> le(double max) {
        return le(max, () -> new IllegalArgumentException(
                String.format("The input must be less than or equal to '%s'.", max)));
    }

    public DoublePropertyValidator<T> le(double max, String errMsg) {
        return le(max, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> DoublePropertyValidator<T> le(
            double max, Supplier<E> exceptionCreator) {
        return le(max, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> DoublePropertyValidator<T> le(
            double max, Function<Double, E> exceptionCreator) {
        withRule(value -> (value != null && value <= max), exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - less than or equal to
    // ================================

    @Override
    protected DoublePropertyValidator<T> thisObject() {
        return this;
    }
}
