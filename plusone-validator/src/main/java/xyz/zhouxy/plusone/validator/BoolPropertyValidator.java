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
 * {@code Boolean} 类型属性的校验器
 *
 * <p>
 * 用于构建校验 {@code Boolean} 类型属性的规则链。
 *
 * @param <T> 待校验对象的类型
 * @author ZhouXY108 <luquanlion@outlook.com>
 */
public class BoolPropertyValidator<T>
        extends BasePropertyValidator<T, Boolean, BoolPropertyValidator<T>> {

    BoolPropertyValidator(Function<T, Boolean> getter) {
        super(getter);
    }

    // ====== isTrueValue ======

    /**
     * 添加一条校验属性的规则，校验属性是否为 {@code true}
     *
     * @return 当前校验器实例，用于链式调用
     */
    public final BoolPropertyValidator<T> isTrueValue() {
        return withRule(Conditions.isTrueValue(), "The input must be true.");
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为 {@code true}
     *
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final BoolPropertyValidator<T> isTrueValue(final String errorMessage) {
        return withRule(Conditions.isTrueValue(), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为 {@code true}
     *
     * @param <X> 自定义异常类型
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> BoolPropertyValidator<T> isTrueValue(
            final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.isTrueValue(), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为 {@code true}
     *
     * @param <X> 自定义异常类型
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> BoolPropertyValidator<T> isTrueValue(
            final Function<Boolean, X> exceptionFunction) {
        return withRule(Conditions.isTrueValue(), exceptionFunction);
    }

    // ====== isFalseValue ======

    /**
     * 添加一条校验属性的规则，校验属性是否为 {@code false}
     *
     * @return 当前校验器实例，用于链式调用
     */
    public final BoolPropertyValidator<T> isFalseValue() {
        return withRule(Conditions.isFalseValue(), "The input must be false.");
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为 {@code false}
     *
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final BoolPropertyValidator<T> isFalseValue(final String errorMessage) {
        return withRule(Conditions.isFalseValue(), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为 {@code false}
     *
     * @param <X> 自定义异常类型
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> BoolPropertyValidator<T> isFalseValue(
            final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.isFalseValue(), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为 {@code false}
     *
     * @param <X> 自定义异常类型
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> BoolPropertyValidator<T> isFalseValue(
            final Function<Boolean, X> exceptionFunction) {
        return withRule(Conditions.isFalseValue(), exceptionFunction);
    }

    private static class Conditions {

        private static <TProperty> Predicate<TProperty> isTrueValue() {
            return Boolean.TRUE::equals;
        }

        private static <TProperty> Predicate<TProperty> isFalseValue() {
            return Boolean.FALSE::equals;
        }
    }

    @Override
    protected BoolPropertyValidator<T> thisObject() {
        return this;
    }
}
