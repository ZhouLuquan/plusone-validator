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
import java.util.regex.Pattern;

import xyz.zhouxy.plusone.commons.constant.PatternConsts;
import xyz.zhouxy.plusone.commons.util.ArrayTools;
import xyz.zhouxy.plusone.commons.util.AssertTools;
import xyz.zhouxy.plusone.commons.util.RegexTools;
import xyz.zhouxy.plusone.commons.util.StringTools;

/**
 * {@code String} 类型属性的校验器
 *
 * <p>
 * 用于构建校验 {@code String} 类型属性的规则链。
 *
 * @param <T> 待校验对象的类型
 * @author ZhouXY
 */
public class StringPropertyValidator<T>
        extends BaseComparablePropertyValidator<T, String, StringPropertyValidator<T>> {

    StringPropertyValidator(Function<T, String> getter) {
        super(getter);
    }

    // ================================
    // #region - matches
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否匹配正则表达式
     *
     * @param pattern 正则表达式
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final StringPropertyValidator<T> matches(
            final Pattern pattern, final String errorMessage) {
        return withRule(Conditions.matches(pattern), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配正则表达式
     *
     * @param <X> 自定义异常类型
     * @param pattern 正则表达式
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public <X extends RuntimeException> StringPropertyValidator<T> matches(
            final Pattern pattern, final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.matches(pattern), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配正则表达式
     *
     * @param <X> 自定义异常类型
     * @param pattern 正则表达式
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> StringPropertyValidator<T> matches(
            final Pattern pattern, final Function<String, X> exceptionFunction) {
        return withRule(Conditions.matches(pattern), exceptionFunction);
    }

    // ================================
    // #endregion - matches
    // ================================

    // ================================
    // #region - matchesAny
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的任一正则表达式
     *
     * @param patterns 正则表达式
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final StringPropertyValidator<T> matchesAny(
            final Pattern[] patterns, final String errorMessage) {
        return withRule(Conditions.matchesAny(patterns), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的任一正则表达式
     *
     * @param <X> 自定义异常类型
     * @param patterns 正则表达式
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> StringPropertyValidator<T> matchesAny(
            final Pattern[] patterns, final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.matchesAny(patterns), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的任一正则表达式
     *
     * @param <X> 自定义异常类型
     * @param patterns 正则表达式
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> StringPropertyValidator<T> matchesAny(
            final Pattern[] patterns, final Function<String, X> exceptionFunction) {
        return withRule(Conditions.matchesAny(patterns), exceptionFunction);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的任一正则表达式
     *
     * @param patterns 正则表达式
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final StringPropertyValidator<T> matchesAny(
            final Collection<Pattern> patterns, final String errorMessage) {
        return withRule(Conditions.matchesAny(patterns), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的任一正则表达式
     *
     * @param <X> 自定义异常类型
     * @param patterns 正则表达式
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> StringPropertyValidator<T> matchesAny(
            final Collection<Pattern> patterns, final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.matchesAny(patterns), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的任一正则表达式
     *
     * @param <X> 自定义异常类型
     * @param patterns 正则表达式
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> StringPropertyValidator<T> matchesAny(
            final Collection<Pattern> patterns, final Function<String, X> exceptionFunction) {
        return withRule(Conditions.matchesAny(patterns), exceptionFunction);
    }

    // ================================
    // #endregion - matchesAny
    // ================================

    // ================================
    // #region - matchesAll
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的所有正则表达式
     *
     * @param patterns 正则表达式
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final StringPropertyValidator<T> matchesAll(
            final Pattern[] patterns, final String errorMessage) {
        return withRule(Conditions.matchesAll(patterns), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的所有正则表达式
     *
     * @param <X> 自定义异常类型
     * @param patterns 正则表达式
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> StringPropertyValidator<T> matchesAll(
            final Pattern[] patterns, final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.matchesAll(patterns), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的所有正则表达式
     *
     * @param <X> 自定义异常类型
     * @param patterns 正则表达式
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> StringPropertyValidator<T> matchesAll(
            final Pattern[] patterns, final Function<String, X> exceptionFunction) {
        return withRule(Conditions.matchesAll(patterns), exceptionFunction);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的所有正则表达式
     *
     * @param patterns 正则表达式
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final StringPropertyValidator<T> matchesAll(
            final Collection<Pattern> patterns, final String errorMessage) {
        return withRule(Conditions.matchesAll(patterns), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的所有正则表达式
     *
     * @param <X> 自定义异常类型
     * @param patterns 正则表达式
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> StringPropertyValidator<T> matchesAll(
            final Collection<Pattern> patterns, final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.matchesAll(patterns), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的所有正则表达式
     *
     * @param <X> 自定义异常类型
     * @param patterns 正则表达式
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> StringPropertyValidator<T> matchesAll(
            final Collection<Pattern> patterns, final Function<String, X> exceptionFunction) {
        return withRule(Conditions.matchesAll(patterns), exceptionFunction);
    }

    // ================================
    // #endregion - matchesAll
    // ================================

    // ================================
    // #region - notBlank
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否不为空白字符串
     *
     * @return 当前校验器实例，用于链式调用
     */
    public final StringPropertyValidator<T> notBlank() {
        return withRule(Conditions.notBlank(),
                "The input must not be blank.");
    }

    /**
     * 添加一条校验属性的规则，校验属性是否不为空白字符串
     *
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final StringPropertyValidator<T> notBlank(final String errorMessage) {
        return withRule(Conditions.notBlank(), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否不为空白字符串
     *
     * @param <X> 自定义异常类型
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> StringPropertyValidator<T> notBlank(
            final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.notBlank(), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否不为空白字符串
     *
     * @param <X> 自定义异常类型
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> StringPropertyValidator<T> notBlank(
            final Function<String, X> exceptionFunction) {
        return withRule(Conditions.notBlank(), exceptionFunction);
    }

    // ================================
    // #endregion - notBlank
    // ================================

    // ================================
    // #region - emailAddress
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否满足邮箱格式
     *
     * @return 当前校验器实例，用于链式调用
     */
    public final StringPropertyValidator<T> emailAddress() {
        return withRule(Conditions.emailAddress(),
                "The input is not a valid email address.");
    }

    /**
     * 添加一条校验属性的规则，校验属性是否满足邮箱格式
     *
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final StringPropertyValidator<T> emailAddress(final String errorMessage) {
        return withRule(Conditions.emailAddress(), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否满足邮箱格式
     *
     * @param <X> 自定义异常类型
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> StringPropertyValidator<T> emailAddress(
            final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.emailAddress(), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否满足邮箱格式
     *
     * @param <X> 自定义异常类型
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> StringPropertyValidator<T> emailAddress(
            final Function<String, X> exceptionFunction) {
        return withRule(Conditions.emailAddress(), exceptionFunction);
    }

    // ================================
    // #endregion - emailAddress
    // ================================

    // ================================
    // #region - notEmpty
    // ================================

    /**
     * 添加一条校验属性的规则，校验字符串属性是否不为空
     *
     * @return 当前校验器实例，用于链式调用
     */
    public final StringPropertyValidator<T> notEmpty() {
        return withRule(Conditions.notEmpty(),
                "The input must not be empty.");
    }

    /**
     * 添加一条校验属性的规则，校验字符串属性是否不为空
     *
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final StringPropertyValidator<T> notEmpty(final String errorMessage) {
        return withRule(Conditions.notEmpty(), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验字符串属性是否不为空
     *
     * @param <X> 自定义异常类型
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> StringPropertyValidator<T> notEmpty(
            final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.notEmpty(), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验字符串属性是否不为空
     *
     * @param <X> 自定义异常类型
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> StringPropertyValidator<T> notEmpty(
            final Function<String, X> exceptionFunction) {
        return withRule(Conditions.notEmpty(), exceptionFunction);
    }

    // ================================
    // #endregion - notEmpty
    // ================================

    // ================================
    // #region - length
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性长度是否等于指定长度
     *
     * @param length 指定长度
     * @param errorMessage 异常信息
     * @return 当前校验器实例，用于链式调用
     */
    public final StringPropertyValidator<T> length(
            final int length, final String errorMessage) {
        return withRule(Conditions.length(length), errorMessage);
    }

    /**
     * 添加一条校验属性的规则，校验属性长度是否等于指定长度
     *
     * @param <X> 自定义异常类型
     * @param length 指定长度
     * @param exceptionSupplier 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> StringPropertyValidator<T> length(
            final int length, final Supplier<X> exceptionSupplier) {
        return withRule(Conditions.length(length), exceptionSupplier);
    }

    /**
     * 添加一条校验属性的规则，校验属性长度是否等于指定长度
     *
     * @param <X> 自定义异常类型
     * @param length 指定长度
     * @param exceptionFunction 自定义异常
     * @return 当前校验器实例，用于链式调用
     */
    public final <X extends RuntimeException> StringPropertyValidator<T> length(
            final int length, final Function<String, X> exceptionFunction) {
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
    public final StringPropertyValidator<T> length(
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
    public final <X extends RuntimeException> StringPropertyValidator<T> length(
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
    public final <X extends RuntimeException> StringPropertyValidator<T> length(
            final int min, final int max, final Function<String, X> exceptionFunction) {
        return withRule(Conditions.length(min, max), exceptionFunction);
    }

    // ================================
    // #endregion - length
    // ================================

    @Override
    protected StringPropertyValidator<T> thisObject() {
        return this;
    }

    private static class Conditions {

        private static Predicate<String> notEmpty() {
            return StringTools::isNotEmpty;
        }

        private static Predicate<String> notBlank() {
            return StringTools::isNotBlank;
        }

        private static Predicate<String> matches(Pattern pattern) {
            AssertTools.checkArgumentNotNull(pattern);
            return input -> input == null || RegexTools.matches(input, pattern);
        }

        private static Predicate<String> matchesAny(Pattern[] patterns) {
            AssertTools.checkArgument(ArrayTools.isAllElementsNotNull(patterns));
            return input -> input == null || RegexTools.matchesAny(input, patterns);
        }

        private static Predicate<String> matchesAny(Collection<Pattern> patterns) {
            AssertTools.checkArgumentNotNull(patterns, "patterns must not be null.");
            return input -> input == null || RegexTools.matchesAny(input, patterns.toArray(new Pattern[0]));
        }

        private static Predicate<String> matchesAll(Pattern[] patterns) {
            AssertTools.checkArgument(ArrayTools.isAllElementsNotNull(patterns));
            return input -> input == null || RegexTools.matchesAll(input, patterns);
        }

        private static Predicate<String> matchesAll(Collection<Pattern> patterns) {
            AssertTools.checkArgumentNotNull(patterns, "patterns must not be null.");
            return input -> input == null || RegexTools.matchesAll(input, patterns.toArray(new Pattern[0]));
        }

        private static Predicate<String> emailAddress() {
            return input -> input == null || RegexTools.matches(input, PatternConsts.EMAIL);
        }

        private static Predicate<String> length(int length) {
            AssertTools.checkArgument(length >= 0,
                "The expected length must be non-negative.");
            return input -> input == null || input.length() == length;
        }

        private static Predicate<String> length(int min, int max) {
            AssertTools.checkArgument(min >= 0, "min must be non-negative.");
            AssertTools.checkArgument(min <= max, "min must be less than or equal to max.");
            return input -> {
                if (input == null) {
                    return true;
                }
                final int len = input.length();
                return len >= min && len <= max;
            };
        }
    }

}
