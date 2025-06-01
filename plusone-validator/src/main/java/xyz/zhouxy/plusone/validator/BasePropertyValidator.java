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

import java.util.Collection;
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
 * <i>内置基础的校验规则。</i>
 * </p>
 *
 * @author ZhouXY
 */
public abstract class BasePropertyValidator<T, TProperty, TPropertyValidator extends BasePropertyValidator<T, TProperty, TPropertyValidator>> {

    private final Function<T, ? extends TProperty> getter;

    private final List<Consumer<? super TProperty>> consumers = new LinkedList<>();

    protected BasePropertyValidator(Function<T, ? extends TProperty> getter) {
        this.getter = getter;
    }

    /**
     * 添加一条校验属性的规则
     *
     * @param rule 校验规则
     * @param e 自定义异常
     * @return 属性校验器
     */
    protected final <E extends RuntimeException> TPropertyValidator withRule(
            Predicate<? super TProperty> rule, Function<TProperty, E> e) {
        return withRule(v -> {
            if (!rule.test(v)) {
                throw e.apply(v);
            }
        });
    }

    /**
     * 添加一条校验属性的规则
     *
     * @param rule 校验规则
     * @param e 自定义异常
     * @return 属性校验器
     */
    protected final TPropertyValidator withRule(Consumer<? super TProperty> rule) {
        this.consumers.add(rule);
        return thisObject();
    }

    /**
     * 校验属性
     * @param propertyValue 属性值
     */
    public final void validate(T propertyValue) {
        for (Consumer<? super TProperty> consumer : consumers) {
            consumer.accept(getter.apply(propertyValue));
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
     * @return 属性校验器
     */
    public TPropertyValidator notNull() {
        return notNull("The input must not be null.");
    }

    /**
     * 添加一条校验属性的规则，校验属性是否不为空
     *
     * @param errMsg 错误信息
     * @return 属性校验器
     */
    public TPropertyValidator notNull(String errMsg) {
        return notNull(convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否不为空
     *
     * @param <E> 自定义异常类型
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> TPropertyValidator notNull(Supplier<E> e) {
        return notNull(convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否不为空
     *
     * @param <E> 自定义异常类型
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> TPropertyValidator notNull(Function<TProperty, E> e) {
        withRule(Objects::nonNull, e);
        return thisObject();
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
     * @return 属性校验器
     */
    public TPropertyValidator isNull() {
        return isNull("The input must be null.");
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为空
     *
     * @param errMsg 错误信息
     * @return 属性校验器
     */
    public TPropertyValidator isNull(String errMsg) {
        return isNull(convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为空
     *
     * @param <E> 自定义异常类型
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> TPropertyValidator isNull(Supplier<E> e) {
        return isNull(convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为空
     *
     * @param <E> 自定义异常类型
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> TPropertyValidator isNull(Function<TProperty, E> e) {
        withRule(Objects::isNull, e);
        return thisObject();
    }

    // ================================
    // #endregion - isNull
    // ================================

    // ================================
    // #region - equalTo
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否等于给定值
     *
     * @param that 给定值
     * @return 属性校验器
     */
    public TPropertyValidator equalTo(Object that) {
        return equalTo(that, value -> ValidationException
                .withMessage("The input must be equal to '%s'.", that));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否等于给定值
     *
     * @param that 给定值
     * @param errMsg 错误信息
     * @return 属性校验器
     */
    public TPropertyValidator equalTo(Object that, String errMsg) {
        return equalTo(that, convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否等于给定值
     *
     * @param <E> 自定义异常类型
     * @param that 给定值
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> TPropertyValidator equalTo(
            Object that, Supplier<E> e) {
        return equalTo(that, convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否等于给定值
     *
     * @param <E> 自定义异常类型
     * @param that 给定值
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> TPropertyValidator equalTo(
            Object that, Function<TProperty, E> e) {
        withRule(value -> Objects.equals(value, that), e);
        return thisObject();
    }

    // ================================
    // #endregion - equalTo
    // ================================

    // ================================
    // #region - must
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否满足给定的条件
     *
     * @param condition 校验规则
     * @return 属性校验器
     */
    public TPropertyValidator must(Predicate<? super TProperty> condition) {
        return must(condition, "The specified condition was not met for the input.");
    }

    /**
     * 添加一条校验属性的规则，校验属性是否满足给定的条件
     *
     * @param condition 校验规则
     * @param errMsg 错误信息
     * @return 属性校验器
     */
    public TPropertyValidator must(Predicate<? super TProperty> condition, String errMsg) {
        return must(condition, convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否满足给定的条件
     *
     * @param <E> 自定义异常类型
     * @param condition 校验规则
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> TPropertyValidator must(
            Predicate<? super TProperty> condition,
            Supplier<E> e) {
        return must(condition, convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否满足给定的条件
     *
     * @param <E> 自定义异常类型
     * @param condition 校验规则
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> TPropertyValidator must(
            Predicate<? super TProperty> condition,
            Function<TProperty, E> e) {
        withRule(condition, e);
        return thisObject();
    }

    /**
     * 添加多条校验属性的规则，校验属性是否满足给定的所有条件
     *
     * @param conditions 校验规则
     * @return 属性校验器
     */
    public TPropertyValidator must(Collection<Predicate<? super TProperty>> conditions) {
        return must(conditions, "The specified conditions were not met for the input.");
    }

    /**
     * 添加多条校验属性的规则，校验属性是否满足给定的所有条件
     *
     * @param conditions 校验规则
     * @param errMsg 错误信息
     * @return 属性校验器
     */
    public TPropertyValidator must(Collection<Predicate<? super TProperty>> conditions, String errMsg) {
        return must(conditions, convertToExceptionFunction(errMsg));
    }

    /**
     * 添加多条校验属性的规则，校验属性是否满足给定的所有条件
     *
     * @param <E> 自定义异常类型
     * @param conditions 校验规则
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> TPropertyValidator must(
            Collection<Predicate<? super TProperty>> conditions, Supplier<E> e) {
        return must(conditions, convertToExceptionFunction(e));
    }

    /**
     * 添加多条校验属性的规则，校验属性是否满足给定的所有条件
     *
     * @param <E> 自定义异常类型
     * @param conditions 校验规则
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> TPropertyValidator must(
            Collection<Predicate<? super TProperty>> conditions,
            Function<TProperty, E> e) {
        for (Predicate<? super TProperty> condition : conditions) {
            withRule(condition, e);
        }
        return thisObject();
    }

    // ================================
    // #endregion - must
    // ================================

    static <V> Function<V, ValidationException> convertToExceptionFunction(String errMsg) {
        return value -> ValidationException.withMessage(errMsg);
    }

    static <V> Function<V, ValidationException> convertToExceptionFunction(
            String errorMessageTemplate, Object... errorMessageArgs) {
        return value -> ValidationException.withMessage(errorMessageTemplate, errorMessageArgs);
    }

    static <V, E extends RuntimeException> Function<V, E> convertToExceptionFunction(
            Supplier<E> exceptionSupplier) {
        return value -> exceptionSupplier.get();
    }
}
