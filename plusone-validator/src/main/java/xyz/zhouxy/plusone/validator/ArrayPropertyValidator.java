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
        return withRule(ArrayTools::isNotEmpty, e);
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
        return withRule(ArrayTools::isEmpty, e);
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
        return withRule(c -> {
            for (TElement element : c) {
                if (!condition.test(element)) {
                    throw e.apply(element);
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
     * @param length 指定长度
     * @param errMsg 异常信息
     * @return 属性校验器
     */
    public ArrayPropertyValidator<T, TElement> length(int length, String errMsg) {
        return length(length, convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性长度是否等于指定长度
     *
     * @param <E> 异常类型
     * @param length 指定长度
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> ArrayPropertyValidator<T, TElement> length(
            int length, Supplier<E> e) {
        return length(length, convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性长度是否等于指定长度
     *
     * @param <E> 异常类型
     * @param length 指定长度
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> ArrayPropertyValidator<T, TElement> length(
            int length, Function<TElement[], E> e) {
        AssertTools.checkArgument(length >= 0,
                "The expected length must be greater than or equal to 0.");
        return withRule(s -> s == null || s.length == length, e);
    }

    static <TElement> boolean checkLength(TElement[] str, int min, int max) {
        if (str == null) {
            return true;
        }
        final int len = str.length;
        return len >= min && len <= max;
    }

    /**
     * 添加一条校验属性的规则，校验属性的长度范围
     *
     * @param min 最小长度
     * @param max 最大长度
     * @param errMsg 错误信息
     * @return 属性校验器
     */
    public ArrayPropertyValidator<T, TElement> length(int min, int max, String errMsg) {
        return length(min, max, convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性的长度范围
     *
     * @param min 最小长度
     * @param max 最大长度
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> ArrayPropertyValidator<T, TElement> length(
            int min, int max, Supplier<E> e) {
        return length(min, max, convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性的长度范围
     *
     * @param min 最小长度
     * @param max 最大长度
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> ArrayPropertyValidator<T, TElement> length(
            int min, int max, Function<TElement[], E> e) {
        AssertTools.checkArgument(min >= 0, "min must be non-negative.");
        AssertTools.checkArgument(min <= max, "min must be less than or equal to max.");
        return withRule(s -> checkLength(s, min, max), e);
    }

    // ================================
    // #endregion - length
    // ================================

    @Override
    protected ArrayPropertyValidator<T, TElement> thisObject() {
        return this;
    }
}
