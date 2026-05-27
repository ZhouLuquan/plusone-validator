/*
 * Copyright 2025-present ZhouXY
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
 * {@code Comparable} 类型属性的校验器的基类
 *
 * @param <T> 待校验对象的类型
 * @param <TProperty> 待校验属性的类型，必须实现 {@code Comparable} 接口
 * @param <TPropertyValidator> 具体校验器类型，用于支持链式调用
 * @see Range
 * @author ZhouXY108 <luquanlion@outlook.com>
 */
public abstract class BaseComparablePropertyValidator<
            T,
            TProperty extends Comparable<? super TProperty>,
            TPropertyValidator extends BaseComparablePropertyValidator<T, TProperty, TPropertyValidator>>
        extends BasePropertyValidator<T, TProperty, TPropertyValidator> {

    BaseComparablePropertyValidator(Function<T, ? extends TProperty> getter) {
        super(getter);
    }

    /**
     * 添加一条校验属性的规则，校验属性的取值范围。
     *
     * @param range 区间
     * @return 当前校验器实例，用于链式调用
     */
    public final TPropertyValidator inRange(final Range<? super TProperty> range) {
        return withRule(Conditions.inRange(range), value -> ValidationException.withMessage(
                "The input must in the interval %s. You entered %s.", range, value));
    }

    /**
     * 添加一条校验属性的规则，校验属性的取值范围。
     *
     * @param range 区间
     * @param errorMessage 自定义错误消息模板
     * @return 当前校验器实例，用于链式调用
     */
    public final TPropertyValidator inRange(
            final Range<? super TProperty> range, final String errorMessage) {
        return withRule(Conditions.inRange(range), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性的取值范围。
     *
     * @param <X> 自定义异常类型
     * @param range 区间
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> TPropertyValidator inRange(
            final Range<? super TProperty> range, final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.inRange(range), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性的取值范围。
     *
     * @param <X> 自定义异常类型
     * @param range 区间
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> TPropertyValidator inRange(
            final Range<? super TProperty> range, final Function<TProperty, X> exceptionFunction) {
        return withRule(Conditions.inRange(range), exceptionFunction);
    }

    /**
     * 校验条件的实现
     */
    private static class Conditions {

        private static <TProperty extends Comparable<? super TProperty>> Predicate<TProperty> inRange(
                final Range<? super TProperty> range) {
            return value -> value == null || range.contains(value);
        }
    }
}
