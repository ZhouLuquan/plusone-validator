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
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.google.common.collect.Range;

/**
 * 针对 {@code Comparable} 类型的属性校验器基类
 *
 * <p>
 * 内置了判断属性是否在给定区间内的校验规则。
 *
 * @param <T> 待校验对象类型
 * @param <TProperty> 属性类型
 * @param <TPropertyValidator> 当前属性校验器类型，用于链式调用
 * @see Range
 * @author ZhouXY
 */
public abstract class BaseComparablePropertyValidator<
            T,
            TProperty extends Comparable<TProperty>,
            TPropertyValidator extends BaseComparablePropertyValidator<T, TProperty, TPropertyValidator>>
        extends BasePropertyValidator<T, TProperty, TPropertyValidator> {

    BaseComparablePropertyValidator(Function<T, ? extends TProperty> getter) {
        super(getter);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否在给定的区间之内
     *
     * @param range 区间
     * @return 属性校验器
     */
    public final TPropertyValidator inRange(final Range<TProperty> range) {
        return withRule(Conditions.inRange(range), value -> ValidationException.withMessage(
                "The input must in the interval %s. You entered %s.", range, value));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否在给定的区间之内
     *
     * @param range 区间
     * @param errorMessage 异常信息
     * @return 属性校验器
     */
    public final TPropertyValidator inRange(
            final Range<TProperty> range, final String errorMessage) {
        return withRule(Conditions.inRange(range), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否在给定的区间之内
     *
     * @param range 区间
     * @param exceptionSupplier 自定义异常
     * @return 属性校验器
     */
    public final <X extends RuntimeException> TPropertyValidator inRange(
            final Range<TProperty> range, final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.inRange(range), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否在给定的区间之内
     *
     * @param range 区间
     * @param exceptionFunction 自定义异常
     * @return 属性校验器
     */
    public final <X extends RuntimeException> TPropertyValidator inRange(
            final Range<TProperty> range, final Function<TProperty, X> exceptionFunction) {
        return withRule(Conditions.inRange(range), exceptionFunction);
    }

    private static class Conditions {
        private static <TProperty extends Comparable<TProperty>> Predicate<TProperty> inRange(
                final Range<TProperty> range) {
            return value -> value == null || range.contains(value);
        }
    }
}
