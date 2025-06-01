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
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import xyz.zhouxy.plusone.commons.constant.PatternConsts;
import xyz.zhouxy.plusone.commons.util.AssertTools;
import xyz.zhouxy.plusone.commons.util.RegexTools;
import xyz.zhouxy.plusone.commons.util.StringTools;

/**
 * StringPropertyValidator
 *
 * <p>
 * 针对文本字段的校验器。
 * </p>
 *
 * @author ZhouXY
 */
public class StringPropertyValidator<T> extends BaseComparablePropertyValidator<T, String, StringPropertyValidator<T>> {

    StringPropertyValidator(Function<T, String> getter) {
        super(getter);
    }

    // ================================
    // #region - matches
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否匹配正则表达式
     *
     * @param regex 正则表达式
     * @param errMsg 异常信息
     * @return 属性校验器
     */
    public StringPropertyValidator<T> matches(Pattern regex, String errMsg) {
        return matches(regex, convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配正则表达式
     *
     * @param <E> 异常类型
     * @param regex 正则表达式
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> StringPropertyValidator<T> matches(
            Pattern regex, Supplier<E> e) {
        return matches(regex, convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配正则表达式
     *
     * @param <E> 异常类型
     * @param regex 正则表达式
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> StringPropertyValidator<T> matches(
            Pattern regex, Function<String, E> e) {
        return withRule(input -> (input == null || RegexTools.matches(input, regex)), e);
    }

    // ================================
    // #endregion - matches
    // ================================

    // ================================
    // #region - matchesOne
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的多个正则表达式的其中一个
     *
     * @param regexs 正则表达式
     * @param errMsg 异常信息
     * @return 属性校验器
     */
    public StringPropertyValidator<T> matchesOne(Pattern[] regexs, String errMsg) {
        return matchesOne(regexs, convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的多个正则表达式的其中一个
     *
     * @param <E> 异常类型
     * @param regexs 正则表达式
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> StringPropertyValidator<T> matchesOne(
            Pattern[] regexs, Supplier<E> e) {
        return matchesOne(regexs, convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的多个正则表达式的其中一个
     *
     * @param <E> 异常类型
     * @param regexs 正则表达式
     * @param e 自定义异常
     * @return
     */
    public <E extends RuntimeException> StringPropertyValidator<T> matchesOne(
            Pattern[] regexs, Function<String, E> e) {
        return withRule(input -> input == null || RegexTools.matchesOne(input, regexs), e);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的多个正则表达式的其中一个
     *
     * @param regexs 正则表达式
     * @param errMsg 异常信息
     * @return 属性校验器
     */
    public StringPropertyValidator<T> matchesOne(List<Pattern> regexs, String errMsg) {
        return matchesOne(regexs, convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的多个正则表达式的其中一个
     *
     * @param <E> 异常类型
     * @param regexs 正则表达式
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> StringPropertyValidator<T> matchesOne(
            List<Pattern> regexs, Supplier<E> e) {
        return matchesOne(regexs, convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的多个正则表达式的其中一个
     *
     * @param <E> 异常类型
     * @param regexs 正则表达式
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> StringPropertyValidator<T> matchesOne(
            List<Pattern> regexs, Function<String, E> e) {
        return withRule(input -> input == null || RegexTools.matchesOne(input, regexs.toArray(new Pattern[regexs.size()])), e);
    }

    // ================================
    // #endregion - matchesOne
    // ================================

    // ================================
    // #region - matchesAll
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的所有正则表达式
     *
     * @param regexs 正则表达式
     * @param errMsg 异常信息
     * @return 属性校验器
     */
    public StringPropertyValidator<T> matchesAll(Pattern[] regexs, String errMsg) {
        return matchesAll(regexs, convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的所有正则表达式
     *
     * @param <E> 异常类型
     * @param regexs 正则表达式
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> StringPropertyValidator<T> matchesAll(
            Pattern[] regexs, Supplier<E> e) {
        return matchesAll(regexs, convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的所有正则表达式
     *
     * @param <E> 异常类型
     * @param regexs 正则表达式
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> StringPropertyValidator<T> matchesAll(
            Pattern[] regexs, Function<String, E> e) {
        return withRule(input -> input == null || RegexTools.matchesAll(input, regexs), e);
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的所有正则表达式
     *
     * @param regexs 正则表达式
     * @param errMsg 异常信息
     * @return 属性校验器
     */
    public StringPropertyValidator<T> matchesAll(Collection<Pattern> regexs, String errMsg) {
        return matchesAll(regexs, convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的所有正则表达式
     *
     * @param <E> 异常类型
     * @param regexs 正则表达式
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> StringPropertyValidator<T> matchesAll(
            Collection<Pattern> regexs, Supplier<E> e) {
        return matchesAll(regexs, convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否匹配指定的所有正则表达式
     *
     * @param <E> 异常类型
     * @param regexs 正则表达式
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> StringPropertyValidator<T> matchesAll(
            Collection<Pattern> regexs, Function<String, E> e) {
        return withRule(input -> input == null || RegexTools.matchesAll(input, regexs.toArray(new Pattern[regexs.size()])), e);
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
     * @return 属性校验器
     */
    public StringPropertyValidator<T> notBlank() {
        return notBlank("The input must not be blank.");
    }

    /**
     * 添加一条校验属性的规则，校验属性是否不为空白字符串
     *
     * @param errMsg 异常信息
     * @return 属性校验器
     */
    public StringPropertyValidator<T> notBlank(String errMsg) {
        return notBlank(convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否不为空白字符串
     *
     * @param <E> 异常类型
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> StringPropertyValidator<T> notBlank(Supplier<E> e) {
        return notBlank(convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否不为空白字符串
     *
     * @param <E> 异常类型
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> StringPropertyValidator<T> notBlank(Function<String, E> e) {
        return withRule(StringTools::isNotBlank, e);
    }

    // ================================
    // #endregion - notBlank
    // ================================

    // ================================
    // #region - emailAddress
    // ================================

    /**
     * 添加一条校验属性的规则，校验属性是否是邮箱地址
     *
     * @return 属性校验器
     */
    public StringPropertyValidator<T> emailAddress() {
        return emailAddress("The input is not a valid email address.");
    }

    /**
     * 添加一条校验属性的规则，校验属性是否是邮箱地址
     *
     * @param errMsg 校验失败的错误信息
     * @return 属性校验器
     */
    public StringPropertyValidator<T> emailAddress(String errMsg) {
        return emailAddress(convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否是邮箱地址
     *
     * @param <E> 异常类型
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> StringPropertyValidator<T> emailAddress(Supplier<E> e) {
        return emailAddress(convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验属性是否是邮箱地址
     *
     * @param <E> 异常类型
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> StringPropertyValidator<T> emailAddress(
            Function<String, E> e) {
        return matches(PatternConsts.EMAIL, e);
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
     * @return 属性校验器
     */
    public StringPropertyValidator<T> notEmpty() {
        return notEmpty("The input must not be empty.");
    }

    /**
     * 添加一条校验属性的规则，校验字符串属性是否不为空
     *
     * @param errMsg 异常信息
     * @return 属性校验器
     */
    public StringPropertyValidator<T> notEmpty(String errMsg) {
        return notEmpty(convertToExceptionFunction(errMsg));
    }

    /**
     * 添加一条校验属性的规则，校验字符串属性是否不为空
     *
     * @param <E> 异常类型
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> StringPropertyValidator<T> notEmpty(Supplier<E> e) {
        return notEmpty(convertToExceptionFunction(e));
    }

    /**
     * 添加一条校验属性的规则，校验字符串属性是否不为空
     *
     * @param <E> 异常类型
     * @param e 自定义异常
     * @return 属性校验器
     */
    public <E extends RuntimeException> StringPropertyValidator<T> notEmpty(
            Function<String, E> e) {
        return withRule(s -> s != null && !s.isEmpty(), e);
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
     * @param errMsg 异常信息
     * @return 属性校验器
     */
    public StringPropertyValidator<T> length(int length, String errMsg) {
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
    public <E extends RuntimeException> StringPropertyValidator<T> length(int length, Supplier<E> e) {
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
    public <E extends RuntimeException> StringPropertyValidator<T> length(int length, Function<String, E> e) {
        AssertTools.checkArgument(length >= 0,
                "The expected length must be greater than or equal to 0.");
        return withRule(s -> s == null || s.length() == length, e);
    }

    static boolean checkLength(String str, int min, int max) {
        if (str == null) {
            return true;
        }
        final int len = str.length();
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
    public StringPropertyValidator<T> length(int min, int max, String errMsg) {
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
    public <E extends RuntimeException> StringPropertyValidator<T> length(int min, int max,
            Supplier<E> e) {
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
    public <E extends RuntimeException> StringPropertyValidator<T> length(int min, int max,
            Function<String, E> e) {
        AssertTools.checkArgument(min >= 0, "min must be non-negative.");
        AssertTools.checkArgument(min <= max, "min must be less than or equal to max.");
        return withRule(s -> checkLength(s, min, max), e);
    }

    // ================================
    // #endregion - length
    // ================================

    @Override
    protected StringPropertyValidator<T> thisObject() {
        return this;
    }
}
