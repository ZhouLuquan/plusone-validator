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

public class BoolPropertyValidator<DTO> extends BasePropertyValidator<DTO, Boolean, BoolPropertyValidator<DTO>> {

    BoolPropertyValidator(Function<DTO, Boolean> getter) {
        super(getter);
    }

    // ====== isTrueValue ======

    public BoolPropertyValidator<DTO> isTrueValue() {
        return isTrueValue("The value must be true.");
    }

    public BoolPropertyValidator<DTO> isTrueValue(String errMsg) {
        return isTrueValue(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> BoolPropertyValidator<DTO> isTrueValue(
            Supplier<E> exceptionCreator) {
        return isTrueValue(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> BoolPropertyValidator<DTO> isTrueValue(
            Function<Boolean, E> exceptionCreator) {
        withRule(Boolean.TRUE::equals, exceptionCreator);
        return this;
    }

    // ====== isFalseValue ======

    public BoolPropertyValidator<DTO> isFalseValue() {
        return isFalseValue("The value must be false.");
    }

    public BoolPropertyValidator<DTO> isFalseValue(String errMsg) {
        return isFalseValue(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> BoolPropertyValidator<DTO> isFalseValue(
            Supplier<E> exceptionCreator) {
        return isFalseValue(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> BoolPropertyValidator<DTO> isFalseValue(
            Function<Boolean, E> exceptionCreator) {
        withRule(Boolean.FALSE::equals, exceptionCreator);
        return this;
    }

    @Override
    protected BoolPropertyValidator<DTO> thisObject() {
        return this;
    }
}
