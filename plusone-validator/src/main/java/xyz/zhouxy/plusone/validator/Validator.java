/*
 * Copyright 2022-2025 the original author or authors.
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

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 校验器
 *
 * <p>
 * 可以使用以下方式初始化一个校验器：
 * </p>
 *
 * <pre>
 * var validator = new Validator&lt;Integer&gt;()
 *         .addRule(value -> Objects.nonNull(value), "value 不能为空")
 *         .addRule(value -> (value >= 0 && value <= 500), "value 应在 [0, 500] 内");
 * </pre>
 *
 * <p>
 * 然后通过校验器的 {@link #validate} 方法对指定对象进行校验。
 * </p>
 *
 * <pre>
 * validator.validate(666);
 * </pre>
 * </p>
 *
 * @author ZhouXY
 * @see IValidateRequired
 * @see BaseValidator
 */
public final class Validator<T> extends BaseValidator<T> {
    public final Validator<T> addRule(final Predicate<? super T> rule, final String errorMessage) {
        withRule(rule, errorMessage);
        return this;
    }

    public final <E extends RuntimeException> Validator<T> addRule(Predicate<? super T> rule, Supplier<E> exceptionCreator) {
        withRule(rule, exceptionCreator);
        return this;
    }

    public final <E extends RuntimeException> Validator<T> addRule(Predicate<? super T> rule, Function<T, E> exceptionCreator) {
        withRule(rule, exceptionCreator);
        return this;
    }

    public final Validator<T> addRule(Consumer<? super T> rule) {
        withRule(rule);
        return this;
    }
}
