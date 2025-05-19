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

public class LongPropertyValidator<DTO> extends ComparablePropertyValidator<DTO, Long, LongPropertyValidator<DTO>> {

    LongPropertyValidator(Function<DTO, Long> getter) {
        super(getter);
    }

    public LongPropertyValidator<DTO> between(long min, long max) {
        return between(min, max, String.format("数值不在 %d 和 %d 之间", min, max));
    }

    public LongPropertyValidator<DTO> between(long min, long max, String errMsg) {
        return between(min, max, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> LongPropertyValidator<DTO> between(long min, long max,
            Supplier<E> exceptionCreator) {
        return between(min, max, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> LongPropertyValidator<DTO> between(long min, long max,
            Function<Long, E> exceptionCreator) {
        withRule(value -> (value >= min && value < max), exceptionCreator);
        return this;
    }

    @Override
    protected LongPropertyValidator<DTO> thisObject() {
        return this;
    }
}
