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
public abstract class BaseComparablePropertyValidator<T, TProperty extends Comparable<TProperty>, TPropertyValidator extends BaseComparablePropertyValidator<T, TProperty, TPropertyValidator>>
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
    public TPropertyValidator inRange(Range<TProperty> range) {
        withRule(value -> value != null && range.contains(value), value -> ValidationException.withMessage(
                "The input must in the interval %s. You entered %s.", range, value));
        return thisObject();
    }

    /**
     * 添加一条校验属性的规则，校验属性是否在给定的区间之内
     *
     * @param range 区间
     * @param errMsg 错误信息
     * @return 属性校验器
     */
    public TPropertyValidator inRange(Range<TProperty> range, String errMsg) {
        withRule(value -> value != null && range.contains(value), convertToExceptionFunction(errMsg));
        return thisObject();
    }

    /**
     * 添加一条校验属性的规则，校验属性是否在给定的区间之内
     *
     * @param range 区间
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> TPropertyValidator inRange(
            Range<TProperty> range, Supplier<E> e) {
        withRule(value -> value != null && range.contains(value), convertToExceptionFunction(e));
        return thisObject();
    }

    /**
     * 添加一条校验属性的规则，校验属性是否在给定的区间之内
     *
     * @param range 区间
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> TPropertyValidator inRange(
            Range<TProperty> range, Function<TProperty, E> e) {
        withRule(value -> value != null && range.contains(value), e);
        return thisObject();
    }
}
