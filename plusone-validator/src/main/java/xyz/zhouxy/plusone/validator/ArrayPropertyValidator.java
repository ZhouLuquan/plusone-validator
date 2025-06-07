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

import xyz.zhouxy.plusone.commons.util.ArrayTools;
import xyz.zhouxy.plusone.commons.util.AssertTools;

/**
 * 数组类型属性的校验器
 *
 * <p>
 * 用于构建校验数组类型属性的规则链。
 *
 * @param <T> 待校验对象的类型
 * @param <E> 数组元素的类型
 * @author ZhouXY
 */
public class ArrayPropertyValidator<T, E>
        extends BasePropertyValidator<T, E[], ArrayPropertyValidator<T, E>> {

    ArrayPropertyValidator(Function<T, E[]> getter) {
        super(getter);
    }

    // ================================
    // #region - notEmpty
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否非空
     *
     * @return 当前校验器实例，用于链式调用
     */
    public final ArrayPropertyValidator<T, E> notEmpty() {
        return withRule(Conditions.notEmpty(), "The input must not be empty.");
    }

    /**
     * 添加一条校验属性的规则，校验属性是否非空
     *
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final ArrayPropertyValidator<T, E> notEmpty(
            final String errorMessage) {
        return withRule(Conditions.notEmpty(), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否非空
     *
     * @param <X> 自定义异常类型
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> ArrayPropertyValidator<T, E> notEmpty(
            final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.notEmpty(), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否非空
     *
     * @param <X> 自定义异常类型
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> ArrayPropertyValidator<T, E> notEmpty(
            final Function<E[], X> exceptionFunction) {
        return withRule(Conditions.notEmpty(), exceptionFunction);
    }

    // ================================
    // #endregion - notEmpty
    // ================================

    // ================================
    // #region - isEmpty
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否为空
     *
     * @return 当前校验器实例，用于链式调用
     */
    public final ArrayPropertyValidator<T, E> isEmpty() {
        return withRule(Conditions.isEmpty(), "The input must be empty.");
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为空
     *
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final ArrayPropertyValidator<T, E> isEmpty(
            final String errorMessage) {
        return withRule(Conditions.isEmpty(), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为空
     *
     * @param <X> 自定义异常类型
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> ArrayPropertyValidator<T, E> isEmpty(
            final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.isEmpty(), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为空
     *
     * @param <X> 自定义异常类型
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> ArrayPropertyValidator<T, E> isEmpty(
            final Function<E[], X> exceptionFunction) {
        return withRule(Conditions.isEmpty(), exceptionFunction);
    }

    // ================================
    // #endregion - isEmpty
    // ================================

    // ================================
    // #region - allMatch
    // ================================

    /**
     * 添加一条校验属性的规则，校验是否所有元素都满足指定条件
     *
     * @param condition 校验条件
     * @return 当前校验器实例，用于链式调用
     */
    public final ArrayPropertyValidator<T, E> allMatch(final Predicate<E> condition) {
        return withRule(c -> {
            for (E element : c) {
                if (!condition.test(element)) {
                    throw ValidationException.withMessage("All elements must match the condition.");
                }
            }
        });
    }

    /**
     * 添加一条校验属性的规则，校验是否所有元素都满足指定条件
     *
     * @param condition 校验条件
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final ArrayPropertyValidator<T, E> allMatch(
            final Predicate<E> condition, final String errorMessage) {
        return withRule(c -> {
            for (E element : c) {
                if (!condition.test(element)) {
                    throw ValidationException.withMessage(errorMessage);
                }
            }
        });
    }

    /**
     * 添加一条校验属性的规则，校验是否所有元素都满足指定条件
     *
     * @param <X> 自定义异常类型
     * @param condition 校验条件
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> ArrayPropertyValidator<T, E> allMatch(
            final Predicate<E> condition, final Supplier<X> exceptionSupplier) {
        return withRule(c -> {
            for (E element : c) {
                if (!condition.test(element)) {
                    throw exceptionSupplier.get();
                }
            }
        });
    }

    /**
     * 添加一条校验属性的规则，校验是否所有元素都满足指定条件
     *
     * @param <X> 自定义异常类型
     * @param condition 校验条件
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> ArrayPropertyValidator<T, E> allMatch(
            final Predicate<E> condition, final Function<E, X> exceptionFunction) {
        return withRule(c -> {
            for (E element : c) {
                if (!condition.test(element)) {
                    throw exceptionFunction.apply(element);
                }
            }
        });
    }

    // ================================
    // #endregion - allMatch
    // ================================

    // ================================
    // #region - length
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性长度是否等于指定长度
     *
     * @param length 预期长度
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final ArrayPropertyValidator<T, E> length(
            final int length, final String errorMessage) {
        return withRule(Conditions.length(length), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性长度是否等于指定长度
     *
     * @param <X> 自定义异常类型
     * @param length 预期长度
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> ArrayPropertyValidator<T, E> length(
            final int length, final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.length(length), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性长度是否等于指定长度
     *
     * @param <X> 自定义异常类型
     * @param length 预期长度
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> ArrayPropertyValidator<T, E> length(
            final int length, final Function<E[], X> exceptionFunction) {
        return withRule(Conditions.length(length), exceptionFunction);
    }

    /**
     * 添加一条校验属性的规则，校验属性的长度范围
     *
     * @param min 最小长度（包含）
     * @param max 最大长度（包含）
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final ArrayPropertyValidator<T, E> length(
            final int min, final int max, final String errorMessage) {
        return withRule(Conditions.length(min, max), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性的长度范围
     *
     * @param <X> 自定义异常类型
     * @param min 最小长度（包含）
     * @param max 最大长度（包含）
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> ArrayPropertyValidator<T, E> length(
            final int min, final int max, final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.length(min, max), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性的长度范围
     *
     * @param <X> 自定义异常类型
     * @param min 最小长度（包含）
     * @param max 最大长度（包含）
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> ArrayPropertyValidator<T, E> length(
            final int min, final int max, final Function<E[], X> exceptionFunction) {
        return withRule(Conditions.length(min, max), exceptionFunction);
    }

    // ================================
    // #endregion - length
    // ================================

    private static class Conditions {

        private static <T> Predicate<T[]> isEmpty() {
            return ArrayTools::isEmpty;
        }

        private static <T> Predicate<T[]> notEmpty() {
            return ArrayTools::isNotEmpty;
        }

        private static <T> Predicate<T[]> length(final int length) {
            AssertTools.checkArgument(length >= 0,
                "The expected length must be non-negative.");
            return input -> input == null || input.length == length;
        }

        private static <T> Predicate<T[]> length(final int min, final int max) {
            AssertTools.checkArgument(min >= 0, "min must be non-negative.");
            AssertTools.checkArgument(min <= max, "min must be less than or equal to max.");
            return input -> {
                if (input == null) {
                    return true;
                }
                final int len = input.length;
                return len >= min && len <= max;
            };
        }
    }

    @Override
    protected ArrayPropertyValidator<T, E> thisObject() {
        return this;
    }
}
