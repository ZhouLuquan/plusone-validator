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

public class DoublePropertyValidator<DTO>
        extends BaseComparablePropertyValidator<DTO, Double, DoublePropertyValidator<DTO>> {

    DoublePropertyValidator(Function<DTO, Double> getter) {
        super(getter);
    }

    // ================================
    // #region - greater than
    // ================================

    public DoublePropertyValidator<DTO> gt(double min) {
        return gt(min, String.format("The value should be greater than %s", min));
    }

    public DoublePropertyValidator<DTO> gt(double min, String errMsg) {
        return gt(min, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> DoublePropertyValidator<DTO> gt(
            double min, Supplier<E> exceptionCreator) {
        return gt(min, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> DoublePropertyValidator<DTO> gt(
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

    public DoublePropertyValidator<DTO> ge(double min) {
        return ge(min, String.format("The value should be greater than or equal to %s", min));
    }

    public DoublePropertyValidator<DTO> ge(double min, String errMsg) {
        return ge(min, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> DoublePropertyValidator<DTO> ge(
            double min, Supplier<E> exceptionCreator) {
        return ge(min, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> DoublePropertyValidator<DTO> ge(
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

    public DoublePropertyValidator<DTO> lt(double max) {
        return lt(max, String.format("The value should be less than %s", max));
    }

    public DoublePropertyValidator<DTO> lt(double max, String errMsg) {
        return lt(max, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> DoublePropertyValidator<DTO> lt(
            double max, Supplier<E> exceptionCreator) {
        return lt(max, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> DoublePropertyValidator<DTO> lt(
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

    public DoublePropertyValidator<DTO> le(double max) {
        return le(max, String.format("The value should be less than or equal to %s", max));
    }

    public DoublePropertyValidator<DTO> le(double max, String errMsg) {
        return le(max, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> DoublePropertyValidator<DTO> le(
            double max, Supplier<E> exceptionCreator) {
        return le(max, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> DoublePropertyValidator<DTO> le(
            double max, Function<Double, E> exceptionCreator) {
        withRule(value -> (value != null && value <= max), exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - less than or equal to
    // ================================

    @Override
    protected DoublePropertyValidator<DTO> thisObject() {
        return this;
    }
}
