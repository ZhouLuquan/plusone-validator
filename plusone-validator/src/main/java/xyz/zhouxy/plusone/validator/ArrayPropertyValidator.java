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

/**
 * 针对数组类型的属性校验器
 *
 * <p>
 * 内置数组相关的校验规则。
 *
 * @author ZhouXY
 */
public class ArrayPropertyValidator<T, TElement>
        extends BasePropertyValidator<T, TElement[], ArrayPropertyValidator<T, TElement>> {

    ArrayPropertyValidator(Function<T, TElement[]> getter) {
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
    public ArrayPropertyValidator<T, TElement> notEmpty() {
        return notEmpty("The input must not be empty.");
    }

    /**
     * 添加一条校验属性的规则，校验属性是否非空
     *
     * @param errMsg 异常信息
     * @return 属性校验器
     */
    public ArrayPropertyValidator<T, TElement> notEmpty(String errMsg) {
        return notEmpty(convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否非空
     *
     * @param <E> 自定义异常类型
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> ArrayPropertyValidator<T, TElement> notEmpty(
            Supplier<E> e) {
        return notEmpty(convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否非空
     *
     * @param <E> 自定义异常类型
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> ArrayPropertyValidator<T, TElement> notEmpty(
            Function<TElement[], E> e) {
        withRule(ArrayTools::isNotEmpty, e);
        return this;
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
    public ArrayPropertyValidator<T, TElement> isEmpty() {
        return isEmpty("The input must be empty.");
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为空
     *
     * @param errMsg 异常信息
     * @return 属性校验器
     */
    public ArrayPropertyValidator<T, TElement> isEmpty(String errMsg) {
        return isEmpty(convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为空
     *
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> ArrayPropertyValidator<T, TElement> isEmpty(
            Supplier<E> e) {
        return isEmpty(convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否为空
     *
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> ArrayPropertyValidator<T, TElement> isEmpty(
            Function<TElement[], E> e) {
        withRule(ArrayTools::isEmpty, e);
        return this;
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
     * @param condition 校验规则
     * @return 属性校验器
     */
    public ArrayPropertyValidator<T, TElement> allMatch(Predicate<TElement> condition) {
        return allMatch(condition, convertToExceptionFunction("All elements must match the condition."));
    }

    /**
     * 添加一条校验属性的规则，校验是否所有元素都满足条件
     *
     * @param condition 校验规则
     * @param errMsg 异常信息
     * @return 属性校验器
     */
    public ArrayPropertyValidator<T, TElement> allMatch(Predicate<TElement> condition, String errMsg) {
        return allMatch(condition, convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验是否所有元素都满足条件
     *
     * @param condition 校验规则
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> ArrayPropertyValidator<T, TElement> allMatch(
            Predicate<TElement> condition, Supplier<E> e) {
        return allMatch(condition, convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验是否所有元素都满足条件
     *
     * @param condition 校验规则
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> ArrayPropertyValidator<T, TElement> allMatch(
            Predicate<TElement> condition, Function<TElement, E> e) {
        withRule(c -> {
            for (TElement element : c) {
                if (!condition.test(element)) {
                    throw e.apply(element);
                }
            }
        });
        return this;
    }

    // ================================
    // #endregion - allMatch
    // ================================

    @Override
    protected ArrayPropertyValidator<T, TElement> thisObject() {
        return this;
    }
}
