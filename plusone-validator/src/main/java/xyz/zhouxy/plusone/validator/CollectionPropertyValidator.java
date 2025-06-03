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

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import xyz.zhouxy.plusone.commons.collection.CollectionTools;
import xyz.zhouxy.plusone.commons.util.AssertTools;

/**
 * 针对集合类型的属性校验器
 *
 * <p>
 * 内置集合相关的校验规则。
 *
 * @author ZhouXY
 */
public class CollectionPropertyValidator<T, E>
        extends BasePropertyValidator<T, Collection<E>, CollectionPropertyValidator<T, E>> {

    CollectionPropertyValidator(Function<T, Collection<E>> getter) {
        super(getter);
    }

    // ================================
    // #region - notEmpty
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否非空
     *
     * @return 属性校验器
     */
    public final CollectionPropertyValidator<T, E> notEmpty() {
        return withRule(Conditions.notEmpty(), "The input must not be empty.");
    }

    /**
     * 添加一条校验属性的规则，校验属性是否非空
     *
     * @param errorMessage 异常信息
     * @return 属性校验器
     */
    public final CollectionPropertyValidator<T, E> notEmpty(final String errorMessage) {
        return withRule(Conditions.notEmpty(), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否非空
     *
     * @param <X> 自定义异常类型
     * @param exceptionSupplier 自定义异常
     * @return 属性校验器
     */
    public final <X extends RuntimeException> CollectionPropertyValidator<T, E> notEmpty(
            final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.notEmpty(), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否非空
     *
     * @param <X> 自定义异常类型
     * @param exceptionFunction 自定义异常
     * @return 属性校验器
     */
    public final <X extends RuntimeException> CollectionPropertyValidator<T, E> notEmpty(
            final Function<Collection<E>, X> exceptionFunction) {
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
     * @return 属性校验器
     */
    public final CollectionPropertyValidator<T, E> isEmpty() {
        return withRule(Conditions.isEmpty(), "The input must be empty.");
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为空
     *
     * @param errorMessage 异常信息
     * @return 属性校验器
     */
    public final CollectionPropertyValidator<T, E> isEmpty(
            final String errorMessage) {
        return withRule(Conditions.isEmpty(), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为空
     *
     * @param exceptionSupplier 自定义异常
     * @return 属性校验器
     */
    public final <X extends RuntimeException> CollectionPropertyValidator<T, E> isEmpty(
            final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.isEmpty(), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为空
     *
     * @param exceptionFunction 自定义异常
     * @return 属性校验器
     */
    public final <X extends RuntimeException> CollectionPropertyValidator<T, E> isEmpty(
            final Function<Collection<E>, X> exceptionFunction) {
        return withRule(Conditions.isEmpty(), exceptionFunction);
    }

    // ================================
    // #endregion - isEmpty
    // ================================

    // ================================
    // #region - allMatch
    // ================================

    /**
     * 添加一条校验属性的规则，校验是否所有元素都满足条件
     *
     * @param condition 校验条件
     * @return 属性校验器
     */
    public final CollectionPropertyValidator<T, E> allMatch(
            final Predicate<E> condition) {
        return withRule(c -> c.forEach(element -> {
            if (!condition.test(element)) {
                throw ValidationException.withMessage("All elements must match the condition.");
            }
        }));
    }

    /**
     * 添加一条校验属性的规则，校验是否所有元素都满足条件
     *
     * @param condition 校验规则
     * @param errorMessage 异常信息
     * @return 属性校验器
     */
    public final CollectionPropertyValidator<T, E> allMatch(
            final Predicate<E> condition, final String errorMessage) {
        return withRule(c -> c.forEach(element -> {
            if (!condition.test(element)) {
                throw ValidationException.withMessage(errorMessage);
            }
        }));
    }

    /**
     * 添加一条校验属性的规则，校验是否所有元素都满足条件
     *
     * @param condition 校验条件
     * @param exceptionSupplier 自定义异常
     * @return 属性校验器
     */
    public final <X extends RuntimeException> CollectionPropertyValidator<T, E> allMatch(
            final Predicate<E> condition, final Supplier<X> exceptionSupplier) {
        return withRule(c -> c.forEach(element -> {
            if (!condition.test(element)) {
                throw exceptionSupplier.get();
            }
        }));
    }

    /**
     * 添加一条校验属性的规则，校验是否所有元素都满足条件
     *
     * @param condition 校验条件
     * @param exceptionFunction 自定义异常
     * @return 属性校验器
     */
    public final <X extends RuntimeException> CollectionPropertyValidator<T, E> allMatch(
            final Predicate<E> condition, final Function<E, X> exceptionFunction) {
        return withRule(c -> c.forEach(element -> {
            if (!condition.test(element)) {
                throw exceptionFunction.apply(element);
            }
        }));
    }

    // ================================
    // #endregion - allMatch
    // ================================

    // ================================
    // #region - size
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性大小是否等于指定大小
     *
     * @param size 指定大小
     * @param errorMessage 异常信息
     * @return 属性校验器
     */
    public final CollectionPropertyValidator<T, E> size(
            final int size, final String errorMessage) {
        return withRule(Conditions.size(size), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性大小是否等于指定大小
     *
     * @param <X> 异常类型
     * @param size 指定大小
     * @param exceptionSupplier 自定义异常
     * @return 属性校验器
     */
    public final <X extends RuntimeException> CollectionPropertyValidator<T, E> size(
            final int size, final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.size(size), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性大小是否等于指定大小
     *
     * @param <X> 异常类型
     * @param size 指定大小
     * @param exceptionFunction 自定义异常
     * @return 属性校验器
     */
    public final <X extends RuntimeException> CollectionPropertyValidator<T, E> size(
            final int size, final Function<Collection<E>, X> exceptionFunction) {
        return withRule(Conditions.size(size), exceptionFunction);
    }

    /**
     * 添加一条校验属性的规则，校验属性的大小范围
     *
     * @param min 最小大小
     * @param max 最大大小
     * @param errorMessage 异常信息
     * @return 属性校验器
     */
    public final CollectionPropertyValidator<T, E> size(
            final int min, final int max, final String errorMessage) {
        return withRule(Conditions.size(min, max), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性的大小范围
     *
     * @param min 最小大小
     * @param max 最大大小
     * @param exceptionSupplier 自定义异常
     * @return 属性校验器
     */
    public final <X extends RuntimeException> CollectionPropertyValidator<T, E> size(
            final int min, final int max, final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.size(min, max), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性的大小范围
     *
     * @param min 最小大小
     * @param max 最大大小
     * @param exceptionFunction 自定义异常
     * @return 属性校验器
     */
    public final <X extends RuntimeException> CollectionPropertyValidator<T, E> size(
            final int min, final int max, final Function<Collection<E>, X> exceptionFunction) {
        return withRule(Conditions.size(min, max), exceptionFunction);
    }

    // ================================
    // #endregion - size
    // ================================

    private static class Conditions {

        private static Predicate<Collection<?>> isEmpty() {
            return CollectionTools::isEmpty;
        }

        private static Predicate<Collection<?>> notEmpty() {
            return CollectionTools::isNotEmpty;
        }

        private static Predicate<Collection<?>> size(int size) {
            AssertTools.checkArgument(size >= 0,
                "The expected size must be non-negative.");
            return collection -> collection == null || collection.size() == size;
        }

        private static Predicate<Collection<?>> size(int min, int max) {
            AssertTools.checkArgument(min >= 0, "min must be non-negative.");
            AssertTools.checkArgument(min <= max, "min must be less than or equal to max.");
            return collection -> {
                if (collection == null) {
                    return true;
                }
                int size = collection.size();
                return size >= min && size <= max;
            };
        }
    }

    @Override
    protected CollectionPropertyValidator<T, E> thisObject() {
        return this;
    }
}
