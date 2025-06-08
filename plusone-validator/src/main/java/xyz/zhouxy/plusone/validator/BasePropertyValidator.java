/*
 * Copyright 2024-2025 the original author or authors.
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

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 属性校验器。包含针对属性的校验规则。
 *
 * <p>
 * 用于构建针对特定属性的校验规则链，支持通过链式调用添加多种校验规则。
 *
 * @param <T> 待校验对象的类型
 * @param <TProperty> 待校验属性的类型
 * @param <TPropertyValidator> 具体校验器类型，用于支持链式调用
 * @author ZhouXY
 */
public abstract class BasePropertyValidator<
        T,
        TProperty,
        TPropertyValidator extends BasePropertyValidator<T, TProperty, TPropertyValidator>> {

    private final Function<T, ? extends TProperty> getter;

    private final List<Consumer<? super TProperty>> consumers = new LinkedList<>();

    protected BasePropertyValidator(Function<T, ? extends TProperty> getter) {
        this.getter = getter;
    }

    /**
     * 添加一条校验属性的规则，当条件不满足时抛出异常。
     *
     * @param condition 校验条件
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     * @throws ValidationException 校验失败时抛出的异常
     */
    protected final TPropertyValidator withRule(
            final Predicate<? super TProperty> condition,
            final String errorMessage) {
        return withRule(input -> {
            if (!condition.test(input)) {
                throw ValidationException.withMessage(errorMessage);
            }
        });
    }

    /**
     * 添加一条校验属性的规则
     *
     * @param condition 校验条件
     * @param errorMessageTemplate 异常信息模板
     * @param errorMessageArgs 异常信息参数
     * @return 当前校验器实例，用于链式调用
     * @throws ValidationException 校验失败时抛出的异常
     */
    protected final TPropertyValidator withRule(
            final Predicate<? super TProperty> condition,
            final String errorMessageTemplate, Object... errorMessageArgs) {
        return withRule(input -> {
            if (!condition.test(input)) {
                throw ValidationException.withMessage(errorMessageTemplate, errorMessageArgs);
            }
        });
    }

    /**
     * 添加一条校验属性的规则
     *
     * @param <X> 自定义异常类型
     * @param condition 校验条件
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    protected final <X extends RuntimeException> TPropertyValidator withRule(
            final Predicate<? super TProperty> condition,
            final Supplier<X> exceptionSupplier) {
        return withRule(input -> {
            if (!condition.test(input)) {
                throw exceptionSupplier.get();
            }
        });
    }

    /**
     * 添加一条校验属性的规则，当条件不满足时抛出自定义异常。可以根据当前属性的值创建异常。
     *
     * @param <X> 自定义异常类型
     * @param condition 校验条件
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    protected final <X extends RuntimeException> TPropertyValidator withRule(
            final Predicate<? super TProperty> condition,
            final Function<? super TProperty, X> exceptionFunction) {
        return withRule(input -> {
            if (!condition.test(input)) {
                throw exceptionFunction.apply(input);
            }
        });
    }

    /**
     * 添加一条校验属性的规则
     *
     * @param rule 自定义校验规则
     * @return 当前校验器实例，用于链式调用
     */
    protected final TPropertyValidator withRule(Consumer<? super TProperty> rule) {
        this.consumers.add(rule);
        return thisObject();
    }

    /**
     * 校验属性
     *
     * @param obj 属性所在的对象
     */
    public final void validate(T obj) {
        for (Consumer<? super TProperty> consumer : consumers) {
            consumer.accept(getter.apply(obj));
        }
    }

    protected abstract TPropertyValidator thisObject();

    // ====================
    // ====== Object ======
    // ====================

    // ================================
    // #region - notNull
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否不为空
     *
     * @return 当前校验器实例，用于链式调用
     */
    public final TPropertyValidator notNull() {
        return withRule(Objects::nonNull, "The input must not be null.");
    }

    /**
     * 添加一条校验属性的规则，校验属性是否不为空
     *
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final TPropertyValidator notNull(final String errorMessage) {
        return withRule(Objects::nonNull, errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否不为空
     *
     * @param <X> 自定义异常类型
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> TPropertyValidator notNull(
            final Supplier<X> exceptionSupplier) {
        return withRule(Objects::nonNull, exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否不为空
     *
     * @param <X> 自定义异常类型
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> TPropertyValidator notNull(
            final Function<TProperty, X> exceptionFunction) {
        return withRule(Objects::nonNull, exceptionFunction);
    }

    // ================================
    // #endregion - notNull
    // ================================

    // ================================
    // #region - isNull
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否为空
     *
     * @return 当前校验器实例，用于链式调用
     */
    public final TPropertyValidator isNull() {
        return withRule(Objects::isNull, "The input must be null.");
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为空
     *
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final TPropertyValidator isNull(final String errorMessage) {
        return withRule(Objects::isNull, errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为空
     *
     * @param <X> 自定义异常类型
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> TPropertyValidator isNull(
            final Supplier<X> exceptionSupplier) {
        return withRule(Objects::isNull, exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为空
     *
     * @param <X> 自定义异常类型
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> TPropertyValidator isNull(
            final Function<TProperty, X> exceptionFunction) {
        return withRule(Objects::isNull, exceptionFunction);
    }

    // ================================
    // #endregion - isNull
    // ================================

    // ================================
    // #region - equal
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否等于给定值
     *
     * @param obj 用于比较的对象
     * @return 当前校验器实例，用于链式调用
     */
    public final TPropertyValidator equal(Object obj) {
        return withRule(Conditions.equal(obj),
                "The input must be equal to '%s'.", obj);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否等于给定值
     *
     * @param obj 用于比较的对象
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final TPropertyValidator equal(
            final Object obj, final String errorMessage) {
        return withRule(Conditions.equal(obj), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否等于给定值
     *
     * @param <X> 自定义异常类型
     * @param obj 用于比较的对象
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> TPropertyValidator equal(
            final Object obj, final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.equal(obj), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否等于给定值
     *
     * @param <X> 自定义异常类型
     * @param obj 用于比较的对象
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> TPropertyValidator equal(
            final Object obj, final Function<TProperty, X> exceptionFunction) {
        return withRule(Conditions.equal(obj), exceptionFunction);
    }

    // ================================
    // #endregion - equal
    // ================================

    // ================================
    // #region - notEqual
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否等于给定值
     *
     * @param obj 用于比较的对象
     * @return 当前校验器实例，用于链式调用
     */
    public final TPropertyValidator notEqual(final Object obj) {
        return withRule(Conditions.notEqual(obj),
                "The input must not equal '%s'.", obj);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否等于给定值
     *
     * @param obj 用于比较的对象
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final TPropertyValidator notEqual(final Object obj, final String errorMessage) {
        return withRule(Conditions.notEqual(obj), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否等于给定值
     *
     * @param <X> 自定义异常类型
     * @param obj 用于比较的对象
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> TPropertyValidator notEqual(
            final Object obj, final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.notEqual(obj), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否等于给定值
     *
     * @param <X> 自定义异常类型
     * @param obj 用于比较的对象
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> TPropertyValidator notEqual(
            final Object obj, final Function<TProperty, X> exceptionFunction) {
        return withRule(Conditions.notEqual(obj), exceptionFunction);
    }

    // ================================
    // #endregion - notEqual
    // ================================

    // ================================
    // #region - must
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否满足给定的条件
     *
     * @param condition 校验条件
     * @return 当前校验器实例，用于链式调用
     */
    public final TPropertyValidator must(final Predicate<? super TProperty> condition) {
        return withRule(condition,
                "The specified condition was not met for the input.");
    }

    /**
     * 添加一条校验属性的规则，校验属性是否满足给定的条件
     *
     * @param condition 校验条件
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final TPropertyValidator must(
            final Predicate<? super TProperty> condition,
            final String errorMessage) {
        return withRule(condition, errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否满足给定的条件
     *
     * @param <X> 自定义异常类型
     * @param condition 校验规则
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> TPropertyValidator must(
            final Predicate<? super TProperty> condition,
            final Supplier<X> exceptionSupplier) {
        return withRule(condition, exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否满足给定的条件
     *
     * @param <X> 自定义异常类型
     * @param condition 校验规则
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> TPropertyValidator must(
            final Predicate<? super TProperty> condition,
            final Function<TProperty, X> exceptionFunction) {
        return withRule(condition, exceptionFunction);
    }

    // ================================
    // #endregion - must
    // ================================

    // ================================
    // #region - conditions
    // ================================

    /**
     * 常用校验条件的实现
     */
    private static class Conditions {

        private static <TProperty> Predicate<TProperty> equal(Object obj) {
            return input -> input == null || input.equals(obj);
        }

        private static <TProperty> Predicate<TProperty> notEqual(Object obj) {
            return input -> input == null || !input.equals(obj);
        }

    }

    // ================================
    // #endregion - conditions
    // ================================
}
