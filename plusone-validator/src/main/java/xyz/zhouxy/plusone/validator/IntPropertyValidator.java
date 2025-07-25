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
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * {@code Integer} 类型属性的校验器
 *
 * <p>
 * 用于构建校验 {@code Integer} 类型属性的规则链。
 *
 * @param <T> 待校验对象的类型
 * @author ZhouXY108 <luquanlion@outlook.com>
 */
public class IntPropertyValidator<T>
        extends BaseComparablePropertyValidator<T, Integer, IntPropertyValidator<T>> {

    IntPropertyValidator(Function<T, Integer> getter) {
        super(getter);
    }

    // ================================
    // #region - greater than
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否大于给定值
     *
     * @param min 最小值
     * @return 当前验证器实例，用于链式调用
     */
    public final IntPropertyValidator<T> gt(final int min) {
        return withRule(Conditions.greaterThan(min),
                "The input must be greater than '%d'.", min);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否大于给定值
     *
     * @param min 最小值
     * @param errorMessage 异常信息
     * @return 当前验证器实例，用于链式调用
     */
    public final IntPropertyValidator<T> gt(
            final int min, final String errorMessage) {
        return withRule(Conditions.greaterThan(min), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否大于给定值
     *
     * @param <X> 自定义异常类型
     * @param min 最小值
     * @param exceptionSupplier 自定义异常
     * @return 当前验证器实例，用于链式调用
     */
    public final <X extends RuntimeException> IntPropertyValidator<T> gt(
            final int min, final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.greaterThan(min), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否大于给定值
     *
     * @param <X> 自定义异常类型
     * @param min 最小值
     * @param exceptionFunction 自定义异常
     * @return 当前验证器实例，用于链式调用
     */
    public final <X extends RuntimeException> IntPropertyValidator<T> gt(
            final int min, final Function<Integer, X> exceptionFunction) {
        return withRule(Conditions.greaterThan(min), exceptionFunction);
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
     * @return 当前验证器实例，用于链式调用
     */
    public final IntPropertyValidator<T> ge(final int min) {
        return withRule(Conditions.greaterThanOrEqualTo(min),
                "The input must be greater than or equal to '%d'.", min);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否大于等于给定值
     *
     * @param min 最小值
     * @param errorMessage 异常信息
     * @return 当前验证器实例，用于链式调用
     */
    public final IntPropertyValidator<T> ge(final int min, final String errorMessage) {
        return withRule(Conditions.greaterThanOrEqualTo(min), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否大于等于给定值
     *
     * @param <X> 自定义异常类型
     * @param min 最小值
     * @param exceptionSupplier 自定义异常
     * @return 当前验证器实例，用于链式调用
     */
    public final <X extends RuntimeException> IntPropertyValidator<T> ge(
            final int min, final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.greaterThanOrEqualTo(min), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否大于等于给定值
     *
     * @param <X> 自定义异常类型
     * @param min 最小值
     * @param exceptionFunction 自定义异常
     * @return 当前验证器实例，用于链式调用
     */
    public final <X extends RuntimeException> IntPropertyValidator<T> ge(
            final int min, final Function<Integer, X> exceptionFunction) {
        return withRule(Conditions.greaterThanOrEqualTo(min), exceptionFunction);
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
     * @return 当前验证器实例，用于链式调用
     */
    public final IntPropertyValidator<T> lt(final int max) {
        return withRule(Conditions.lessThan(max),
                "The input must be less than '%d'.", max);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否小于给定值
     *
     * @param max 最大值
     * @param errorMessage 异常信息
     * @return 当前验证器实例，用于链式调用
     */
    public final IntPropertyValidator<T> lt(
            final int max, final String errorMessage) {
        return withRule(Conditions.lessThan(max), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否小于给定值
     *
     * @param <X> 自定义异常类型
     * @param max 最大值
     * @param exceptionSupplier 自定义异常
     * @return 当前验证器实例，用于链式调用
     */
    public final <X extends RuntimeException> IntPropertyValidator<T> lt(
            final int max, final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.lessThan(max), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否小于给定值
     *
     * @param <X> 自定义异常类型
     * @param max 最大值
     * @param exceptionFunction 自定义异常
     * @return 当前验证器实例，用于链式调用
     */
    public final <X extends RuntimeException> IntPropertyValidator<T> lt(
            final int max, final Function<Integer, X> exceptionFunction) {
        return withRule(Conditions.lessThan(max), exceptionFunction);
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
     * @return 当前验证器实例，用于链式调用
     */
    public final IntPropertyValidator<T> le(final int max) {
        return withRule(Conditions.lessThanOrEqualTo(max),
                "The input must be less than or equal to '%d'.", max);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否小于等于给定值
     *
     * @param max 最大值
     * @param errorMessage 异常信息
     * @return 当前验证器实例，用于链式调用
     */
    public final IntPropertyValidator<T> le(
            final int max, final String errorMessage) {
        return withRule(Conditions.lessThanOrEqualTo(max), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否小于等于给定值
     *
     * @param <X> 自定义异常类型
     * @param max 最大值
     * @param exceptionSupplier 自定义异常
     * @return 当前验证器实例，用于链式调用
     */
    public final <X extends RuntimeException> IntPropertyValidator<T> le(
            final int max, final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.lessThanOrEqualTo(max), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否小于等于给定值
     *
     * @param <X> 自定义异常类型
     * @param max 最大值
     * @param exceptionFunction 自定义异常
     * @return 当前验证器实例，用于链式调用
     */
    public final <X extends RuntimeException> IntPropertyValidator<T> le(
            final int max, final Function<Integer, X> exceptionFunction) {
        return withRule(Conditions.lessThanOrEqualTo(max), exceptionFunction);
    }

    // ================================
    // #endregion - less than or equal to
    // ================================

    @Override
    protected IntPropertyValidator<T> thisObject() {
        return this;
    }

    private static class Conditions {

        private static Predicate<Integer> greaterThan(int min) {
            return input -> input == null || input > min;
        }

        private static Predicate<Integer> greaterThanOrEqualTo(int min) {
            return input -> input == null || input >= min;
        }

        private static Predicate<Integer> lessThan(int max) {
            return input -> input == null || input < max;
        }

        private static Predicate<Integer> lessThanOrEqualTo(int max) {
            return input -> input == null || input <= max;
        }
    }
}
