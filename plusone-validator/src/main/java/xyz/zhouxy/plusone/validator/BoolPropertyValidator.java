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
 * 针对 {@code Boolean} 类型的属性校验器
 *
 * <p>
 * 内置了判断 Boolean 值是否为 true 或 false 的校验规则。
 * </p>
 *
 * @author ZhouXY
 */
public class BoolPropertyValidator<T> extends BasePropertyValidator<T, Boolean, BoolPropertyValidator<T>> {

    BoolPropertyValidator(Function<T, Boolean> getter) {
        super(getter);
    }

    // ====== isTrueValue ======

    /**
     * 添加一条判断属性值是否为 {@code true} 的校验规则
     *
     * @return 属性校验器
     */
    public BoolPropertyValidator<T> isTrueValue() {
        return isTrueValue("The input must be true.");
    }

    /**
     * 添加一条判断属性值是否为 {@code true} 的校验规则
     *
     * @param errMsg 校验失败的错误信息
     * @return 属性校验器
     */
    public BoolPropertyValidator<T> isTrueValue(String errMsg) {
        return isTrueValue(convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条判断属性值是否为 {@code true} 的校验规则
     *
     * @param <E> 自定义异常类型
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> BoolPropertyValidator<T> isTrueValue(Supplier<E> e) {
        return isTrueValue(convertToExceptionFunction(e));
    }

    /**
     * 添加一条判断属性值是否为 {@code true} 的校验规则
     *
     * @param <E> 自定义异常类型
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> BoolPropertyValidator<T> isTrueValue(Function<Boolean, E> e) {
        return withRule(Boolean.TRUE::equals, e);
    }

    // ====== isFalseValue ======

    /**
     * 添加一条判断属性值是否为 {@code false} 的校验规则
     *
     * @return 属性校验器
     */
    public BoolPropertyValidator<T> isFalseValue() {
        return isFalseValue("The input must be false.");
    }

    /**
     * 添加一条判断属性值是否为 {@code false} 的校验规则
     *
     * @param errMsg 错误信息
     * @return 属性校验器
     */
    public BoolPropertyValidator<T> isFalseValue(String errMsg) {
        return isFalseValue(convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条判断属性值是否为 {@code false} 的校验规则
     *
     * @param <E> 异常类型
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> BoolPropertyValidator<T> isFalseValue(Supplier<E> e) {
        return isFalseValue(convertToExceptionFunction(e));
    }

    /**
     * 添加一条判断属性值是否为 {@code false} 的校验规则
     *
     * @param <E> 异常类型
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> BoolPropertyValidator<T> isFalseValue(Function<Boolean, E> e) {
        return withRule(Boolean.FALSE::equals, e);
    }

    @Override
    protected BoolPropertyValidator<T> thisObject() {
        return this;
    }
}
