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

/**
 * 浮点数属性校验器
 *
 * <p>
 * 内置对 {@code Double} 类型常用的校验规则。
 *
 * @author ZhouXY
 */
public class DoublePropertyValidator<T>
        extends BaseComparablePropertyValidator<T, Double, DoublePropertyValidator<T>> {

    DoublePropertyValidator(Function<T, Double> getter) {
        super(getter);
    }

    // ================================
    // #region - greater than
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否大于给定值
     *
     * @param min 最小值
     * @return 属性校验器
     */
    public DoublePropertyValidator<T> gt(double min) {
        return gt(min, convertToExceptionFunction("The input must be greater than '%s'.", min));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否大于给定值
     *
     * @param min 最小值
     * @param errMsg 错误信息
     * @return 属性校验器
     */
    public DoublePropertyValidator<T> gt(double min, String errMsg) {
        return gt(min, convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否大于给定值
     *
     * @param <E> 异常类型
     * @param min 最小值
     * @param e 错误信息
     * @return 属性校验器
     */
    public <E extends RuntimeException> DoublePropertyValidator<T> gt(double min, Supplier<E> e) {
        return gt(min, convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否大于给定值
     *
     * @param <E> 异常类型
     * @param min 最小值
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> DoublePropertyValidator<T> gt(double min, Function<Double, E> e) {
        return withRule(value -> (value != null && value > min), e);
    }

    // ================================
    // #endregion - greater than
    // ================================

    // ================================
    // #region - greater than or equal to
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否大于等于给定值
     *
     * @param min 最小值
     * @return 属性校验器
     */
    public DoublePropertyValidator<T> ge(double min) {
        return ge(min, convertToExceptionFunction("The input must be greater than or equal to '%s'.", min));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否大于等于给定值
     *
     * @param min 最小值
     * @param errMsg 错误信息
     * @return 属性校验器
     */
    public DoublePropertyValidator<T> ge(double min, String errMsg) {
        return ge(min, convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否大于等于给定值
     *
     * @param <E> 异常类型
     * @param min 最小值
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> DoublePropertyValidator<T> ge(double min, Supplier<E> e) {
        return ge(min, convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否大于等于给定值
     *
     * @param <E> 异常类型
     * @param min 最小值
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> DoublePropertyValidator<T> ge(double min, Function<Double, E> e) {
        return withRule(value -> (value != null && value >= min), e);
    }

    // ================================
    // #endregion - greater than or equal to
    // ================================

    // ================================
    // #region - less than
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否小于给定值
     *
     * @param max 最大值
     * @return 属性校验器
     */
    public DoublePropertyValidator<T> lt(double max) {
        return lt(max, convertToExceptionFunction("The input must be less than '%s'.", max));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否小于给定值
     *
     * @param max 最大值
     * @param errMsg 错误信息
     * @return 属性校验器
     */
    public DoublePropertyValidator<T> lt(double max, String errMsg) {
        return lt(max, convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否小于给定值
     *
     * @param <E> 异常类型
     * @param max 最大值
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> DoublePropertyValidator<T> lt(double max, Supplier<E> e) {
        return lt(max, convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否小于给定值
     *
     * @param <E> 异常类型
     * @param max 最大值
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> DoublePropertyValidator<T> lt(double max, Function<Double, E> e) {
        return withRule(value -> (value != null && value < max), e);
    }

    // ================================
    // #endregion - less than
    // ================================

    // ================================
    // #region - less than or equal to
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否小于等于给定值
     *
     * @param max 最大值
     * @return 属性校验器
     */
    public DoublePropertyValidator<T> le(double max) {
        return le(max, convertToExceptionFunction("The input must be less than or equal to '%s'.", max));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否小于等于给定值
     *
     * @param max 最大值
     * @param errMsg 错误信息
     * @return 属性校验器
     */
    public DoublePropertyValidator<T> le(double max, String errMsg) {
        return le(max, convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否小于等于给定值
     *
     * @param <E> 异常类型
     * @param max 最大值
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> DoublePropertyValidator<T> le(double max, Supplier<E> e) {
        return le(max, convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否小于等于给定值
     *
     * @param <E> 异常类型
     * @param max 最大值
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> DoublePropertyValidator<T> le(double max, Function<Double, E> e) {
        return withRule(value -> (value != null && value <= max), e);
    }

    // ================================
    // #endregion - less than or equal to
    // ================================

    @Override
    protected DoublePropertyValidator<T> thisObject() {
        return this;
    }
}
