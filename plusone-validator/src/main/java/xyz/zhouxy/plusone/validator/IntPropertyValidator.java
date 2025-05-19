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

public class IntPropertyValidator<DTO> extends ComparablePropertyValidator<DTO, Integer, IntPropertyValidator<DTO>> {

    IntPropertyValidator(Function<DTO, Integer> getter) {
        super(getter);
    }

    public IntPropertyValidator<DTO> between(int min, int max) {
        return between(min, max, String.format("数值不在 %d 和 %d 之间", min, max));
    }

    public IntPropertyValidator<DTO> between(int min, int max, String errMsg) {
        return between(min, max, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> IntPropertyValidator<DTO> between(int min, int max,
            Supplier<E> exceptionCreator) {
        return between(min, max, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> IntPropertyValidator<DTO> between(int min, int max,
            Function<Integer, E> exceptionCreator) {
        withRule(value -> (value >= min && value < max), exceptionCreator);
        return this;
    }

    @Override
    protected IntPropertyValidator<DTO> thisObject() {
        return this;
    }
}
